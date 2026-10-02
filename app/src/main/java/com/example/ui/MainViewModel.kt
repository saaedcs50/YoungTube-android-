package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.BlockedWordEntity
import com.example.data.model.Category
import com.example.data.model.ChannelEntity
import com.example.data.model.SettingsEntity
import com.example.data.model.UsageEntity
import com.example.data.model.VideoEntity
import com.example.data.repository.YoungTubeRepository
import com.example.util.CryptoUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: YoungTubeRepository = YoungTubeRepository(AppDatabase.getInstance(application))

    // Navigation and Modals State
    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.KidFeed)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _isPinModalVisible = MutableStateFlow(false)
    val isPinModalVisible: StateFlow<Boolean> = _isPinModalVisible.asStateFlow()

    private val _showOnboarding = MutableStateFlow(false)
    val showOnboarding: StateFlow<Boolean> = _showOnboarding.asStateFlow()

    private val _showAdBlockNotice = MutableStateFlow(false)
    val showAdBlockNotice: StateFlow<Boolean> = _showAdBlockNotice.asStateFlow()

    private val _dashboardSection = MutableStateFlow(DashboardSection.CHILD_PROFILE)
    val dashboardSection: StateFlow<DashboardSection> = _dashboardSection.asStateFlow()

    // Filters and Categories State
    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Database flows
    val settingsState: StateFlow<SettingsEntity?> = repository.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allChannels: StateFlow<List<ChannelEntity>> = repository.getAllChannels()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val blockedWords: StateFlow<List<BlockedWordEntity>> = repository.getAllBlockedWords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayUsage: StateFlow<UsageEntity?> = repository.getTodayUsage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val favoriteVideos: StateFlow<List<VideoEntity>> = repository.getFavoriteVideos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedVideos: StateFlow<List<VideoEntity>> = repository.getSavedVideos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val hiddenVideos: StateFlow<List<VideoEntity>> = repository.getHiddenVideos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Raw visible videos from database
    private val rawVisibleVideos: StateFlow<List<VideoEntity>> = repository.getVisibleVideos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Feed combining Category, Search Query, Blocked Words, Channel enabled state, and Music preference
    val filteredFeedVideos: StateFlow<List<VideoEntity>> = combine(
        combine(rawVisibleVideos, _selectedCategory, _searchQuery) { videos, category, query ->
            Triple(videos, category, query)
        },
        blockedWords,
        allChannels,
        settingsState
    ) { (videos, categoryId, query), blocked, channelsList, settings ->
        val channelEnabledMap = channelsList.associate { it.sourceId to it.enabled }
        val blockedWordStrings = blocked.map { it.word.lowercase().trim() }
        val hideMusic = settings?.hideMusicVideos == true
        val cleanQuery = query.trim().lowercase()

        videos.filter { video ->
            // 1. Channel must be enabled
            val isChannelActive = channelEnabledMap[video.channelId] ?: true
            if (!isChannelActive) return@filter false

            // 2. Hide music videos if requested
            if (hideMusic && video.hasMusic) return@filter false

            // 3. Blocked words check
            val titleLower = video.title.lowercase()
            val containsBlockedWord = blockedWordStrings.any { word ->
                word.isNotEmpty() && titleLower.contains(word)
            }
            if (containsBlockedWord) return@filter false

            // 4. Category filter
            val matchesCategory = if (categoryId == "all") {
                true
            } else {
                val catObj = Category.DEFAULT_CATEGORIES.find { it.id == categoryId }
                val targetTags = catObj?.tags ?: listOf(categoryId)
                video.primaryCategory == categoryId || targetTags.any { tag ->
                    channelsList.find { it.sourceId == video.channelId }?.categories?.contains(tag) == true
                }
            }
            if (!matchesCategory) return@filter false

            // 5. Search query filter
            if (cleanQuery.isNotEmpty()) {
                val channelTitle = channelsList.find { it.sourceId == video.channelId }?.title?.lowercase() ?: ""
                val matchesTitle = titleLower.contains(cleanQuery)
                val matchesChannel = channelTitle.contains(cleanQuery)
                if (!matchesTitle && !matchesChannel) return@filter false
            }

            true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Timer Job when Video is actively playing
    private val _isPlayerPlaying = MutableStateFlow(false)
    val isPlayerPlaying: StateFlow<Boolean> = _isPlayerPlaying.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
            val settings = repository.getSettingsSync()
            if (settings?.pinHash == null) {
                _showOnboarding.value = true
            }
        }
        startUsageMonitoring()
    }

    private fun startUsageMonitoring() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_isPlayerPlaying.value && _currentScreen.value is AppScreen.Player) {
                    repository.incrementUsage(1)
                }
                checkSessionLimits()
            }
        }
    }

    private fun checkSessionLimits() {
        val settings = settingsState.value ?: return
        val usage = todayUsage.value?.secondsUsedToday ?: 0
        val maxSeconds = settings.sessionLimitMinutes * 60

        // 1. Check max limit
        if (usage >= maxSeconds && maxSeconds > 0) {
            if (_currentScreen.value is AppScreen.Player || _currentScreen.value is AppScreen.KidFeed) {
                _currentScreen.value = AppScreen.SessionLimitReached
                _isPlayerPlaying.value = false
            }
            return
        }

        // 2. Check schedule window
        if (!isWithinSchedule(settings.scheduleStart, settings.scheduleEnd)) {
            if (_currentScreen.value is AppScreen.Player || _currentScreen.value is AppScreen.KidFeed) {
                _currentScreen.value = AppScreen.SessionLimitReached
                _isPlayerPlaying.value = false
            }
        }
    }

    private fun isWithinSchedule(start: String, end: String): Boolean {
        if (start.isBlank() || end.isBlank()) return true
        try {
            val now = Calendar.getInstance()
            val currentH = now.get(Calendar.HOUR_OF_DAY)
            val currentM = now.get(Calendar.MINUTE)
            val currentTime = currentH * 60 + currentM

            val startParts = start.split(":").map { it.toInt() }
            val endParts = end.split(":").map { it.toInt() }
            val startTime = startParts[0] * 60 + startParts[1]
            val endTime = endParts[0] * 60 + endParts[1]

            return if (startTime <= endTime) {
                currentTime in startTime..endTime
            } else {
                currentTime >= startTime || currentTime <= endTime
            }
        } catch (_: Exception) {
            return true
        }
    }

    fun setCategory(categoryId: String) {
        _selectedCategory.value = categoryId
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun navigateTo(screen: AppScreen) {
        if (screen !is AppScreen.Player) {
            _isPlayerPlaying.value = false
        }
        _currentScreen.value = screen
    }

    fun playVideo(video: VideoEntity) {
        _currentScreen.value = AppScreen.Player(video)
        _isPlayerPlaying.value = true
    }

    fun setPlayerPlaying(playing: Boolean) {
        _isPlayerPlaying.value = playing
    }

    fun setDashboardSection(section: DashboardSection) {
        _dashboardSection.value = section
    }

    fun showPinModal(show: Boolean) {
        _isPinModalVisible.value = show
    }

    fun showAdBlockModal(show: Boolean) {
        _showAdBlockNotice.value = show
    }

    fun dismissOnboarding() {
        _showOnboarding.value = false
    }

    fun saveOnboardingSettings(
        pin: String,
        question: String,
        answer: String
    ) {
        viewModelScope.launch {
            val pinHash = CryptoUtils.sha256(pin)
            val answerHash = CryptoUtils.sha256(answer.trim().lowercase())
            val current = repository.getSettingsSync() ?: SettingsEntity(id = "main")
            val updated = current.copy(
                pinHash = pinHash,
                securityQuestion = question,
                securityAnswerHash = answerHash,
                hasCompletedFirstSetup = true
            )
            repository.upsertSettings(updated)
            _showOnboarding.value = false
        }
    }

    fun verifyPin(pin: String): Boolean {
        val settings = settingsState.value ?: return false
        val enteredHash = CryptoUtils.sha256(pin)
        return enteredHash == settings.pinHash
    }

    fun recoverPinWithAnswer(answer: String, newPin: String): Boolean {
        val settings = settingsState.value ?: return false
        val enteredAnswerHash = CryptoUtils.sha256(answer.trim().lowercase())
        if (enteredAnswerHash == settings.securityAnswerHash) {
            viewModelScope.launch {
                val newHash = CryptoUtils.sha256(newPin)
                repository.upsertSettings(settings.copy(pinHash = newHash))
            }
            return true
        }
        return false
    }

    fun toggleVideoFavorite(videoId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.setVideoFavorite(videoId, isFavorite)
        }
    }

    fun toggleVideoSaved(videoId: String, isSaved: Boolean) {
        viewModelScope.launch {
            repository.setVideoSaved(videoId, isSaved)
        }
    }

    fun hideVideo(videoId: String) {
        viewModelScope.launch {
            repository.setVideoHidden(videoId, true)
        }
    }

    fun unhideVideo(videoId: String) {
        viewModelScope.launch {
            repository.setVideoHidden(videoId, false)
        }
    }

    fun setChannelEnabled(channelId: String, enabled: Boolean) {
        viewModelScope.launch {
            repository.setChannelEnabled(channelId, enabled)
        }
    }

    fun addBlockedWord(word: String) {
        viewModelScope.launch {
            repository.addBlockedWord(word)
        }
    }

    fun removeBlockedWord(word: String) {
        viewModelScope.launch {
            repository.removeBlockedWord(word)
        }
    }

    fun addCustomChannel(sourceId: String, title: String, categories: String) {
        viewModelScope.launch {
            repository.addCustomChannel(sourceId, title, categories)
        }
    }

    fun updateChildProfile(name: String, age: Int?) {
        viewModelScope.launch {
            val settings = settingsState.value ?: SettingsEntity(id = "main")
            repository.upsertSettings(settings.copy(childName = name, childAge = age))
        }
    }

    fun updateTimerSettings(limitMinutes: Int, start: String, end: String) {
        viewModelScope.launch {
            val settings = settingsState.value ?: SettingsEntity(id = "main")
            repository.upsertSettings(
                settings.copy(
                    sessionLimitMinutes = limitMinutes,
                    scheduleStart = start,
                    scheduleEnd = end
                )
            )
        }
    }

    fun setHideMusicVideos(hide: Boolean) {
        viewModelScope.launch {
            val settings = settingsState.value ?: SettingsEntity(id = "main")
            repository.upsertSettings(settings.copy(hideMusicVideos = hide))
        }
    }

    fun setTasteShiftEnabled(enabled: Boolean, targetCategories: String) {
        viewModelScope.launch {
            val settings = settingsState.value ?: SettingsEntity(id = "main")
            repository.upsertSettings(
                settings.copy(
                    tasteShiftEnabled = enabled,
                    tasteShiftTargetCategories = targetCategories
                )
            )
        }
    }

    fun resetTodayUsage() {
        viewModelScope.launch {
            repository.resetTodayUsage()
            if (_currentScreen.value is AppScreen.SessionLimitReached) {
                _currentScreen.value = AppScreen.KidFeed
            }
        }
    }
}

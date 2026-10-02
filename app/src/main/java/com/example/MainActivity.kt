package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.components.PlayerView
import com.example.ui.dialogs.AdBlockNoticeDialog
import com.example.ui.dialogs.OnboardingDialog
import com.example.ui.dialogs.PinLockDialog
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.KidHomeScreen
import com.example.ui.screens.ParentDashboardScreen
import com.example.ui.screens.SessionEndScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    YoungTubeApp()
                }
            }
        }
    }
}

@Composable
fun YoungTubeApp(viewModel: MainViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val settings by viewModel.settingsState.collectAsStateWithLifecycle()
    val feedVideos by viewModel.filteredFeedVideos.collectAsStateWithLifecycle()
    val channels by viewModel.allChannels.collectAsStateWithLifecycle()
    val favoriteVideos by viewModel.favoriteVideos.collectAsStateWithLifecycle()
    val savedVideos by viewModel.savedVideos.collectAsStateWithLifecycle()
    val hiddenVideos by viewModel.hiddenVideos.collectAsStateWithLifecycle()
    val blockedWords by viewModel.blockedWords.collectAsStateWithLifecycle()
    val todayUsage by viewModel.todayUsage.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val isPinModalVisible by viewModel.isPinModalVisible.collectAsStateWithLifecycle()
    val showOnboarding by viewModel.showOnboarding.collectAsStateWithLifecycle()
    val showAdBlockNotice by viewModel.showAdBlockNotice.collectAsStateWithLifecycle()
    val dashboardSection by viewModel.dashboardSection.collectAsStateWithLifecycle()

    // Handle Android system back gesture / button
    BackHandler(enabled = currentScreen !is AppScreen.KidFeed) {
        when (currentScreen) {
            is AppScreen.Player -> viewModel.navigateTo(AppScreen.KidFeed)
            is AppScreen.Favorites -> viewModel.navigateTo(AppScreen.KidFeed)
            is AppScreen.ParentDashboard -> viewModel.navigateTo(AppScreen.KidFeed)
            is AppScreen.SessionLimitReached -> {
                // Keep session limit screen locked until parents unlock
            }
            AppScreen.KidFeed -> {}
        }
    }

    // Main Screen Switcher
    when (val screen = currentScreen) {
        is AppScreen.KidFeed -> {
            KidHomeScreen(
                childName = settings?.childName ?: "",
                videos = feedVideos,
                channels = channels,
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.setCategory(it) },
                searchQuery = searchQuery,
                onSearchQueryChanged = { viewModel.setSearchQuery(it) },
                favoriteCount = favoriteVideos.size,
                onOpenFavorites = { viewModel.navigateTo(AppScreen.Favorites) },
                onOpenParentLock = { viewModel.showPinModal(true) },
                onPlayVideo = { video -> viewModel.playVideo(video) },
                onToggleFavorite = { vId, isFav -> viewModel.toggleVideoFavorite(vId, isFav) }
            )
        }

        is AppScreen.Favorites -> {
            FavoritesScreen(
                favorites = favoriteVideos,
                channels = channels,
                onBack = { viewModel.navigateTo(AppScreen.KidFeed) },
                onPlayVideo = { video -> viewModel.playVideo(video) },
                onToggleFavorite = { vId, isFav -> viewModel.toggleVideoFavorite(vId, isFav) }
            )
        }

        is AppScreen.Player -> {
            val channel = channels.find { it.sourceId == screen.video.channelId }
            val channelTitle = channel?.title ?: "قناة أطفال"

            PlayerView(
                video = screen.video,
                channelTitle = channelTitle,
                queue = feedVideos,
                onClose = { viewModel.navigateTo(AppScreen.KidFeed) },
                onSelectNextVideo = { next -> viewModel.playVideo(next) },
                onToggleFavorite = { isFav -> viewModel.toggleVideoFavorite(screen.video.videoId, isFav) },
                onToggleSaved = { isSaved -> viewModel.toggleVideoSaved(screen.video.videoId, isSaved) },
                onHideVideo = {
                    viewModel.hideVideo(screen.video.videoId)
                    viewModel.navigateTo(AppScreen.KidFeed)
                },
                onBlockChannel = {
                    viewModel.setChannelEnabled(screen.video.channelId, false)
                    viewModel.navigateTo(AppScreen.KidFeed)
                },
                onPlayingStateChanged = { isPlaying ->
                    viewModel.setPlayerPlaying(isPlaying)
                }
            )
        }

        is AppScreen.ParentDashboard -> {
            ParentDashboardScreen(
                settings = settings,
                channels = channels,
                blockedWords = blockedWords,
                savedVideos = savedVideos,
                hiddenVideos = hiddenVideos,
                todayUsage = todayUsage,
                activeSection = dashboardSection,
                onSelectSection = { viewModel.setDashboardSection(it) },
                onCloseDashboard = { viewModel.navigateTo(AppScreen.KidFeed) },
                onLockDashboard = { viewModel.navigateTo(AppScreen.KidFeed) },
                onOpenAdBlockNotice = { viewModel.showAdBlockModal(true) },
                onUpdateChildProfile = { name, age -> viewModel.updateChildProfile(name, age) },
                onUpdateTimer = { limit, start, end -> viewModel.updateTimerSettings(limit, start, end) },
                onResetTodayUsage = { viewModel.resetTodayUsage() },
                onToggleChannel = { id, enabled -> viewModel.setChannelEnabled(id, enabled) },
                onAddBlockedWord = { word -> viewModel.addBlockedWord(word) },
                onRemoveBlockedWord = { word -> viewModel.removeBlockedWord(word) },
                onUnhideVideo = { vId -> viewModel.unhideVideo(vId) },
                onUnsaveVideo = { vId -> viewModel.toggleVideoSaved(vId, false) },
                onToggleHideMusic = { hide -> viewModel.setHideMusicVideos(hide) },
                onAddCustomChannel = { id, title, cat -> viewModel.addCustomChannel(id, title, cat) },
                onPlayVideo = { video -> viewModel.playVideo(video) }
            )
        }

        is AppScreen.SessionLimitReached -> {
            SessionEndScreen(
                onOpenParentLock = { viewModel.showPinModal(true) },
                onResetTodayUsage = { viewModel.resetTodayUsage() }
            )
        }
    }

    // PIN Lock Dialog (for entering dashboard or overriding session limit)
    PinLockDialog(
        isOpen = isPinModalVisible,
        onDismiss = { viewModel.showPinModal(false) },
        onVerifyPin = { pin -> viewModel.verifyPin(pin) },
        securityQuestion = settings?.securityQuestion,
        onRecoverPin = { answer, newPin -> viewModel.recoverPinWithAnswer(answer, newPin) },
        onUnlockedSuccess = {
            viewModel.showPinModal(false)
            viewModel.navigateTo(AppScreen.ParentDashboard)
        }
    )

    // First-run Onboarding Dialog (PIN Setup)
    OnboardingDialog(
        isOpen = showOnboarding,
        onSaveSettings = { pin, question, answer ->
            viewModel.saveOnboardingSettings(pin, question, answer)
        }
    )

    // AdBlock Guidance Notice Dialog
    AdBlockNoticeDialog(
        isOpen = showAdBlockNotice,
        onDismiss = { viewModel.showAdBlockModal(false) }
    )
}

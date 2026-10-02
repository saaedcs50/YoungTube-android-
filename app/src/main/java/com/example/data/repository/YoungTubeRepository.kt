package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.model.BlockedWordEntity
import com.example.data.model.ChannelEntity
import com.example.data.model.SettingsEntity
import com.example.data.model.UsageEntity
import com.example.data.model.VideoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class YoungTubeRepository(private val database: AppDatabase) {

    private val settingsDao = database.settingsDao()
    private val channelDao = database.channelDao()
    private val videoDao = database.videoDao()
    private val blockedWordDao = database.blockedWordDao()
    private val usageDao = database.usageDao()

    fun getSettings(): Flow<SettingsEntity?> = settingsDao.getSettings()

    suspend fun getSettingsSync(): SettingsEntity? = withContext(Dispatchers.IO) {
        settingsDao.getSettingsSync()
    }

    suspend fun upsertSettings(settings: SettingsEntity) = withContext(Dispatchers.IO) {
        settingsDao.upsertSettings(settings)
    }

    fun getVisibleVideos(): Flow<List<VideoEntity>> = videoDao.getVisibleVideos()

    fun getFavoriteVideos(): Flow<List<VideoEntity>> = videoDao.getFavoriteVideos()

    fun getSavedVideos(): Flow<List<VideoEntity>> = videoDao.getSavedByParentVideos()

    fun getHiddenVideos(): Flow<List<VideoEntity>> = videoDao.getHiddenVideos()

    fun getAllChannels(): Flow<List<ChannelEntity>> = channelDao.getAllChannels()

    fun getEnabledChannels(): Flow<List<ChannelEntity>> = channelDao.getEnabledChannels()

    fun getAllBlockedWords(): Flow<List<BlockedWordEntity>> = blockedWordDao.getAllBlockedWords()

    fun getTodayUsage(): Flow<UsageEntity?> {
        val today = getTodayDateStr()
        return usageDao.getUsageForDate(today)
    }

    suspend fun setVideoFavorite(videoId: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        videoDao.setVideoFavorite(videoId, isFavorite, System.currentTimeMillis())
    }

    suspend fun setVideoSaved(videoId: String, isSaved: Boolean) = withContext(Dispatchers.IO) {
        videoDao.setVideoSavedByParent(videoId, isSaved)
    }

    suspend fun setVideoHidden(videoId: String, isHidden: Boolean) = withContext(Dispatchers.IO) {
        videoDao.setVideoHidden(videoId, isHidden)
    }

    suspend fun setChannelEnabled(channelId: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        channelDao.setChannelEnabled(channelId, enabled)
        if (!enabled) {
            videoDao.hideAllVideosForChannel(channelId)
        }
    }

    suspend fun addBlockedWord(word: String) = withContext(Dispatchers.IO) {
        val clean = word.trim()
        if (clean.isNotEmpty()) {
            blockedWordDao.insertWord(BlockedWordEntity(clean))
        }
    }

    suspend fun removeBlockedWord(word: String) = withContext(Dispatchers.IO) {
        blockedWordDao.deleteWord(word.trim())
    }

    suspend fun addCustomChannel(sourceId: String, title: String, categories: String) = withContext(Dispatchers.IO) {
        val channel = ChannelEntity(
            sourceId = sourceId.trim(),
            title = title.trim(),
            categories = categories.trim(),
            isPreloaded = false,
            enabled = true
        )
        channelDao.insertChannel(channel)
    }

    suspend fun incrementUsage(seconds: Int) = withContext(Dispatchers.IO) {
        val today = getTodayDateStr()
        val current = usageDao.getUsageForDateSync(today)
        val newTotal = (current?.secondsUsedToday ?: 0) + seconds
        usageDao.upsertUsage(UsageEntity(date = today, secondsUsedToday = newTotal))
    }

    suspend fun resetTodayUsage() = withContext(Dispatchers.IO) {
        val today = getTodayDateStr()
        usageDao.resetUsageForDate(today)
    }

    suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        val channelCount = channelDao.getCount()
        if (channelCount == 0) {
            channelDao.insertChannels(CuratedSeedData.initialChannels)
        }

        val videoCount = videoDao.getCount()
        if (videoCount == 0) {
            videoDao.insertVideos(CuratedSeedData.initialVideos)
        }

        val existingSettings = settingsDao.getSettingsSync()
        if (existingSettings == null) {
            settingsDao.upsertSettings(SettingsEntity(id = "main"))
        }

        for (bw in CuratedSeedData.initialBlockedWords) {
            blockedWordDao.insertWord(bw)
        }
    }

    fun getTodayDateStr(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }
}

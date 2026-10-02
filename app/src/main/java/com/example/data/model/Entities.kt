package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: String = "main",
    val pinHash: String? = null,
    val securityQuestion: String? = null,
    val securityAnswerHash: String? = null,
    val childName: String = "",
    val childAge: Int? = null,
    val sessionLimitMinutes: Int = 60,
    val scheduleStart: String = "00:00",
    val scheduleEnd: String = "23:59",
    val hideMusicVideos: Boolean = false,
    val enabledOptInGaming: Boolean = false,
    val tasteShiftEnabled: Boolean = false,
    val tasteShiftTargetCategories: String = "science,education,quran",
    val hasCompletedFirstSetup: Boolean = false
)

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey
    val sourceId: String,
    val sourceType: String = "channel", // "channel" or "playlist"
    val title: String,
    val thumbnail: String? = null,
    val categories: String = "", // Comma-separated: "stories,shows"
    val isPreloaded: Boolean = true,
    val enabled: Boolean = true
)

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey
    val videoId: String,
    val channelId: String,
    val title: String,
    val thumbnail: String? = null,
    val durationSeconds: Int = 0,
    val hasMusic: Boolean = true,
    val viewCountText: String? = null,
    val isFavorite: Boolean = false,
    val isSavedByParent: Boolean = false,
    val isHidden: Boolean = false,
    val lastWatchedTimestamp: Long = 0L,
    val primaryCategory: String = "all"
)

@Entity(tableName = "blocked_words")
data class BlockedWordEntity(
    @PrimaryKey
    val word: String
)

@Entity(tableName = "usage")
data class UsageEntity(
    @PrimaryKey
    val date: String, // YYYY-MM-DD
    val secondsUsedToday: Int = 0
)

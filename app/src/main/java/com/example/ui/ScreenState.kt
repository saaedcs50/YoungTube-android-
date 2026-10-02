package com.example.ui

import com.example.data.model.VideoEntity

sealed interface AppScreen {
    data object KidFeed : AppScreen
    data object Favorites : AppScreen
    data class Player(val video: VideoEntity) : AppScreen
    data object ParentDashboard : AppScreen
    data object SessionLimitReached : AppScreen
}

enum class DashboardSection {
    CHILD_PROFILE,
    TIMER_SCHEDULE,
    CHANNELS_CURATION,
    CONTENT_FILTERING,
    SAVED_VIDEOS,
    TASTE_SHIFT
}

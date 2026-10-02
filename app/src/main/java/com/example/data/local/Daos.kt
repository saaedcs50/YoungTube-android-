package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.BlockedWordEntity
import com.example.data.model.ChannelEntity
import com.example.data.model.SettingsEntity
import com.example.data.model.UsageEntity
import com.example.data.model.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 'main' LIMIT 1")
    fun getSettings(): Flow<SettingsEntity?>

    @Query("SELECT * FROM settings WHERE id = 'main' LIMIT 1")
    suspend fun getSettingsSync(): SettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSettings(settings: SettingsEntity)
}

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channels ORDER BY title ASC")
    fun getAllChannels(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels WHERE enabled = 1 ORDER BY title ASC")
    fun getEnabledChannels(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels WHERE sourceId = :id LIMIT 1")
    suspend fun getChannelById(id: String): ChannelEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChannels(channels: List<ChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: ChannelEntity)

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

    @Query("UPDATE channels SET enabled = :enabled WHERE sourceId = :id")
    suspend fun setChannelEnabled(id: String, enabled: Boolean)

    @Query("SELECT COUNT(*) FROM channels")
    suspend fun getCount(): Int
}

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos WHERE isHidden = 0 ORDER BY lastWatchedTimestamp DESC, videoId DESC")
    fun getVisibleVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE isFavorite = 1 AND isHidden = 0 ORDER BY lastWatchedTimestamp DESC")
    fun getFavoriteVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE isSavedByParent = 1 ORDER BY lastWatchedTimestamp DESC")
    fun getSavedByParentVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE isHidden = 1 ORDER BY lastWatchedTimestamp DESC")
    fun getHiddenVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE videoId = :id LIMIT 1")
    suspend fun getVideoById(id: String): VideoEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVideo(video: VideoEntity)

    @Query("UPDATE videos SET isFavorite = :isFavorite, lastWatchedTimestamp = :timestamp WHERE videoId = :id")
    suspend fun setVideoFavorite(id: String, isFavorite: Boolean, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE videos SET isSavedByParent = :isSaved WHERE videoId = :id")
    suspend fun setVideoSavedByParent(id: String, isSaved: Boolean)

    @Query("UPDATE videos SET isHidden = :isHidden WHERE videoId = :id")
    suspend fun setVideoHidden(id: String, isHidden: Boolean)

    @Query("UPDATE videos SET isHidden = 1 WHERE channelId = :channelId")
    suspend fun hideAllVideosForChannel(channelId: String)

    @Query("SELECT COUNT(*) FROM videos")
    suspend fun getCount(): Int
}

@Dao
interface BlockedWordDao {
    @Query("SELECT * FROM blocked_words ORDER BY word ASC")
    fun getAllBlockedWords(): Flow<List<BlockedWordEntity>>

    @Query("SELECT word FROM blocked_words")
    suspend fun getBlockedWordStrings(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: BlockedWordEntity)

    @Query("DELETE FROM blocked_words WHERE word = :word")
    suspend fun deleteWord(word: String)
}

@Dao
interface UsageDao {
    @Query("SELECT * FROM usage WHERE date = :date LIMIT 1")
    fun getUsageForDate(date: String): Flow<UsageEntity?>

    @Query("SELECT * FROM usage WHERE date = :date LIMIT 1")
    suspend fun getUsageForDateSync(date: String): UsageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUsage(usage: UsageEntity)

    @Query("UPDATE usage SET secondsUsedToday = 0 WHERE date = :date")
    suspend fun resetUsageForDate(date: String)
}

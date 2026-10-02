package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.BlockedWordEntity
import com.example.data.model.ChannelEntity
import com.example.data.model.SettingsEntity
import com.example.data.model.UsageEntity
import com.example.data.model.VideoEntity

@Database(
    entities = [
        SettingsEntity::class,
        ChannelEntity::class,
        VideoEntity::class,
        BlockedWordEntity::class,
        UsageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
    abstract fun channelDao(): ChannelDao
    abstract fun videoDao(): VideoDao
    abstract fun blockedWordDao(): BlockedWordDao
    abstract fun usageDao(): UsageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "youngtube.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

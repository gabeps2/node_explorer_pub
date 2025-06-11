package com.gbrl.dev.lightningnode.repository.feature.common.impl

import android.content.Context
import androidx.core.content.edit
import com.gbrl.dev.lightningnode.repository.feature.common.PreferencesRepository

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override suspend fun getLastFetchTime(): Long {
        return preferences.getLong(LAST_FETCH_TIME_KEY, 0L)
    }

    override suspend fun setLastFetchTime(timestamp: Long) {
        preferences.edit { putLong(LAST_FETCH_TIME_KEY, timestamp) }
    }

    private companion object {
        const val LAST_FETCH_TIME_KEY = "last_fetch_time"
        const val PREFERENCES = "preferences"
    }
}
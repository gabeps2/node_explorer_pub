package com.gbrl.dev.lightningnode.repository.feature.common

interface PreferencesRepository {
    suspend fun getLastFetchTime(): Long
    suspend fun setLastFetchTime(timestamp: Long)
}
package com.gbrl.dev.lightningnode.networking.provider.okhttp

import com.gbrl.dev.lightningnode.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun buildHttpClient(): OkHttpClient =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

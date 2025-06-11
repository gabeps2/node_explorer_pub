package com.gbrl.dev.lightningnode.networking.provider.retrofit

import com.gbrl.dev.lightningnode.networking.provider.okhttp.buildHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun buildRetrofit(
    host: String,
): Retrofit = retrofitClient(
    host = host,
    httpClient = buildHttpClient(),
)

fun retrofitClient(
    host: String,
    httpClient: OkHttpClient,
): Retrofit = Retrofit
    .Builder()
    .baseUrl(host)
    .client(httpClient)
    .build()

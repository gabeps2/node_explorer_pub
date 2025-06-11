package com.gbrl.dev.lightningnode.networking.provider.retrofit

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url

interface RetrofitService {
    @GET
    suspend fun getRequest(
        @Url path: String,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>
}
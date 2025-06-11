package com.gbrl.dev.lightningnode.networking.engine

import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.gbrl.dev.lightningnode.networking.provider.retrofit.RetrofitService
import com.gbrl.dev.lightningnode.networking.provider.retrofit.impl.RequestContextImpl
import okhttp3.ResponseBody
import kotlin.reflect.KClass

class NetworkEngine(
    private val service: RetrofitService,
    private val defaultJsonSerializer: JsonSerializer
) {
    suspend fun <T : Any> getRequest(
        url: String,
        headers: Map<String, String>?,
        responseClass: KClass<T>,
        jsonSerializer: JsonSerializer? = null
    ): NetworkingResponse<T?> {
        return doRequest(
            requestMethod = HttpMethod.GET,
            url = url,
            headers = headers,
            responseClass = responseClass,
            jsonSerializer = jsonSerializer
        )
    }

    private suspend fun <T : Any> doRequest(
        requestMethod: HttpMethod,
        url: String,
        headers: Map<String, String>?,
        jsonSerializer: JsonSerializer? = null,
        responseClass: KClass<T>
    ): NetworkingResponse<T?> = RequestContextImpl(
        url = url,
        requestMethod = requestMethod,
        headers = headers.orEmpty()
    ).run {
        val response = service.call()

        val deserializedData =
            response.data?.deserialize(responseClass, jsonSerializer ?: defaultJsonSerializer)

        return NetworkingResponse(
            data = deserializedData,
            httpCode = response.httpCode
        )
    }

    private fun <T : Any> ResponseBody?.deserialize(
        clazz: KClass<T>,
        jsonSerializer: JsonSerializer
    ): T? {
        return this?.charStream()?.use {
            jsonSerializer.fromJson(it, clazz)
        }
    }
}


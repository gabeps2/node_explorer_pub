package com.gbrl.dev.lightningnode.networking.adapter

import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import kotlin.reflect.KClass

interface NetworkAdapter {
    suspend fun <T : Any> getRequest(
        url: String,
        headers: Map<String, String>? = null,
        responseClass: KClass<T>,
        jsonSerializer: JsonSerializer? = null
    ): NetworkingResponse<T?>
}
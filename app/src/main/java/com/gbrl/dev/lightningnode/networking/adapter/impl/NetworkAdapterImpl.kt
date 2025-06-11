package com.gbrl.dev.lightningnode.networking.adapter.impl

import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.gbrl.dev.lightningnode.networking.adapter.NetworkAdapter
import com.gbrl.dev.lightningnode.networking.engine.NetworkEngine
import kotlin.reflect.KClass

class NetworkAdapterImpl(
    private val networkEngine: NetworkEngine
) : NetworkAdapter {
    override suspend fun <T : Any> getRequest(
        url: String,
        headers: Map<String, String>?,
        responseClass: KClass<T>,
        jsonSerializer: JsonSerializer?
    ): NetworkingResponse<T?> =
        networkEngine.getRequest(
            url = url,
            headers = headers,
            responseClass = responseClass,
            jsonSerializer = jsonSerializer
        )
}
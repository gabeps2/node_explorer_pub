package com.gbrl.dev.lightningnode.networking.provider.serializer.impl

import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.google.gson.Gson
import java.io.Reader
import kotlin.reflect.KClass

class GsonSerializerImpl(
    private val gson: Gson
) : JsonSerializer {
    override fun toJson(obj: Any?): String? = obj?.let { gson.toJson(it) }

    override fun <T : Any> fromJson(
        charStream: Reader,
        responseClass: KClass<T>
    ): T? = runCatching {
        gson.fromJson(charStream, responseClass.javaObjectType)
    }.getOrElse {
        it.printStackTrace()
        null
    }

    override fun <T : Any> fromJson(
        json: String?,
        responseClass: KClass<T>
    ): T? = runCatching {
        gson.fromJson(json, responseClass.javaObjectType)
    }.getOrElse {
        it.printStackTrace()
        null
    }
}
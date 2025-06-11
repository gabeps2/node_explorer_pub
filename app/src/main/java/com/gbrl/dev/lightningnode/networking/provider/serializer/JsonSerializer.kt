package com.gbrl.dev.lightningnode.networking.provider.serializer

import java.io.Reader
import kotlin.reflect.KClass

interface JsonSerializer {
    fun toJson(obj: Any?): String?
    fun <T : Any> fromJson(charStream: Reader, responseClass: KClass<T>): T?
    fun <T : Any> fromJson(json: String?, responseClass: KClass<T>): T?
}
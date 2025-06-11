package com.gbrl.dev.lightningnode.networking.provider.retrofit.impl

import com.gbrl.dev.lightningnode.networking.engine.HttpMethod
import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import com.gbrl.dev.lightningnode.networking.provider.retrofit.RequestContext
import com.gbrl.dev.lightningnode.networking.provider.retrofit.RetrofitService
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class RequestContextImpl(
    val url: String,
    val headers: Map<String, String>,
    val requestMethod: HttpMethod
) : RequestContext {
    override suspend fun RetrofitService.call(): NetworkingResponse<ResponseBody?> =
        runCatching {
            doRequest()
                .throwIfUnsuccessful()
                .toNetworkResponse()
        }.getOrElse {
            throw it
        }

    private fun <T : Any> Response<T>.throwIfUnsuccessful(): Response<T> =
        takeIf {
            isSuccessful
        } ?: throw HttpException(this)

    private fun Response<ResponseBody>.toNetworkResponse(): NetworkingResponse<ResponseBody?> {
        return NetworkingResponse(
            data = body(),
            httpCode = code()
        )
    }

    private suspend fun RetrofitService.doRequest(): Response<ResponseBody> {
        return when (requestMethod) {
            HttpMethod.GET -> getRequest(url, headers)
        }
    }
}
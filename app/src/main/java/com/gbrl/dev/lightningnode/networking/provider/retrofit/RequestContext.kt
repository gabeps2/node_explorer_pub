package com.gbrl.dev.lightningnode.networking.provider.retrofit

import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import okhttp3.ResponseBody

interface RequestContext {
    suspend fun RetrofitService.call() : NetworkingResponse<ResponseBody?>
}
package com.gbrl.dev.lightningnode.networking.feature.node

import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse

interface NodeNetworking {
    suspend fun getNodes(): NetworkingResponse<List<*>?>
}
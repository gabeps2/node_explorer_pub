package com.gbrl.dev.lightningnode.networking.feature.node.impl

import com.gbrl.dev.lightningnode.networking.adapter.NetworkAdapter
import com.gbrl.dev.lightningnode.networking.feature.node.NodeNetworking
import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse

class NodeNetworkingImpl(
    private val networkAdapter: NetworkAdapter
) : NodeNetworking {
    override suspend fun getNodes(): NetworkingResponse<List<*>?> =
        networkAdapter.getRequest(
            url = NODES_URL,
            headers = mapOf(),
            responseClass = List::class
        )

    private companion object {
        const val NODES_URL = "https://mempool.space/api/v1/lightning/nodes/rankings/connectivity"
    }
}
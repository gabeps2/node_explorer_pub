package com.gbrl.dev.lightningnode.repository.feature.node.impl

import com.gbrl.dev.lightningnode.networking.feature.node.NodeNetworking
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.gbrl.dev.lightningnode.repository.Result
import com.gbrl.dev.lightningnode.repository.feature.common.PreferencesRepository
import com.gbrl.dev.lightningnode.repository.feature.node.NodeDTO
import com.gbrl.dev.lightningnode.repository.feature.node.NodeRepository
import com.gbrl.dev.lightningnode.repository.feature.node.toEntity
import com.gbrl.dev.lightningnode.repository.feature.node.toNodeDTO
import com.gbrl.dev.lightningnode.repository.map
import com.gbrl.dev.lightningnode.repository.notNull
import com.gbrl.dev.lightningnode.repository.result
import com.gbrl.dev.lightningnode.storage.feature.node.dao.NodeDao

class NodeRepositoryImpl(
    private val nodeNetworking: NodeNetworking,
    private val preferencesRepository: PreferencesRepository,
    private val nodeDao: NodeDao,
    private val jsonSerializer: JsonSerializer
) : NodeRepository {
    override suspend fun getNodes(invalidateCache: Boolean): Result<List<NodeDTO>> {
        val lastFetchTime = preferencesRepository.getLastFetchTime()
        val isCacheValid = !invalidateCache && lastFetchTime.lastFetchIsWithinLimit()

        if (isCacheValid) {
            val databaseResult = result { nodeDao.getTopNodesByConnectivity(NODE_LIST_SIZE_LIMIT) }
                .notNull()
                .map { nodes -> nodes.map { it.toNodeDTO() } }

            if (databaseResult.isSuccess) {
                val databaseList = databaseResult.toValue().orEmpty()
                if (databaseList.size == NODE_LIST_SIZE_LIMIT) {
                    return databaseResult
                }
            }
        }

        val remoteResult =
            result { nodeNetworking.getNodes().data }
                .notNull()
                .map {
                    it.mapNotNull { node ->
                        val nodeAsJson = jsonSerializer.toJson(node)
                        jsonSerializer.fromJson(nodeAsJson, NodeDTO::class)
                    }.run {
                        checkFavorites(this).apply {
                            updateCache(this)
                        }
                    }
                }

        return remoteResult
    }

    override suspend fun updateFavorite(
        isFavorite: Boolean,
        publicKey: String
    ): Result<Unit> = result {
        nodeDao.updateFavorite(isFavorite, publicKey)
    }

    override suspend fun getFavoriteNodes(): Result<List<NodeDTO>> = result {
        nodeDao.getFavoriteNodes().map { it.toNodeDTO() }
    }

    private suspend fun updateCache(nodeNetworkingModelList: List<NodeDTO>) {
        updateLastFetchTime()
        saveOnDatabase(nodeNetworkingModelList)
    }

    private suspend fun checkFavorites(nodeNetworkingModelList: List<NodeDTO>): List<NodeDTO> {
        val localFavorites = nodeDao.getFavoriteNodes().associateBy { it.publicKey }

        val mergedItems = nodeNetworkingModelList.map {
            val isFavorite = localFavorites[it.publicKey]?.isFavorite == true
            it.copy(isFavorite = isFavorite)
        }

        return mergedItems
    }

    private suspend fun saveOnDatabase(nodeNetworkingModelList: List<NodeDTO>) =
        nodeDao.insertAll(nodeNetworkingModelList.map { it.toEntity() })


    private suspend fun updateLastFetchTime() {
        preferencesRepository.setLastFetchTime(System.currentTimeMillis())
    }

    private fun Long.lastFetchIsWithinLimit(): Boolean {
        return System.currentTimeMillis() - this < TIME_LIMIT
    }

    private companion object {
        const val NODE_LIST_SIZE_LIMIT = 100
        const val TIME_LIMIT = 10 * 60 * 1000
    }
}
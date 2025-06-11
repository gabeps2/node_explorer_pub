package com.gbrl.dev.lightningnode.repository.feature.node

import com.gbrl.dev.lightningnode.repository.Result

interface NodeRepository {
    suspend fun getNodes(invalidateCache: Boolean) : Result<List<NodeDTO>>
    suspend fun updateFavorite(isFavorite: Boolean, publicKey: String) : Result<Unit>
    suspend fun getFavoriteNodes() : Result<List<NodeDTO>>
}
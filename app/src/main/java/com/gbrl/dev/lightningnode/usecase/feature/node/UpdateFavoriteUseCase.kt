package com.gbrl.dev.lightningnode.usecase.feature.node

import com.gbrl.dev.lightningnode.repository.Result
import com.gbrl.dev.lightningnode.repository.feature.node.NodeRepository
import com.gbrl.dev.lightningnode.usecase.UseCase

class UpdateFavoriteUseCase(
    private val nodeRepository: NodeRepository
) : UseCase<UpdateFavoriteUseCase.Params, Unit>() {
    override suspend fun run(params: Params): Result<Unit> =
        nodeRepository.updateFavorite(params.isFavorite, params.publicKey)

    data class Params(
        val isFavorite: Boolean,
        val publicKey: String
    )
}
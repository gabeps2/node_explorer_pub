package com.gbrl.dev.lightningnode.usecase.feature.node

import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.Result
import com.gbrl.dev.lightningnode.repository.feature.node.NodeRepository
import com.gbrl.dev.lightningnode.repository.result
import com.gbrl.dev.lightningnode.usecase.UseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.common.toUiModel

class GetFavoriteNodeUseCase(
    private val nodeRepository: NodeRepository
) : UseCase<Unit, List<NodeUiModel>>() {
    override suspend fun run(params: Unit): Result<List<NodeUiModel>> {
        val repositoryResult = nodeRepository.getFavoriteNodes()

        return result {
            if (repositoryResult.isSuccess) {
                repositoryResult.toValue()!!.map { it.toUiModel() }
            } else emptyList()
        }
    }
}
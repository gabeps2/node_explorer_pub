package com.gbrl.dev.lightningnode.usecase.feature.node

import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.Result
import com.gbrl.dev.lightningnode.repository.feature.node.NodeRepository
import com.gbrl.dev.lightningnode.repository.result
import com.gbrl.dev.lightningnode.usecase.UseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.common.toUiModel

class GetNodesUseCase(
    private val nodeRepository: NodeRepository
) : UseCase<Boolean, List<NodeUiModel>>() {

    override suspend fun run(invalidateCache: Boolean): Result<List<NodeUiModel>> {
        val result = nodeRepository.getNodes(invalidateCache)

        if (result.isSuccess) {
            return result {
                result.toValue()!!.map {
                    it.toUiModel()
                }
            }
        }

        result.toException()?.let { throw it } ?: throw Exception()
    }

    companion object {
        const val DATE_PATTERN = "MM/dd/yyyy HH:mm"
        const val BTC_VS_SATS = 100_000_000.0
        const val TO_MILLISECONDS = 1000

        const val PT_BR = "pt-BR"
        const val EN_US = "en"
    }
}
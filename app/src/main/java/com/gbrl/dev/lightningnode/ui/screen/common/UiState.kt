package com.gbrl.dev.lightningnode.ui.screen.common

import com.gbrl.dev.lightningnode.model.NodeUiModel

sealed class UiState {
    data class Failure(val error: Throwable) : UiState()
    data class Success(val nodeList: List<NodeUiModel>) : UiState()
    object Loading : UiState()
}

fun UiState.isLoading() = this == UiState.Loading
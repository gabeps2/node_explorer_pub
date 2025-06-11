package com.gbrl.dev.lightningnode.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.onFailure
import com.gbrl.dev.lightningnode.repository.onSuccess
import com.gbrl.dev.lightningnode.ui.screen.common.UiState
import com.gbrl.dev.lightningnode.usecase.feature.node.GetFavoriteNodeUseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.UpdateFavoriteUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNodesUseCase: GetNodesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getFavoriteNodeUseCase: GetFavoriteNodeUseCase,
) : ViewModel() {

    private val _uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

    private val _favoriteNodesUiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    val favoriteNodesUiState: State<UiState> get() = _favoriteNodesUiState

    fun getNodes(invalidateCache: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getNodesUseCase.invoke(invalidateCache)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    it.printStackTrace()
                    _uiState.value = UiState.Failure(it)
                }

            getFavoriteNodeUseCase.invoke(Unit)
                .onSuccess {
                    _favoriteNodesUiState.value = UiState.Success(it)
                }.onFailure {
                    it.printStackTrace()
                    _favoriteNodesUiState.value = UiState.Failure(it)
                }

        }
    }

    fun updateFavorite(
        isFavorite: Boolean,
        node: NodeUiModel
    ) {
        viewModelScope.launch {
            updateFavoriteUseCase(UpdateFavoriteUseCase.Params(isFavorite, node.publicKey))
        }
    }
}
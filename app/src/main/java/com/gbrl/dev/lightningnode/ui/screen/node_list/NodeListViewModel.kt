package com.gbrl.dev.lightningnode.ui.screen.node_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.onFailure
import com.gbrl.dev.lightningnode.repository.onSuccess
import com.gbrl.dev.lightningnode.ui.screen.common.UiState
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.UpdateFavoriteUseCase
import kotlinx.coroutines.launch

class NodeListViewModel(
    private val getNodesUseCase: GetNodesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    private val _uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    val uiState: State<UiState> get() = _uiState

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
        }
    }

    fun sortBy(list: List<NodeUiModel>, sortMethod: SortMethod, orderMethod: OrderMethod) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val sortedList = list.sortedBy { sortMethod.toSort(it) as Comparable<Any> }

            _uiState.value = UiState.Success(
                if (orderMethod == OrderMethod.DESC) sortedList.reversed() else sortedList
            )
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

    private fun SortMethod.toSort(node: NodeUiModel) =
        when (this) {
            SortMethod.CAPACITY -> node.capacityRaw
            SortMethod.CHANNELS -> node.channelsRaw
            SortMethod.FIRST_SEEN -> node.firstSeenRaw
            SortMethod.UPDATED_AT -> node.updatedAtRaw
            else -> node.channelsRaw
        }
}
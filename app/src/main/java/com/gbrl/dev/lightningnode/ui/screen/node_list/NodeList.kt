package com.gbrl.dev.lightningnode.ui.screen.node_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.components.container.node_card.NodeCard
import com.gbrl.dev.lightningnode.ui.components.container.node_card.NodeCardResizing
import com.gbrl.dev.lightningnode.ui.components.container.node_details.NodeDetails
import com.gbrl.dev.lightningnode.ui.components.header.Header
import com.gbrl.dev.lightningnode.ui.screen.common.Error
import com.gbrl.dev.lightningnode.ui.screen.common.Loading
import com.gbrl.dev.lightningnode.ui.screen.common.PullToRefresh
import com.gbrl.dev.lightningnode.ui.screen.common.UiState
import com.gbrl.dev.lightningnode.ui.theme.Colors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeList(
    viewModel: NodeListViewModel,
    onBackStep: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState = remember { viewModel.uiState }

    var orderMethod by remember { mutableStateOf(OrderMethod.DESC) }
    var sortMethod by remember { mutableStateOf(SortMethod.CHANNELS) }
    var selectedNode by remember { mutableStateOf<NodeUiModel?>(null) }

    val sheetState = rememberModalBottomSheetState()
    val sortSheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var showSortBottomSheet by remember { mutableStateOf(false) }

    val onError = @Composable {
        Error(
            title = stringResource(R.string.error_title),
            throwable = Throwable(),
            primaryButtonText = stringResource(R.string.error_try_again_text)
        ) {
            viewModel.getNodes(true)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getNodes()
    }

    LaunchedEffect(sortMethod, orderMethod) {
        when (val currentState = viewModel.uiState.value) {
            is UiState.Success -> {
                viewModel.sortBy(
                    currentState.nodeList,
                    sortMethod,
                    orderMethod
                )
            }

            else -> Unit
        }
    }

    PullToRefresh(
        isRefreshing = uiState.value == UiState.Loading,
        onRefresh = { viewModel.getNodes(true) }
    ) {
        Column {
            Header(
                label = stringResource(R.string.top_100_nodes_title),
                leftIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = Colors.darkGray500
                    )
                },
                rightIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showSortBottomSheet = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_sort),
                            contentDescription = null
                        )
                    }
                },
                onLeftIconClick = onBackStep
            )

            if (showBottomSheet) {
                ModalBottomSheet(
                    dragHandle = {},
                    shape = RoundedCornerShape(8.dp),
                    onDismissRequest = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    sheetState = sheetState
                ) {
                    selectedNode?.let {
                        NodeDetails(it)
                    }
                }
            }

            if (showSortBottomSheet) {
                ModalBottomSheet(
                    dragHandle = {},
                    shape = RoundedCornerShape(8.dp),
                    onDismissRequest = {
                        coroutineScope.launch {
                            sortSheetState.hide()
                        }.invokeOnCompletion {
                            if (!sortSheetState.isVisible) {
                                showSortBottomSheet = false
                            }
                        }
                    },
                    sheetState = sortSheetState
                ) {
                    SortBottomSheet(sortMethod, orderMethod) { it, orderBy ->
                        sortMethod = it
                        orderMethod = orderBy
                        showSortBottomSheet = false
                    }
                }
            }

            when (val currentState = uiState.value) {
                UiState.Loading -> Loading()
                is UiState.Failure -> onError()
                is UiState.Success -> NodeListContent(
                    nodes = currentState.nodeList,
                    onFavoriteClick = { isFavorite, node ->
                        viewModel.updateFavorite(isFavorite, node)
                    },
                    onDetailClick = {
                        coroutineScope.launch {
                            showBottomSheet = true
                            selectedNode = it
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NodeListContent(
    nodes: List<NodeUiModel>,
    onFavoriteClick: ((Boolean, NodeUiModel) -> Unit)? = null,
    onDetailClick: (NodeUiModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = nodes,
                key = { index, _ ->
                    index
                }
            ) { index, it ->
                NodeCard(
                    position = index + 1,
                    nodeCardResizing = NodeCardResizing.FILL,
                    node = it,
                    onFavoriteClick = { isFavorite ->
                        onFavoriteClick?.invoke(isFavorite, it)
                    }
                ) {
                    onDetailClick(it)
                }
            }
        }
    }
}
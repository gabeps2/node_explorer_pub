package com.gbrl.dev.lightningnode.ui.screen.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.components.banner.Banner
import com.gbrl.dev.lightningnode.ui.components.container.node_card.NodeCard
import com.gbrl.dev.lightningnode.ui.components.container.node_details.NodeDetails
import com.gbrl.dev.lightningnode.ui.components.header.Header
import com.gbrl.dev.lightningnode.ui.screen.common.Error
import com.gbrl.dev.lightningnode.ui.screen.common.Loading
import com.gbrl.dev.lightningnode.ui.screen.common.PullToRefresh
import com.gbrl.dev.lightningnode.ui.screen.common.UiState
import com.gbrl.dev.lightningnode.ui.screen.common.isLoading
import kotlinx.coroutines.launch

@VisibleForTesting
const val FIRST_FIVE_NODES = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel,
    onBannerClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getNodes()
    }

    val uiState = remember { viewModel.uiState }
    val favoriteNodesUiState = remember { viewModel.favoriteNodesUiState }

    val coroutineScope = rememberCoroutineScope()

    var selectedNode by remember { mutableStateOf<NodeUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val onError = @Composable {
        Error(
            title = stringResource(R.string.error_title),
            throwable = Throwable(),
            primaryButtonText = stringResource(R.string.error_try_again_text)
        ) {
            viewModel.getNodes(true)
        }
    }

    PullToRefresh(
        isRefreshing = uiState.value.isLoading(),
        onRefresh = { viewModel.getNodes(true) }
    ) {
        Column {
            Header(
                label = stringResource(R.string.node_explorer_title),
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

            when (val state = uiState.value) {
                UiState.Loading -> Loading()
                is UiState.Failure -> onError()
                is UiState.Success -> HomeContent(
                    nodes = state.nodeList,
                    onBannerClick = onBannerClick,
                    favoriteNodesUiState = favoriteNodesUiState.value,
                    onErrorCallback = { onError() },
                    onFavoriteClick = { isFavorite, node ->
                        viewModel.updateFavorite(
                            isFavorite,
                            node
                        )
                    }
                ) {
                    coroutineScope.launch {
                        showBottomSheet = true
                        selectedNode = it
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    nodes: List<NodeUiModel>,
    onBannerClick: () -> Unit,
    favoriteNodesUiState: UiState,
    onErrorCallback: @Composable () -> Unit,
    onFavoriteClick: ((Boolean, NodeUiModel) -> Unit)? = null,
    onNodeDetailsClick: (NodeUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Banner(
            modifier = Modifier.padding(vertical = 8.dp),
            title = stringResource(R.string.top_100_nodes_title),
            description = stringResource(R.string.top_100_nodes_description),
        ) {
            onBannerClick()
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.top_5_nodes_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )


        RenderUntil(
            modifier = Modifier.padding(top = 8.dp),
            numOfItems = FIRST_FIVE_NODES,
            nodes = nodes,
            onNodeFavoriteClick = onFavoriteClick
        ) {
            onNodeDetailsClick(it)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.favorites_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )

        Column(
            Modifier.padding(top = 8.dp)
        ) {
            when (favoriteNodesUiState) {
                UiState.Loading -> Loading()
                is UiState.Failure -> onErrorCallback()
                is UiState.Success -> {
                    if (favoriteNodesUiState.nodeList.isEmpty()) {
                        Text(stringResource(R.string.empty_favorite_list_message))
                    } else {
                        RenderUntil(
                            numOfItems = FIRST_FIVE_NODES,
                            nodes = favoriteNodesUiState.nodeList,
                            onNodeFavoriteClick = onFavoriteClick
                        ) {
                            onNodeDetailsClick(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@VisibleForTesting
fun RenderUntil(
    modifier: Modifier = Modifier,
    numOfItems: Int,
    nodes: List<NodeUiModel>,
    onNodeFavoriteClick: ((Boolean, NodeUiModel) -> Unit)? = null,
    onNodeDetailsClick: (NodeUiModel) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = nodes.take(numOfItems),
            key = { index, _ ->
                index
            }
        ) { index, node ->
            NodeCard(
                node = node,
                onFavoriteClick = { onNodeFavoriteClick?.invoke(it, node) }
            ) {
                onNodeDetailsClick(node)
            }
        }
    }
}

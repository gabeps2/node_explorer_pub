package com.gbrl.dev.lightningnode.ui.screen.node_list

import androidx.compose.runtime.Composable
import com.gbrl.dev.lightningnode.ui.navigation.LocalNavController
import org.koin.compose.koinInject

@Composable
fun NodeListRoute() {
    val viewModel: NodeListViewModel = koinInject()
    val navController = LocalNavController.current

    NodeList(
        viewModel,
        onBackStep = {
            navController.popBackStack()
        }
    )
}
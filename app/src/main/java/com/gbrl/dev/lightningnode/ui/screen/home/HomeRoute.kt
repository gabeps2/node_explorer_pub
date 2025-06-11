package com.gbrl.dev.lightningnode.ui.screen.home

import androidx.compose.runtime.Composable
import com.gbrl.dev.lightningnode.ui.navigation.LocalNavController
import com.gbrl.dev.lightningnode.ui.navigation.Routes
import org.koin.compose.koinInject

@Composable
fun HomeRoute() {
    val viewModel = koinInject<HomeViewModel>()
    val localNavController = LocalNavController.current

    Home(
        viewModel = viewModel,
    ) {
        localNavController.navigate(Routes.NODE_LIST)
    }
}

package com.gbrl.dev.lightningnode.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gbrl.dev.lightningnode.ui.screen.home.HomeRoute
import com.gbrl.dev.lightningnode.ui.screen.node_list.NodeListRoute

@Composable
fun NavController(
    navController: NavHostController = rememberNavController()
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController, startDestination = Routes.HOME) {
            composable(route = Routes.HOME) {
                HomeRoute()
            }

            composable(route = Routes.NODE_LIST) {
                NodeListRoute()
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}
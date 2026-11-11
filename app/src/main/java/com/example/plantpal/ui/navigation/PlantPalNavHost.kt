package com.example.plantpal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantpal.ui.edit.EditPlantScreen
import com.example.plantpal.ui.list.PlantListScreen

object Routes {
    const val ARG_PLANT_ID = "plantId"
    const val LIST = "plants"
    const val ADD = "plants/add"
    const val EDIT = "plants/edit/{$ARG_PLANT_ID}"

    fun edit(plantId: Long) = "plants/edit/$plantId"
}

@Composable
fun PlantPalNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            PlantListScreen(
                onAddPlant = { navController.navigate(Routes.ADD) },
                onPlantClick = { plantId -> navController.navigate(Routes.edit(plantId)) }
            )
        }
        composable(Routes.ADD) {
            EditPlantScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.EDIT,
            arguments = listOf(navArgument(Routes.ARG_PLANT_ID) { type = NavType.LongType })
        ) {
            EditPlantScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
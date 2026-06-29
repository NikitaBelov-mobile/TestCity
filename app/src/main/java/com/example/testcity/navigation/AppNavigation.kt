package com.example.testcity.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.testcity.feature.cities.CitiesScreenEntry
import com.example.testcity.feature.citydetail.CityDetailScreenEntry

@Composable
fun TestCityNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = CitiesRoute,
        modifier = modifier.fillMaxSize(),
    ) {
        composable<CitiesRoute> {
            CitiesScreenEntry(
                onNavigateToDetail = { cityId ->
                    navController.navigate(CityDetailRoute(cityId))
                },
            )
        }
        composable<CityDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<CityDetailRoute>()
            CityDetailScreenEntry(
                cityId = route.cityId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

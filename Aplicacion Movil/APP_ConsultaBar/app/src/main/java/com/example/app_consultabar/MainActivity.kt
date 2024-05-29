package com.example.app_consultabar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_consultabar.Interfaces.DetallesMesas
import com.example.app_consultabar.Interfaces.ImagenInicio
import com.example.app_consultabar.Interfaces.PantallaMesas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            ImagenInicio(navController)
        }
        composable("tables") {
            PantallaMesas(navController)
        }
        composable(
            "table/{tableId}",
            arguments = listOf(navArgument("tableId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tableId = backStackEntry.arguments?.getString("tableId")
            DetallesMesas(navController, tableId)
        }
    }
}
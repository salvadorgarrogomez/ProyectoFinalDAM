package com.example.app_consultabar.Interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_consultabar.R
import kotlinx.coroutines.delay

@Composable
fun ImagenInicio(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000) // Esperar 3 segundos
        navController.navigate("tables") {
            popUpTo("splash") { inclusive = true } // Eliminar la pantalla de splash de la pila de navegación
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.tpv), // Asegúrate de tener el logotipo en drawable
            contentDescription = "Logotipo TPV",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Bar ElEscobar", style = MaterialTheme.typography.titleLarge)
    }
}

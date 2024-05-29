package com.example.app_consultabar.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_consultabar.Services.ApiService

@Composable
fun DetallesMesas(navController: NavController, tableId: String?) {
    var numComensales by remember { mutableStateOf("") }
    var newProduct by remember { mutableStateOf("") }
    val products = remember { mutableStateListOf<String>() }
    val nombresProductos = remember { mutableStateListOf<String>() }
    var suggestions by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Lógica para obtener los nombres de productos de la API
    LaunchedEffect(Unit) {
        try {
            val response = ApiService.productService.obtenerProductos()
            if (response.isSuccessful) {
                val productos = response.body()
                productos?.let {
                    nombresProductos.addAll(it.map { producto -> producto.name })
                }
            }
        } catch (e: Exception) {
            // Manejar errores
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.popBackStack() }) {
            Text("Atrás")
        }
    }

    Column(modifier = Modifier.padding(5.dp)) {
        Text(text = "Detalles de la $tableId", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = numComensales,
            onValueChange = { numComensales = it },
            label = { Text("Número de Comensales") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box {
            Column {
                TextField(
                    value = newProduct,
                    onValueChange = { value ->
                        newProduct = value
                        expanded = true
                        suggestions = nombresProductos.filter {
                            it.startsWith(value, ignoreCase = true)
                        }
                    },
                    label = { Text("Nuevo Producto") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (newProduct.isNotEmpty()) {
                    products.add(newProduct)
                    newProduct = ""
                }
            }) {
                Text("Agregar Producto")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(products) { product ->
                Text(product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { /* Guardar cambios en la API */ }) {
                Text("Guardar")
            }
        }
    }
}



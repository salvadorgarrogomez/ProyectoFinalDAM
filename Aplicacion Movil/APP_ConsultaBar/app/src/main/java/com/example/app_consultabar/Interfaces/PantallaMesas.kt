package com.example.app_consultabar.Interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_consultabar.Models.Table

@Composable
fun PantallaMesas(navController: NavController) {
    val tables = listOf(
        Table(1, "Mesa 1", listOf()),
        Table(2, "Mesa 2", listOf()),
        Table(3, "Mesa 3", listOf()),
        Table(4, "Mesa 4", listOf()),
        Table(5, "Mesa 5", listOf()),
        Table(6, "Mesa 6", listOf()),
        Table(7, "Mesa 7", listOf()),
        Table(8, "Mesa 8", listOf()),
        Table(9, "Mesa 9", listOf()),
        Table(10, "Mesa 10", listOf()),
        Table(11, "Barra 1", listOf()),
        Table(12, "Barra 2", listOf()),
        Table(13, "Barra 3", listOf()),
        Table(14, "Barra 4", listOf()),
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tables) { table ->
            TableCard(table, navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TableCard(table: Table, navController: NavController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { navController.navigate("table/${table.name}") }
        .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = table.name)
        }
    }
}

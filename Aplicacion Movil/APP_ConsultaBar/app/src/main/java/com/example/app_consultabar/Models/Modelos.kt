package com.example.app_consultabar.Models

data class Table(
    val id: Int,
    val name: String,
    val products: List<Productos>
)

data class Productos(
    val id: Int,
    val name: String,
    val quantity: Int
)

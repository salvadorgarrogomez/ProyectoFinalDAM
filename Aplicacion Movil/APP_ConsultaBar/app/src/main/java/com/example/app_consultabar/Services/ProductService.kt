package com.example.app_consultabar.Services

import com.example.app_consultabar.Models.Productos
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("/productos")
    suspend fun obtenerProductos(): Response<List<Productos>>
}






package com.example.api_comandas.controladores;

import com.example.api_comandas.entidades.Productos;
import com.example.api_comandas.servicios.ProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductosControlador {

    private final ProductosServicio productosServicio;

    @Autowired
    public ProductosControlador(ProductosServicio productosServicio) {
        this.productosServicio = productosServicio;
    }

    @GetMapping("/productos")
    public List<String> obtenerNombresDeProductos() {
        return productosServicio.obtenerProductos();
    }
}

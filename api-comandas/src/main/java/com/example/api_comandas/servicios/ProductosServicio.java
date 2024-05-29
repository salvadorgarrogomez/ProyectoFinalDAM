package com.example.api_comandas.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.api_comandas.entidades.Productos;
import com.example.api_comandas.repositorios.ProductosRepositorio;

@Service
public class ProductosServicio {

    private final ProductosRepositorio productosRepositorio;

    @Autowired
    public ProductosServicio(ProductosRepositorio productosRepositorio) {
        this.productosRepositorio = productosRepositorio;
    }

    public List<String> obtenerProductos() {
        List<Productos> productos = productosRepositorio.findAll(Sort.by("id").ascending());
        return productos.stream()
                .map(producto -> producto.getId() + " - " + producto.getNombre())
                .collect(Collectors.toList());
    }
}
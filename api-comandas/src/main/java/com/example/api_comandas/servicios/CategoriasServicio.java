package com.example.api_comandas.servicios;

import com.example.api_comandas.entidades.Categorias;
import com.example.api_comandas.repositorios.CategoriasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriasServicio {

    private final CategoriasRepositorio categoriasRepositorio;

    @Autowired
    public CategoriasServicio(CategoriasRepositorio categoriasRepositorio) {
        this.categoriasRepositorio = categoriasRepositorio;
    }

    public List<String> obtenerNombresDeCategorias() {
        List<Categorias> categorias = categoriasRepositorio.findAll();
        return categorias.stream()
                .map(Categorias::getNombre) // Obtiene solo el nombre de cada categoría
                .collect(Collectors.toList());
    }
}

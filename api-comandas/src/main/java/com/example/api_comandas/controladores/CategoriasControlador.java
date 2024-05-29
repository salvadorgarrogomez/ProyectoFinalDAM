package com.example.api_comandas.controladores;

import com.example.api_comandas.servicios.CategoriasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriasControlador {

    private final CategoriasServicio categoriasServicio;

    @Autowired
    public CategoriasControlador(CategoriasServicio categoriasServicio) {
        this.categoriasServicio = categoriasServicio;
    }

    @GetMapping("/categorias/nombres")
    public List<String> obtenerNombresDeCategorias() {
        return categoriasServicio.obtenerNombresDeCategorias();
    }

}

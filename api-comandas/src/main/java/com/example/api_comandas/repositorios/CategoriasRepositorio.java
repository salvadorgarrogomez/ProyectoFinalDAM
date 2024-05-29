package com.example.api_comandas.repositorios;

import com.example.api_comandas.entidades.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasRepositorio extends JpaRepository<Categorias, Long> {
}


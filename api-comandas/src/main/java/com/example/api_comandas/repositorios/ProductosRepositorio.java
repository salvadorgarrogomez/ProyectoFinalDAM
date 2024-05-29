package com.example.api_comandas.repositorios;

import com.example.api_comandas.entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepositorio extends JpaRepository<Productos, Long>{
}

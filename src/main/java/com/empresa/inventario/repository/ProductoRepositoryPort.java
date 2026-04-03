package com.empresa.inventario.repository;

import com.empresa.inventario.models.Producto;

import java.util.List;

public interface ProductoRepositoryPort {
    List<Producto> findAll();
}

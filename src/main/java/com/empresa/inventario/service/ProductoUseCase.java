package com.empresa.inventario.service;

import com.empresa.inventario.models.Producto;

import java.util.List;

public interface ProductoUseCase {
    List<Producto> obtenerProductos();
}

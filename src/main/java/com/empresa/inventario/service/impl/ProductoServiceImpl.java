package com.empresa.inventario.service.impl;

import com.empresa.inventario.models.Producto;
import com.empresa.inventario.repository.ProductoRepositoryPort;
import com.empresa.inventario.service.ProductoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoUseCase {
    @Autowired
    private ProductoRepositoryPort productoRepositoryPort;

    @Override
    public List<Producto> obtenerProductos() {
        return productoRepositoryPort.findAll();
    }
}

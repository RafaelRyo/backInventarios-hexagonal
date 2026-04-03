package com.empresa.inventario.repository;

import com.empresa.inventario.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Producto toDomain(ProductoEntity entity) {
        Producto producto = new Producto();
        producto.setIdProducto(entity.getIdProducto());
        producto.setNombre(entity.getNombre());
        return producto;
    }

    private ProductoEntity toEntity(Producto producto) {
        ProductoEntity entity = new ProductoEntity();
        entity.setIdProducto(producto.getIdProducto());
        entity.setNombre(producto.getNombre());
        return entity;
    }
}

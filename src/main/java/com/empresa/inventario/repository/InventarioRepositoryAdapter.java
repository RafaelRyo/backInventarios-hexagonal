package com.empresa.inventario.repository;

import com.empresa.inventario.models.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InventarioRepositoryAdapter implements InventarioRepositoryPort {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Inventario save(Inventario inventario) {
        InventarioEntity entity = toEntity(inventario);
        InventarioEntity saved = inventarioRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Inventario> findById(String id) {
        return inventarioRepository.findById(id).map(this::toDomain);
    }

    private Inventario toDomain(InventarioEntity entity) {
        Inventario inventario = new Inventario();
        inventario.setId(entity.getId());
        inventario.setNombreUsuario(entity.getNombreUsuario());
        inventario.setProducto(entity.getProducto());
        inventario.setNumeroSerie(entity.getNumeroSerie());
        inventario.setFechaRegistro(entity.getFechaRegistro());
        inventario.setEstado(entity.getEstado());
        return inventario;
    }

    private InventarioEntity toEntity(Inventario inventario) {
        InventarioEntity entity = new InventarioEntity();
        entity.setId(inventario.getId());
        entity.setNombreUsuario(inventario.getNombreUsuario());
        entity.setProducto(inventario.getProducto());
        entity.setNumeroSerie(inventario.getNumeroSerie());
        entity.setFechaRegistro(inventario.getFechaRegistro());
        entity.setEstado(inventario.getEstado());
        return entity;
    }
}

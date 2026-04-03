package com.empresa.inventario.repository;

import com.empresa.inventario.models.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioRepositoryPort {
    List<Inventario> findAll();
    Inventario save(Inventario inventario);
    Optional<Inventario> findById(String id);
}

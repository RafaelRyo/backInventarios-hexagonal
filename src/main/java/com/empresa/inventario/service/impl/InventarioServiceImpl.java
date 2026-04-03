package com.empresa.inventario.service.impl;

import com.empresa.inventario.models.Inventario;
import com.empresa.inventario.repository.InventarioRepositoryPort;
import com.empresa.inventario.service.InventarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioUseCase {
    @Autowired
    private InventarioRepositoryPort inventarioRepositoryPort;

    @Override
    public List<Inventario> obtenerInventarios() {
        return inventarioRepositoryPort.findAll();
    }

    @Override
    public Inventario registrarInventario(Inventario inventario) {
        return inventarioRepositoryPort.save(inventario);
    }

    @Override
    public Inventario actualizarEstado(String id, String estado) {
        Inventario inventario = inventarioRepositoryPort.findById(id).orElseThrow(
                () -> new RuntimeException("Inventario no encontrado"));
        inventario.setEstado(estado);
        return inventarioRepositoryPort.save(inventario);
    }
}

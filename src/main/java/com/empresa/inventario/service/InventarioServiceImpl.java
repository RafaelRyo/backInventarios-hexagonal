package com.empresa.inventario.service;

import com.empresa.inventario.models.Inventario;
import com.empresa.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements IInventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> obtenerInventarios(){
        return inventarioRepository.findAll();
    }

    @Override
    public Inventario registrarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario actualizarEstado(String id, String estado) {
        Inventario inventario = inventarioRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Inventario no encontrado"));
        inventario.setEstado(estado);
        return inventarioRepository.save(inventario);
    }
}

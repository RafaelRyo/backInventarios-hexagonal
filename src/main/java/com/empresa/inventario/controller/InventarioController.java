package com.empresa.inventario.controller;

import com.empresa.inventario.models.Inventario;
import com.empresa.inventario.service.InventarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioUseCase inventarioUseCase;

    @GetMapping
    public List<Inventario> obtenerInventarios() {
        return inventarioUseCase.obtenerInventarios();
    }

    @PostMapping("/registrar")
    public Inventario registrarInventario(@RequestBody Inventario inventario) {
        return inventarioUseCase.registrarInventario(inventario);
    }

    @PutMapping("/actualizar-estado/{id}")
    public Inventario entregarProducto(@PathVariable String id, @RequestParam String estado) {
        return inventarioUseCase.actualizarEstado(id, estado);
    }
}

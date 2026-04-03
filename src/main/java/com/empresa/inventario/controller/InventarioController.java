package com.empresa.inventario.controller;

import com.empresa.inventario.models.Inventario;
import com.empresa.inventario.service.IInventarioService;
import com.empresa.inventario.service.InventarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private IInventarioService iInventarioService;

    @GetMapping
    public List<Inventario> obtenerInventarios(){
        return iInventarioService.obtenerInventarios();
    }

    @PostMapping("/registrar")
    public Inventario registrarInventario(@RequestBody Inventario inventario) {
        return iInventarioService.registrarInventario(inventario);
    }

    @PutMapping("/actualizar-estado/{id}")
    public Inventario entregarProducto(@PathVariable String id, @RequestParam String estado){
        return iInventarioService.actualizarEstado(id, estado);
    }
}

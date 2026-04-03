package com.empresa.inventario.service;

import com.empresa.inventario.models.Inventario;

import java.util.List;

public interface IInventarioService {

    List<Inventario> obtenerInventarios();
    Inventario registrarInventario(Inventario inventario);
    Inventario actualizarEstado(String id, String estado);
}

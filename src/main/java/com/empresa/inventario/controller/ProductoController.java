package com.empresa.inventario.controller;

import com.empresa.inventario.models.Producto;
import com.empresa.inventario.service.ProductoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoUseCase productoUseCase;

    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoUseCase.obtenerProductos();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
    }
}

package com.empresa.inventario.service;

import com.empresa.inventario.models.Producto;
import com.empresa.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }
}

package com.empresa.inventario.repository;

import com.empresa.inventario.models.Inventario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventarioRepository extends MongoRepository<Inventario, String> {
}

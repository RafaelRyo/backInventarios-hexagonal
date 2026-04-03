package com.empresa.inventario.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "inventario")
public class InventarioEntity {
    @Id
    private String id;

    private String nombreUsuario;

    private String producto;

    private String numeroSerie;

    private LocalDate fechaRegistro;

    private String estado;
}

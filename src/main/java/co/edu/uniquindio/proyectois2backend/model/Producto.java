package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {

    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private Double precio;
    private Integer stock;
    private String proveedor;
}

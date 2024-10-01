package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Servicio {

    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private Double precio;
    private String duracion;
    // TODO implementar las recomendaciones de productos o servicios adicionales recomendados por el estilista
}

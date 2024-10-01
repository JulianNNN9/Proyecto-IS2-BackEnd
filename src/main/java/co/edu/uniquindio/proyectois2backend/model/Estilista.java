package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estilista {

    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String especialidad;
    // TODO implementar agenda
    // TODO implementar relación con preferencias de clientes recurrentes
    // TODO implementar fotos de trabajos anteriores
}

package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resena {

    @EqualsAndHashCode.Include
    private String id;

    private Integer calificacion;
    private String comentario;
    private String idCliente;
    private String idCita;
}


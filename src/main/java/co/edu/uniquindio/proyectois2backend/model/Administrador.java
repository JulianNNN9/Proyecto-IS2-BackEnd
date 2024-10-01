package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Administrador {

    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String correo;
    private String contrasena;
}

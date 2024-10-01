package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private List<String> preferecias;

}

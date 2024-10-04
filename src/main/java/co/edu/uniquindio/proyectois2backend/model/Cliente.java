package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    @ElementCollection
    private List<String> preferecias;

}

package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Administrador")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Administrador {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nombre;
    private String correo;
    private String contrasena;
}

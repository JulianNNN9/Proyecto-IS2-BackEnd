package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Estilista")
public class Estilista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Column(nullable = false)
    private String correo;

    @OneToMany(mappedBy = "estilista", cascade = CascadeType.ALL)
    private List<TipoEspecialidadEstilista> tipoEspecialidadEstilistas;

    // TODO implementar agenda
    // TODO implementar relación con preferencias de clientes recurrentes
    // TODO implementar fotos de trabajos anteriores
}

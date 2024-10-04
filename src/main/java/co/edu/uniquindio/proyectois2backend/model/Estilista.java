package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Estilista")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estilista {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nombre;
    private String especialidad;
    // TODO implementar agenda
    // TODO implementar relación con preferencias de clientes recurrentes
    // TODO implementar fotos de trabajos anteriores
    @OneToMany(mappedBy = "estilista")
    private List<Cita> citas;
}

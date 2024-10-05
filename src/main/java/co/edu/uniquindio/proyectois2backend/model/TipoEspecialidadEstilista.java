package co.edu.uniquindio.proyectois2backend.model;


import jakarta.persistence.*;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TipoEspecialidadEstilista")
public class TipoEspecialidadEstilista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estilista_id", nullable = false)
    private Estilista estilista;

    @ManyToOne
    @JoinColumn(name = "tipo_especialidad_id", nullable = false)
    private TipoEspecialidad especialidad;

    @Override
    public String toString() {
        return "TipoEspecialidadEstilista{" +
                "id=" + id +
                ", especialidad=" + (especialidad != null ? especialidad.getNombre() : "N/A") +
                '}';
    }
}
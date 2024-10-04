package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.simplejavamail.api.internal.clisupport.model.Cli;

@Entity
@Table(name = "Resena")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resena {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Integer calificacion;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente idCliente;
    @ManyToOne
    @JoinColumn(name = "estilista_id")
    private Cita idCita;
}


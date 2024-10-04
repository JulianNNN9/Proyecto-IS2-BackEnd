package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Cita")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private LocalDateTime fecha;
    @ManyToMany
    @JoinTable(
            name = "cita_servicios",
            joinColumns = @JoinColumn(name = "cita_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<Servicio> idServicios;
    @OneToOne
    @JoinColumn(name = "detalle_pago")
    private Pago pago;
    private Boolean confirmacion;
    private EstadoCita estadoCita;
    private Double propina;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente idCliente;
    @ManyToOne
    @JoinColumn(name = "estilista_id")
    private Estilista idEstilista;
}

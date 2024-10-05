package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleServicioCita> detalleServicioCitas; // Lista de DetalleServicio en lugar de servicios

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleProductoCita> detalleProductoCitas; // Nueva relación con productos


    @NotNull
    @Column(nullable = false)
    private Double totalPago;

    @NotNull
    @Column(nullable = false)
    private Boolean confirmacion;

    @NotNull
    @Column(nullable = false)
    private EstadoCita estadoCita;

    private Double propina;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "estilista_id")
    @NotNull
    private Estilista estilista;

    @NotNull
    @Column(nullable = false)
    private String direccion;
}

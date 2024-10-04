package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cita")
@Data
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double totalPago;
    private Boolean confirmacion;

    @ManyToOne
    @JoinColumn(name = "id_estado_cita", nullable = false)
    private EstadoCita estadoCita;

    private Double propina;
    private String comentario;
    private String idCliente;
    private String idEstilista;
    private Boolean recordatorioEnviado;
}

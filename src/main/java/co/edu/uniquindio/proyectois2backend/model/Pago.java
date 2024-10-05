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
@Table(name = "Pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;

    @ElementCollection
    private List<String> detallePago;

    @Column(nullable = false)
    private LocalDateTime fechaPago; // Fecha y hora del pago

    @NotNull
    @Column(nullable = false)
    private EstadoPago estadoPago;

    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita Cita;
}

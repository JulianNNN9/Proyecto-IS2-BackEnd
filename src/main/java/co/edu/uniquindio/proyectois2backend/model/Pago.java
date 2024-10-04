package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Pago")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Double total;
    @ElementCollection
    private List<String> detallePago;
    private EstadoPago estadoPago;
    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}

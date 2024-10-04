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
    private String detallePago;
    private EstadoPago estadoPago;
    // TODO ¿Implementar pasarela?
}

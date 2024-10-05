package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "DetalleProductoCita")
public class DetalleProductoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull
    @Column(nullable = false)
    private Integer cantidad; // Cantidad de productos comprados

    @NotNull
    @Column(nullable = false)
    private Double precio; // Precio unitario del producto en el momento de la compra
}
package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "DetalleServicioCita")
public class DetalleServicioCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @NotNull
    @Column(nullable = false)
    private Double precio; // Precio unitario del producto en el momento de la compra

    @Override
    public String toString() {
        return "DetalleServicioCita{" +
                "id=" + id +
                ", cita=" + (cita != null ? cita.getId() : "N/A") +
                ", servicio=" + (servicio != null ? servicio.getNombre() : "N/A") +
                ", precio=" + precio +
                '}';
    }
}
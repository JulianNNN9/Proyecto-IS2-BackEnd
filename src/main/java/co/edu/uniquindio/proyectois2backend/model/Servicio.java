package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Column(nullable = false)
    private Double precio;

    @NotNull
    @Column(nullable = false)
    private Integer duracion;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<DetalleServicioCita> detalleServicios; // Relación inversa con DetalleServicio
    // TODO implementar las recomendaciones de productos o servicios adicionales recomendados por el estilista

}

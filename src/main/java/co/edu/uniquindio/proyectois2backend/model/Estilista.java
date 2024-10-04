package co.edu.uniquindio.proyectois2backend.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estilista")
@Data
@Builder
public class Estilista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especialidad;
    // TODO implementar agenda
    // TODO implementar relación con preferencias de clientes recurrentes
    // TODO implementar fotos de trabajos anteriores
}

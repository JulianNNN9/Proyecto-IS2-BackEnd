package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita {

    @EqualsAndHashCode.Include
    private String id;

    private LocalDateTime fecha;
    private List<String> idServicios;
    private Double totalPago;
    private Boolean confirmacion;
    private EstadoCita estadoCita;
    //Implementar funcionalidad recordatorio
    private Double propina;
    private String comentario;
    private String idCliente;
    private String idEstilista;
}

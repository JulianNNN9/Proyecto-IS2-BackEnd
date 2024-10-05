package co.edu.uniquindio.proyectois2backend.dto.cita;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CrearCitaDTO (
        @NotNull(message = "La fecha y hora son requeridas")
        String fecha,

        @NotNull(message = "Los detalles del servicio son requeridos")
        List<DetalleServicioCitaDTO> detalleServicioCitaDTOS,

        List<DetalleProductoCitaDTO> detalleProductoCitaDTOS,

        @NotNull(message = "El cliente es requerido")
        Long clienteId,

        @NotNull(message = "El estilista es requerido")
        Long estilistaId
){}



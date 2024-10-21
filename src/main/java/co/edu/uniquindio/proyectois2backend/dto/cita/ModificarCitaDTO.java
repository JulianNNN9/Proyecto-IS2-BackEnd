package co.edu.uniquindio.proyectois2backend.dto.cita;

import java.util.List;

public record ModificarCitaDTO(
        String fecha,
        List<DetalleServicioCitaDTO> detalleServicioCitaDTOS,
        List<DetalleProductoCitaDTO> detalleProductoCitaDTOS,
        Long estilistaId
) {}

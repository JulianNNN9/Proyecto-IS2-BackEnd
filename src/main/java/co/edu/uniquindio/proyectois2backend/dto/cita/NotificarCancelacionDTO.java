package co.edu.uniquindio.proyectois2backend.dto.cita;

import java.time.LocalDateTime;

public record NotificarCancelacionDTO(
        String nombreCliente,
        LocalDateTime fecha
) {
}

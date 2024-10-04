package co.edu.uniquindio.proyectois2backend.dto.cita;

import java.time.LocalDateTime;

public record RecordatorioDTO(
        String idCita,
        LocalDateTime fechaCita,
        String correoCliente
) {
}

package co.edu.uniquindio.proyectois2backend.dto.cita;

import java.time.LocalDateTime;

public record NotificarReprogramacionDTO(
        String nombreCliente,
        LocalDateTime fechaCitaAntigua,
        LocalDateTime FechaCitaNueva
) {
}

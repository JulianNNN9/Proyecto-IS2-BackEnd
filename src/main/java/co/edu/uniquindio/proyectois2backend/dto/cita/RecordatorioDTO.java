package co.edu.uniquindio.proyectois2backend.dto.cita;

public record RecordatorioDTO(
        String nombreCliente,
        String servicio,
        String fechaCita,
        String horaCita,
        String nombreEstilista,
        String direccionPeluqueria,
        String telefonoPeluqueria
) {
}

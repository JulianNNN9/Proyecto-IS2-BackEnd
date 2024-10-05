package co.edu.uniquindio.proyectois2backend.dto.cita;


public record ConfirmacionDTO(
        String nombreCliente,
        String servicio,
        String fechaCita,
        String horaCita,
        String nombreEstilista,
        String direccion,
        String telefonoPeluqueria
) {
}

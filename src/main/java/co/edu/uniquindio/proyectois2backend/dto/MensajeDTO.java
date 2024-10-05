package co.edu.uniquindio.proyectois2backend.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}


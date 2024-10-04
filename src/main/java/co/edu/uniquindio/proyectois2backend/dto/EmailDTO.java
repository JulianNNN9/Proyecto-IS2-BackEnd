package co.edu.uniquindio.proyectois2backend.dto;

public record EmailDTO (
    String asunto,
    String cuerpo,
    String destinatario
) {
}

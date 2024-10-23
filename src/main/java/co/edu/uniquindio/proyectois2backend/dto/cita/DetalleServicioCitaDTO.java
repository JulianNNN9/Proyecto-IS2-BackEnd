package co.edu.uniquindio.proyectois2backend.dto.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DetalleServicioCitaDTO(

        @NotBlank @NotNull
        Long id,
        @NotBlank @NotNull
        String nombre,
        @NotBlank @NotNull
        double precio

){}



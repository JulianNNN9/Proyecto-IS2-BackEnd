package co.edu.uniquindio.proyectois2backend.dto.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DetalleServicioCitaDTO(

        @NotBlank @NotNull
        Long idServicio,
        @NotBlank @NotNull
        double precio

){}



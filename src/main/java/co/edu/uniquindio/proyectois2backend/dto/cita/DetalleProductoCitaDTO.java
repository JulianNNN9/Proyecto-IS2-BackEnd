package co.edu.uniquindio.proyectois2backend.dto.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DetalleProductoCitaDTO(


        @NotBlank @NotNull
        Long idProducto,
        @NotBlank @NotNull
        int cantidad,
        @NotBlank @NotNull
        double precio

){}



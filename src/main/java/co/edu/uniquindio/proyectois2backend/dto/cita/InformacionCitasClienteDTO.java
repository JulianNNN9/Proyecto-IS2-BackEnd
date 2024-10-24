package co.edu.uniquindio.proyectois2backend.dto.cita;


import java.util.List;

public record InformacionCitasClienteDTO(
        Long id,

        String fecha,

        List<InformacionDetallesServiciosCitaClienteDTO> informacionDetallesServiciosCitaClienteDTOList,

        List<InformacionDetallesProductosCitaClienteDTO> informacionDetallesProductosCitaClienteDTOList,

        String comentario,

        String nombreCliente,

        String nombreEstilista
){}



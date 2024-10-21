package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionServicioDTO;

import java.util.List;

public interface ServicioService {
    List<InformacionServicioDTO> obtenerTodosLosServicios();
}

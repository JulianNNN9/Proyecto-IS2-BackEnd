package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionProductoDTO;

import java.util.List;

public interface ProductoService {

    List<InformacionProductoDTO> obtenerTodosLosProductos();
}

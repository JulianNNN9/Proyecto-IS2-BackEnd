package co.edu.uniquindio.proyectois2backend.services.interfaces;

import java.util.List;

public interface ClienteService {
    public List<Object[]> revisarPreferenciasPorNombre(String nombreCliente) throws Exception;
}

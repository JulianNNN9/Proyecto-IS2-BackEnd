package co.edu.uniquindio.proyectois2backend.services.interfaces;

import java.util.Optional;

import co.edu.uniquindio.proyectois2backend.model.Cliente;

public interface ClienteService {

    Cliente crearCliente(Cliente cliente) throws Exception;
    boolean buscarCliente(Cliente cliente) throws Exception;
    Optional<Cliente> buscarDatosCliente(Cliente cliente) throws Exception;
    
}

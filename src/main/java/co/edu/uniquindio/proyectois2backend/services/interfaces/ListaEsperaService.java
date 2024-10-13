package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.model.ListaEspera;

import java.util.List;

public interface ListaEsperaService {
    ListaEspera agregarClienteALaListaDeEspera(Long clienteId) throws Exception;
    List<ListaEspera> obtenerClientesEnEspera();
    ListaEspera atenderCliente() throws Exception;
}
package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.model.ListaEspera;
import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.repositories.ListaEsperaRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ListaEsperaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RequiredArgsConstructor
@Service
public class ListaEsperaServiceImple implements ListaEsperaService {

    private Queue<ListaEspera> listaEsperaQueue = new LinkedList<>();
    private ListaEsperaRepository listaEsperaRepository;
    private ClienteRepository clienteRepository;

    @Override
    public ListaEspera agregarClienteALaListaDeEspera(Long clienteId) throws Exception {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));

        ListaEspera listaEspera = new ListaEspera();
        listaEspera.setCliente(cliente);
        listaEspera.setFechaSolicitud(LocalDateTime.now());
        listaEspera.setEstado("EN_ESPERA");

        listaEsperaQueue.offer(listaEspera); // Encolar cliente
        return listaEsperaRepository.save(listaEspera);
    }

    @Override
    public List<ListaEspera> obtenerClientesEnEspera() {
        return new ArrayList<>(listaEsperaQueue); // Convertir la cola en lista para su visualización
    }

    @Override
    public ListaEspera atenderCliente() throws Exception {
        ListaEspera clienteAtendido = listaEsperaQueue.poll(); // Desencolar cliente (atender el primero)
        if (clienteAtendido == null) {
            throw new Exception("No hay clientes en espera");
        }

        clienteAtendido.setEstado("ATENDIDO");
        return listaEsperaRepository.save(clienteAtendido);
    }
}

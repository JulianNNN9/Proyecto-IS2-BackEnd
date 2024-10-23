package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImple implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Object[]> revisarPreferenciasPorNombre(String nombreCliente) throws Exception {
        return clienteRepository.findPreferenciasByNombreCliente(nombreCliente);
    }
}

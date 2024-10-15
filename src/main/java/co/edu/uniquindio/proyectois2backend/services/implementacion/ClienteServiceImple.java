package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImple implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        //Aquí podrías agregar lógica adicional, como validaciones
        return clienteRepository.save(cliente); // Guarda el cliente en la base de datos
    }
}

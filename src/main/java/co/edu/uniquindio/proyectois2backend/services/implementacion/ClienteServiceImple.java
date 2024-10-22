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
    public Cliente crearCliente(Cliente cliente) throws Exception {
        //Aquí podrías agregar lógica adicional, como validaciones

        if (!clienteRepository.ExisteClienteCorreo(cliente.getCorreo())){

            return clienteRepository.save(cliente); // Guarda el cliente en la base de datos
        }else{

            throw new Exception("correo duplicado");
        }
        
    }

    @Override
    public boolean buscarCliente (Cliente cliente) throws Exception{

        if (!clienteRepository.ExisteCliente(cliente.getCorreo(), cliente.getContrasena())){

            return false; // busca el cliente en la base de datos
        }else{

            return true;
        }

    }
}

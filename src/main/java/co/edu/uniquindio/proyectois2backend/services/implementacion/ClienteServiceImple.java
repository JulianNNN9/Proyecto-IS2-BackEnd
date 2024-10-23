package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImple implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Object[]> revisarPreferenciasPorNombre(String nombreCliente) throws Exception {
        return clienteRepository.findPreferenciasByNombreCliente(nombreCliente);
    }

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

    @Override
    public Optional<Cliente> buscarDatosCliente (Cliente cliente) throws Exception{

        try {
            
            Long clienteId = clienteRepository.obtenerIdCliente(cliente.getCorreo());
 
            Optional<Cliente> clienteEncontrado = clienteRepository.findById(clienteId);

            System.out.println(cliente);

            return clienteEncontrado;
        } catch (Exception e) {

            throw new Exception("no se encontro ningun usuario");
        }
        

        

    }
}

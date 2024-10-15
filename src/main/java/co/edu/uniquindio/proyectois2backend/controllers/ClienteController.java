package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes") // Endpoint base para el controlador de clientes
public class ClienteController {

    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Llama al servicio para crear el cliente
        try {
            Cliente nuevoCliente = clienteService.crearCliente(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED); // Retorna el cliente creado con un estado 201
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        
    }

    // Otros métodos del controlador pueden ir aquí (e.g., obtener, actualizar, eliminar)
}

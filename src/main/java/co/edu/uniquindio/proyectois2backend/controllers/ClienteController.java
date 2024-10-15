package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.services.implementacion.ClienteServiceImple;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Executable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes") // Endpoint base para el controlador de clientes
public class ClienteController {

    private final ClienteServiceImple clienteServiceImple;

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Llama al servicio para crear el cliente
        try {
            Cliente nuevoCliente = clienteServiceImple.crearCliente(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED); // Retorna el cliente creado con un estado 201
        } catch (Exception e) {

            if(e.getMessage()=="correo duplicado"){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
    
        }
        
    }
    
    @PostMapping("/saludo")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok().body("Hola Mundo");
    }

    // Otros métodos del controlador pueden ir aquí (e.g., obtener, actualizar, eliminar)
}

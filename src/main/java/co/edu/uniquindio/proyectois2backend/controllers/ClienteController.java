package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes") // Endpoint base para el controlador de clientes
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Llama al servicio para crear el cliente
        try {
            Cliente nuevoCliente = clienteService.crearCliente(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED); // Retorna el cliente creado con un estado 201
        } catch (Exception e) {

            if(e.getMessage()=="correo duplicado"){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
    
        }
        
    }

    @PostMapping("/InicioSesion")
    public boolean buscarCliente(@RequestBody Cliente cliente) throws Exception {
        // Llama al servicio para crear el cliente

            boolean response = clienteService.buscarCliente(cliente);
            return response;
    }


    @PostMapping("/DatosCliente") // Trae los datos completos de algun cliente en especifico @Param Correo
    public Optional<Cliente> buscarDatosCliente (@RequestBody Cliente cliente) throws Exception {

        try {

            Optional<Cliente> clienteEncontrado = clienteService.buscarDatosCliente(cliente);

            return clienteEncontrado;
            
        } catch (Exception e) {
            
            throw new Exception("No se puedo encontrar el usuario");
        }
    }
    
    @PostMapping("/saludo")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok().body("Hola Mundo");
    }

    // Otros métodos del controlador pueden ir aquí (e.g., obtener, actualizar, eliminar)
}

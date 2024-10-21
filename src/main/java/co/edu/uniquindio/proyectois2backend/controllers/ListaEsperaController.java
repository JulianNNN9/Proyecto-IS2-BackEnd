package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.model.ListaEspera;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ListaEsperaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/listaEspera")
public class ListaEsperaController {

    private ListaEsperaService listaEsperaService;

    // Agregar cliente a la lista de espera
    @PostMapping("/agregar")
    public ResponseEntity<ListaEspera> agregarClienteALaLista(@RequestParam Long clienteId) {
        try {
            ListaEspera listaEspera = listaEsperaService.agregarClienteALaListaDeEspera(clienteId);
            return new ResponseEntity<>(listaEspera, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener lista de clientes en espera
    @GetMapping("/enEspera")
    public ResponseEntity<List<ListaEspera>> obtenerClientesEnEspera() {
        List<ListaEspera> clientesEnEspera = listaEsperaService.obtenerClientesEnEspera();
        return new ResponseEntity<>(clientesEnEspera, HttpStatus.OK);
    }

    // Atender cliente (quitarlo de la lista de espera)
    @PostMapping("/atender")
    public ResponseEntity<ListaEspera> atenderCliente() {
        try {
            ListaEspera clienteAtendido = listaEsperaService.atenderCliente();
            return new ResponseEntity<>(clienteAtendido, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

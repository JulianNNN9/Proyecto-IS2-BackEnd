package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionServicioDTO;
import co.edu.uniquindio.proyectois2backend.model.Cliente;
import co.edu.uniquindio.proyectois2backend.services.implementacion.ClienteServiceImple;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    // Endpoint para obtener todos los servicios
    @GetMapping("/obtener-servicios")
    public ResponseEntity<List<InformacionServicioDTO>> obtenerTodosLosServicios() {
        List<InformacionServicioDTO> servicios = servicioService.obtenerTodosLosServicios();
        return ResponseEntity.ok(servicios);
    }
}

package co.edu.uniquindio.proyectois2backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionEstilistaDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EstilistaService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/estilistas")

public class EstilistasController {
    
    private final EstilistaService estilistaService;

    // Endpoint para obtener todos los servicios
    @GetMapping("/obtener-estilistas")
    public ResponseEntity<List<InformacionEstilistaDTO>> obtenerTodosLosServicios() throws Exception {
        List<InformacionEstilistaDTO> estilistas = estilistaService.obtenerTodosLosEstilistas();
        return ResponseEntity.ok(estilistas);
    }
}



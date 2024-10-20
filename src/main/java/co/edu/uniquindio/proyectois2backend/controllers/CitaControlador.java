package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.dto.MensajeDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.CrearCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionCitasClienteDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cita")
@RequiredArgsConstructor
public class CitaControlador {

    private final CitaService citaService;

    @PostMapping("/crear-cita")
    public ResponseEntity<MensajeDTO<String>> crearCita(@Valid @RequestBody CrearCitaDTO crearCitaDTO) throws Exception {
        citaService.crearCita(crearCitaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita creada correctamente"));
    }

    @PostMapping("/confirmar-cita/{id}")
    public ResponseEntity<MensajeDTO<String>> confirmarCita(@PathVariable("id") Long citaId) throws Exception {
        citaService.confirmarCita(citaId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita confirmada correctamente"));
    }
    @GetMapping("/{clienteId}/historial")
    public ResponseEntity<List<InformacionCitasClienteDTO>> obtenerHistorialCliente(@PathVariable Long clienteId) {
        List<InformacionCitasClienteDTO> historial = citaService.obtenerHistorialCliente(clienteId);
        return ResponseEntity.ok(historial);
    }
    @PostMapping("/saludo")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok().body("Hola Mundo");
    }
}

package co.edu.uniquindio.proyectois2backend.controllers;

import co.edu.uniquindio.proyectois2backend.dto.MensajeDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.CrearCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionCitasClienteDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.ModificarCitaDTO;
import co.edu.uniquindio.proyectois2backend.model.Cita;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cita")
@RequiredArgsConstructor
public class CitaControlador {

    private final CitaService citaService;

    @PostMapping("/crear-cita2")
    public ResponseEntity<MensajeDTO<String>> crearCita2(@Valid @RequestBody CrearCitaDTO crearCitaDTO) throws Exception {
        citaService.crearCita(crearCitaDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita creada correctamente"));
    }

    @PostMapping("/crear-cita")
    public Long crearCita(@Valid @RequestBody CrearCitaDTO crearCitaDTO) throws Exception {
        Long CitaId = citaService.crearCita(crearCitaDTO);
        return CitaId;
    }


    @PostMapping("/confirmar-cita/{id}")
    public ResponseEntity<MensajeDTO<String>> confirmarCita(@PathVariable("id") Long citaId) throws Exception {
        citaService.confirmarCita(citaId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita confirmada correctamente"));
    }
    @GetMapping("/{clienteId}/historial-citas")
    public ResponseEntity<List<InformacionCitasClienteDTO>> obtenerHistorialCliente(@PathVariable Long clienteId) {
        List<InformacionCitasClienteDTO> historial = citaService.obtenerHistorialCliente(clienteId);
        return ResponseEntity.ok(historial);
    }
    // Endpoint para cancelar una cita
    @PutMapping("/{citaId}/cancelar-cita")
    public ResponseEntity<MensajeDTO<String>> cancelarCita(@PathVariable("citaId") Long citaId) {
        try {
            Cita citaCancelada = citaService.cancelarCita(citaId);
            return ResponseEntity.ok(new MensajeDTO<>(false, "La cita ha sido cancelada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }
    // Endpoint para cancelar una cita
    @PutMapping("/{citaId}/finalizar-cita")
    public ResponseEntity<MensajeDTO<String>> finalizarCita(@PathVariable Long citaId) {
        try {
            citaService.finalizarCita(citaId);
            return ResponseEntity.ok(new MensajeDTO<>(false, "La cita ha sido finalizada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }
    @PutMapping("/{citaId}/modificar-cita-pendiente")
    public ResponseEntity<MensajeDTO<String>> modificarCita(@PathVariable("citaId") Long citaId, @RequestBody ModificarCitaDTO modificarCitaDTO) {
        System.out.println(citaId);
        System.out.println(modificarCitaDTO);
        try {
            Cita citaModificada = citaService.modificarCitaPendiente(citaId, modificarCitaDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, "Cita modificada exitosamente"));
        } catch (Exception e) {
            System.out.println("exception: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }
    // Endpoint para modificar el comentario de una cita
    @PutMapping("/{citaId}/comentario-cita")
    public ResponseEntity<MensajeDTO<String>> modificarComentarioCita(
            @PathVariable Long citaId,
            @RequestParam String comentario) {
        System.out.println(citaId);
        System.out.println(comentario);
        try {
            Cita citaModificada = citaService.modificarComentarioCita(citaId, comentario);
            return ResponseEntity.ok(new MensajeDTO<>(false, "Su comentario se agregó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    // Endpoint para agregar propina a una cita
    @PutMapping("/{citaId}/propina-cita")
    public ResponseEntity<MensajeDTO<String>> agregarPropinaCita(
            @PathVariable Long citaId,
            @RequestParam double propina) {
        try {
            Cita citaModificada = citaService.agregarPropinaCita(citaId, propina);
            return ResponseEntity.ok(new MensajeDTO<>(false, "Propina Agregada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }

    // Endpoint para reprogramar una cita
    @PutMapping("/{citaId}/reprogramar-cita")
    public ResponseEntity<MensajeDTO<String>> reprogramarCita(
            @PathVariable Long citaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime nuevaFecha) {
        try {
            Cita citaReprogramada = citaService.reprogramarCita(citaId, nuevaFecha);
            return ResponseEntity.ok(new MensajeDTO<>(false, "Su cita se reprogramó exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }
    @PostMapping("/saludo")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok().body("Hola Mundo");
    }
}
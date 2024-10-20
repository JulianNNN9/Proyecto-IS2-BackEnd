package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmarCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.CrearCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionCitasClienteDTO;
import co.edu.uniquindio.proyectois2backend.model.Cita;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {

    void enviarRecordatoriosDeCitas() throws Exception;
    Long crearCita(CrearCitaDTO crearCitaDTO) throws Exception;
    void confirmarCita(Long id) throws Exception;
    List<InformacionCitasClienteDTO> obtenerHistorialCliente(Long clienteId);
    Cita cancelarCita(Long citaId) throws Exception;
    Cita reprogramarCita(Long citaId, LocalDateTime nuevaFecha) throws Exception;
}

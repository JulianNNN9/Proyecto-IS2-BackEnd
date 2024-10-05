package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmarCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.CrearCitaDTO;

public interface CitaService {

    void enviarRecordatoriosDeCitas() throws Exception;
    Long crearCita(CrearCitaDTO crearCitaDTO) throws Exception;
    void confirmarCita(Long id) throws Exception;
}

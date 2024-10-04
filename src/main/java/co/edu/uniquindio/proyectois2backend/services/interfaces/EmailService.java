package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.EmailDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;

public interface EmailService {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;

    void enviarCorreoRecordatorioCita(RecordatorioDTO citaRecordatorioDTO) throws Exception;

}

package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    void enviarConfirmacionTemplateEmail(String to, ConfirmacionDTO confirmacionDTO) throws MessagingException;
    void enviarRecordatorioTemplateEmail(String to, RecordatorioDTO recordatorioDTO) throws MessagingException;
}

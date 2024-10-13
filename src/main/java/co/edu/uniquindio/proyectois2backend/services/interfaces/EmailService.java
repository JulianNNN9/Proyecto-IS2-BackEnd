package co.edu.uniquindio.proyectois2backend.services.interfaces;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.NotificarCancelacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.NotificarReprogramacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
    void enviarConfirmacionTemplateEmail(String to, ConfirmacionDTO confirmacionDTO) throws MessagingException;
    void enviarRecordatorioTemplateEmail(String to, RecordatorioDTO recordatorioDTO) throws MessagingException;
    void enviarEmailEstilistaCancelacionCita(String to, NotificarCancelacionDTO notificarCancelacionDTO) throws MessagingException;
    void enviarEmailEstilistaCambiosCita(String to, NotificarReprogramacionDTO notificarReprogramacionDTO) throws MessagingException;
}

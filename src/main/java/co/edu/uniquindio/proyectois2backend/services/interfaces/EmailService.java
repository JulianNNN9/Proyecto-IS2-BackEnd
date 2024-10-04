package co.edu.uniquindio.proyectois2backend.services.interfaces;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    void enviarConfirmacionTemplateEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException;
    void enviarRecordatorioTemplateEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException;
}

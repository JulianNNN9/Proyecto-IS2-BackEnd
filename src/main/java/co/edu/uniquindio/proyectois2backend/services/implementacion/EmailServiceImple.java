package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.EmailDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImple implements EmailService {

    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

        Email email = EmailBuilder.startingBlank()
                .from("notificacionesunieventos@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "Nuestro correo", "password")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }

    }

    @Override
    @Async
    public void enviarCorreoRecordatorioCita(RecordatorioDTO citaRecordatorioDTO) throws Exception {
        String asunto = "Recordatorio de Cita para Hoy";
        String cuerpo = String.format("Estimado cliente, le recordamos que tiene una cita hoy a las %s. No olvide asistir.",
                citaRecordatorioDTO.fechaCita().toLocalTime());

        EmailDTO emailDTO = new EmailDTO(asunto, cuerpo, citaRecordatorioDTO.correoCliente());
        enviarCorreo(emailDTO);
    }
}

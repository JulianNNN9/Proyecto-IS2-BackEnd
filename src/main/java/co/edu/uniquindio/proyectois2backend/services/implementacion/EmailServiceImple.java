package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.NotificarCancelacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.NotificarReprogramacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailServiceImple implements EmailService {


    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void enviarConfirmacionTemplateEmail(String to, ConfirmacionDTO confirmacionDTO) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nombreCliente", confirmacionDTO.nombreCliente());
        templateModel.put("servicio", confirmacionDTO.servicio());
        templateModel.put("fechaCita", confirmacionDTO.fechaCita());
        templateModel.put("horaCita", confirmacionDTO.horaCita());
        templateModel.put("nombreEstilista", confirmacionDTO.nombreEstilista());
        templateModel.put("direccionPeluqueria", confirmacionDTO.direccionPeluqueria());
        templateModel.put("telefonoPeluqueria", confirmacionDTO.telefonoPeluqueria());


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlBody = templateEngine.process("confirmacionTemplate", context);

        helper.setTo(to);
        helper.setSubject("Confirmacion de Cita");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
    @Override
    @Async
    public void enviarRecordatorioTemplateEmail(String to, RecordatorioDTO recordatorioDTO) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nombreCliente", recordatorioDTO.nombreCliente());
        templateModel.put("servicio", recordatorioDTO.servicio());
        templateModel.put("fechaCita", recordatorioDTO.fechaCita());
        templateModel.put("horaCita", recordatorioDTO.horaCita());
        templateModel.put("nombreEstilista", recordatorioDTO.nombreEstilista());
        templateModel.put("direccionPeluqueria", recordatorioDTO.direccionPeluqueria());
        templateModel.put("telefonoPeluqueria", recordatorioDTO.telefonoPeluqueria());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlBody = templateEngine.process("recordatorioTemplate", context);

        helper.setTo(to);
        helper.setSubject("Recordatorio de Cita");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Async
    @Override
    public void enviarEmailEstilistaCancelacionCita(String to, NotificarCancelacionDTO notificarCancelacionDTO) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nombreCliente", notificarCancelacionDTO.nombreCliente());
        templateModel.put("fechaCita", notificarCancelacionDTO.fecha());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlBody = templateEngine.process("notificarCancelacionTemplate", context);

        helper.setTo(to);
        helper.setSubject("La cita ha sido cancelada.");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Async
    @Override
    public void enviarEmailEstilistaCambiosCita(String to, NotificarReprogramacionDTO notificarReprogramacionDTO) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nombreCliente", notificarReprogramacionDTO.nombreCliente());
        templateModel.put("fechaCitaAntigua", notificarReprogramacionDTO.fechaCitaAntigua());
        templateModel.put("fechaCitaNueva", notificarReprogramacionDTO.FechaCitaNueva());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlBody = templateEngine.process("notificarCambiosTemplate", context);

        helper.setTo(to);
        helper.setSubject("La fecha de la cita ha sido modificada.");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
}

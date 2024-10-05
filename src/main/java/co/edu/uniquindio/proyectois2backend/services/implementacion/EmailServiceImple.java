package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        templateModel.put("direccion", confirmacionDTO.direccion());
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
        templateModel.put("direccion", recordatorioDTO.direccion());
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
}

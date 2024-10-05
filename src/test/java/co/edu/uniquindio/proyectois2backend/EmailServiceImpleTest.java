package co.edu.uniquindio.proyectois2backend;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmacionDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;

import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class EmailServiceImpleTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEnviarRecordatorioCorreo(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formatea la hora actual
        String formattedTime = LocalTime.now().format(timeFormatter);

        RecordatorioDTO recordatorioDTO = new RecordatorioDTO(
                "Johan Noe",
                "Corte de Pelo",
                "" + LocalDate.now(),
                formattedTime,
                "Luisa Quintero",
                "Barrio Pinares Mz 1 Casa 8 - Armenia/Quindio",
                "31244455566");

        String correo = "ljohannoe@gmail.com";

        Assertions.assertDoesNotThrow(() -> emailService.enviarRecordatorioTemplateEmail(correo, recordatorioDTO));
    }

    @Test
    public void testEnviarConfirmacionCorreo(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formatea la hora actual
        String formattedTime = LocalTime.now().format(timeFormatter);

        ConfirmacionDTO confirmacionDTO = new ConfirmacionDTO(
                "Johan Noe",
                "Corte de Pelo",
                "" + LocalDate.now(),
                formattedTime,
                "Luisa Quintero",
                "Barrio Pinares Mz 1 Casa 8 - Armenia/Quindio",
                "31244455566");

        String correo = "ljohannoe@gmail.com";

        Assertions.assertDoesNotThrow(() -> emailService.enviarConfirmacionTemplateEmail(correo, confirmacionDTO));
    }
}

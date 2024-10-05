package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import co.edu.uniquindio.proyectois2backend.model.Cita;
import co.edu.uniquindio.proyectois2backend.model.DetalleServicioCita;
import co.edu.uniquindio.proyectois2backend.repositories.CitaRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImple implements CitaService {

    private final EmailService emailService;
    private final CitaRepository citaRepository;

    @Override
    @Scheduled(cron = "0 0 8 * * ?") // Ejecuta a las 8:00 AM todos los días
    public void enviarRecordatoriosDeCitas() throws MessagingException {
        LocalDate hoy = LocalDate.now();
        List<Cita> citasDeHoy = citaRepository.obtenerCitasPorFecha(hoy);

        for (Cita cita : citasDeHoy) {
            String nombreServicios = "";
            for(DetalleServicioCita detalleServicio: cita.getDetalleServicios()){
                nombreServicios += detalleServicio.getServicio().getNombre() + ", ";
            }
            RecordatorioDTO citaRecordatorioDTO = new RecordatorioDTO(
                    cita.getCliente().getNombre(),
                    nombreServicios,
                    "" + cita.getFecha().toLocalDate(),
                    "" + cita.getFecha().toLocalTime(),
                    cita.getEstilista().getNombre(),
                    "Barrio los Pinares Mz 1 Casa 10 - Armenia/Quindio",
                    "3121111111"
            );
            emailService.enviarRecordatorioTemplateEmail(cita.getCliente().getCorreo(), citaRecordatorioDTO);
        }
    }
}

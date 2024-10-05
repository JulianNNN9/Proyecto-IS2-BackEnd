package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.RecordatorioDTO;
import co.edu.uniquindio.proyectois2backend.model.Cita;
import co.edu.uniquindio.proyectois2backend.repositories.CitaRepository;
import co.edu.uniquindio.proyectois2backend.repositories.ClienteRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
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
    private final ClienteRepository clienteRepository;

    @Override
    @Scheduled(cron = "0 0 8 * * ?") // Ejecuta a las 8:00 AM todos los días
    public void enviarRecordatoriosDeCitas() {
        LocalDate hoy = LocalDate.now();
        List<Cita> citasDeHoy = citaRepository.obtenerCitasPorFecha(hoy);

        for (Cita cita : citasDeHoy) {
            try {
                String correoCliente = clienteRepository.obtenerCorreoClientePorId(String.valueOf(cita.getCliente()));
                RecordatorioDTO citaRecordatorioDTO = new RecordatorioDTO(
                        cita.getId(),
                        cita.getFecha(),
                        correoCliente
                );
                emailService.enviarCorreoRecordatorioCita(citaRecordatorioDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

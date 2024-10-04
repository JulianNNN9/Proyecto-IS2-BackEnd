package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.model.Cita;
import co.edu.uniquindio.proyectois2backend.repositories.CitaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordatorioServiceImple {

    private CitaRepo citaRepo;

    @Scheduled(fixedRate = 3600000) // cada hora
    public void enviarRecordatorios() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime despues = ahora.plusHours(24);
        List<Cita> citas = citaRepo.findCitasProximas(ahora, despues);

        for (Cita cita : citas) {
            if (!cita.isRecordatorioEnviado()) {
                enviarRecordatorio(cita);
                cita.setRecordatorioEnviado(true);
                citaRepo.save(cita);
            }
        }
    }

    private void enviarRecordatorio(Cita cita) {

    }
}
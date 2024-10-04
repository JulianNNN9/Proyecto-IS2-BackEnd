package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepo extends JpaRepository<Cita, Long>  {
    List<Cita> findCitasProximas(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}

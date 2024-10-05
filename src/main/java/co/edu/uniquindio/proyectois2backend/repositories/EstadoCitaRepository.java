package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Long> {
    Optional<EstadoCita> findByNombre(String nombre);
}

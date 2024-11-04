package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.DetalleServicioCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleServicioCitaRepository extends JpaRepository<DetalleServicioCita, Long> {


}

package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.DetalleProductoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DetalleProductoCitaRepository extends JpaRepository<DetalleProductoCita, Long> {


}

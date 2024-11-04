package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("SELECT c FROM Cita c WHERE DATE(c.fecha) = :fecha")
    List<Cita> obtenerCitasPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM Cita c WHERE c.cliente.id = :idCliente")
    List<Cita> obtenerCitasPorCliente(@Param("idCliente") Long idCliente);

}

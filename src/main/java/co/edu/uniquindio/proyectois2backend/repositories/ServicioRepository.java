package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    @Query("SELECT s.nombre, s.precio FROM Servicio s")
    List<Object[]> findAllServiciosWithNombreAndPrecio();
}

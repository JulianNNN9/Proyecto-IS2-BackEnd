package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c.correo FROM Cliente c WHERE c.id = :idCliente")
    String obtenerCorreoClientePorId(@Param("idCliente") Long idCliente);

    @Query("SELECT c.nombre, p FROM Cliente c JOIN c.preferenciaClientes p WHERE c.nombre = :nombreCliente")
    List<Object[]> findPreferenciasByNombreCliente(@Param("nombreCliente") String nombreCliente);

}

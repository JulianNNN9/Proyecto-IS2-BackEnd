package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("SELECT c.correo FROM Cliente c WHERE c.id = :idCliente")
    String obtenerCorreoClientePorId(@Param("idCliente") String idCliente);
}

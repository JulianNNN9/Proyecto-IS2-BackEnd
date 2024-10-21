package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c.correo FROM Cliente c WHERE c.id = :idCliente")
    String obtenerCorreoClientePorId(@Param("idCliente") Long idCliente);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.correo = :correo")
    boolean ExisteClienteCorreo(@Param("correo") String correo);  

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.correo = :correo AND c.contrasena = :contrasena")
    boolean ExisteCliente(@Param("correo") String correo, @Param("contrasena") String contrasena );  
}

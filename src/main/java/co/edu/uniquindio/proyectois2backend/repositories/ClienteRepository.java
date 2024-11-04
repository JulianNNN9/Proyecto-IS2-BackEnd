package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c.correo FROM Cliente c WHERE c.id = :idCliente")
    String obtenerCorreoClientePorId(@Param("idCliente") Long idCliente);

    @Query("SELECT c.nombre, p FROM Cliente c JOIN c.preferenciaClientes p WHERE c.nombre = :nombreCliente")
    List<Object[]> findPreferenciasByNombreCliente(@Param("nombreCliente") String nombreCliente);
  
    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.correo = :correo")
    boolean ExisteClienteCorreo(@Param("correo") String correo);  

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.correo = :correo AND c.contrasena = :contrasena")
    boolean ExisteCliente(@Param("correo") String correo, @Param("contrasena") String contrasena );  

    @Query("SELECT c.id FROM Cliente c WHERE c.correo = :correo")
    Long obtenerIdCliente(@Param("correo") String correo);
}

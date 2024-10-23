package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReseniaRepository extends JpaRepository<Resenia, Long> {

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.calificacion = :calificacion AND r.cliente.nombre = :nombreCliente AND r.comentario = :comentario")
    List<Object[]> findByCalificacionNombreClienteComentario(int calificacion, String nombreCliente, String comentario);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.calificacion = :calificacion AND r.cliente.nombre = :nombreCliente")
    List<Object[]> findByCalificacionAndNombreCliente(int calificacion, String nombreCliente);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.calificacion = :calificacion AND r.comentario = :comentario")
    List<Object[]> findByCalificacionAndComentario(int calificacion, String comentario);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.cliente.nombre = :nombreCliente AND r.comentario = :comentario")
    List<Object[]> findByNombreClienteAndComentario(String nombreCliente, String comentario);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.calificacion = :calificacion")
    List<Object[]> findByCalificacion(int calificacion);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.cliente.nombre = :nombreCliente")
    List<Object[]> findByNombreCliente(String nombreCliente);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r WHERE r.comentario = :comentario")
    List<Object[]> findByComentario(String comentario);

    @Query("SELECT r.calificacion, r.cliente.nombre FROM Resenia r")
    List<Object[]> findAllCalificacionesWithNombreCliente();
}

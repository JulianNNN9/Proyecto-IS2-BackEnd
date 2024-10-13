package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.ListaEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Long> {
    List<ListaEspera> findByEstado(String estado);
}
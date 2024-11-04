package co.edu.uniquindio.proyectois2backend.repositories;

import co.edu.uniquindio.proyectois2backend.model.Estilista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstilistaRepository extends JpaRepository<Estilista, Long> {
}


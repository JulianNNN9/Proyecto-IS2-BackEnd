package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.resenia.FiltrosReseniaDTO;
import co.edu.uniquindio.proyectois2backend.repositories.ReseniaRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ResenaServiceImple implements ResenaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    public List<Object[]> filtrarResenias(FiltrosReseniaDTO filtrosResenia) {
        Integer calificacion = filtrosResenia.calificacion();
        String nombreCliente = filtrosResenia.nombreCliente();
        String comentario = filtrosResenia.comentario();

        // Si todos los filtros son nulos o vacíos, devuelve todas las reseñas
        if ((calificacion == null) &&
                (nombreCliente == null || nombreCliente.isEmpty()) &&
                (comentario == null || comentario.isEmpty())) {
            return reseniaRepository.findAllCalificacionesWithNombreCliente();
        }

        // Si todos los filtros están presentes
        if (calificacion != null &&
                (nombreCliente != null && !nombreCliente.isEmpty()) &&
                (comentario != null && !comentario.isEmpty())) {
            return reseniaRepository.findByCalificacionNombreClienteComentario(calificacion, nombreCliente, comentario);
        }

        // Filtrar combinaciones de dos parámetros
        if (calificacion != null && nombreCliente != null && !nombreCliente.isEmpty()) {
            return reseniaRepository.findByCalificacionAndNombreCliente(calificacion, nombreCliente);
        }

        if (calificacion != null && comentario != null && !comentario.isEmpty()) {
            return reseniaRepository.findByCalificacionAndComentario(calificacion, comentario);
        }

        if (nombreCliente != null && !nombreCliente.isEmpty() && comentario != null && !comentario.isEmpty()) {
            return reseniaRepository.findByNombreClienteAndComentario(nombreCliente, comentario);
        }

        // Filtrar individualmente si no hay combinaciones
        if (calificacion != null) {
            return reseniaRepository.findByCalificacion(calificacion);
        }

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            return reseniaRepository.findByNombreCliente(nombreCliente);
        }

        if (comentario != null && !comentario.isEmpty()) {
            return reseniaRepository.findByComentario(comentario);
        }

        // Si no se encontraron resultados, retorna una lista vacía o un valor por defecto
        return Collections.emptyList();
    }

}

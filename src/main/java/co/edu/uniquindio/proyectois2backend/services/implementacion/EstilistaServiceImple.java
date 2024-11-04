package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionEstilistaDTO;
import co.edu.uniquindio.proyectois2backend.model.Estilista;
import co.edu.uniquindio.proyectois2backend.repositories.EstilistaRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EstilistaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EstilistaServiceImple implements EstilistaService {

    private final EstilistaRepository estilistaRepository;

    @Override
    public List<InformacionEstilistaDTO> obtenerTodosLosEstilistas() {
        List<Estilista> estilistas = estilistaRepository.findAll();
        return estilistas.stream()
                .map(this::convertirAEstilistaDTO)
                .collect(Collectors.toList());
    }

    // Método privado para convertir un Estilista en un InformacionEstilistaDTO
    private InformacionEstilistaDTO convertirAEstilistaDTO(Estilista estilista) {
        return new InformacionEstilistaDTO(
                estilista.getId(),
                estilista.getNombre(),
                estilista.getCorreo()
        );
    }
}

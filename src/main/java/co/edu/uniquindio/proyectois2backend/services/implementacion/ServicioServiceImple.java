package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionServicioDTO;
import co.edu.uniquindio.proyectois2backend.model.Servicio;
import co.edu.uniquindio.proyectois2backend.repositories.ServicioRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioServiceImple implements ServicioService {

    private final ServicioRepository servicioRepository;

    @Override
    public List<InformacionServicioDTO> obtenerTodosLosServicios() {
        List<Servicio> servicios = servicioRepository.findAll();

        // Convertir los servicios a DTO
        return servicios.stream()
                .map(servicio -> new InformacionServicioDTO(
                        servicio.getId(),
                        servicio.getNombre(),
                        servicio.getPrecio()
                ))
                .collect(Collectors.toList());
    }
}

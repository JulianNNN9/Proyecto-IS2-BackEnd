package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionProductoDTO;
import co.edu.uniquindio.proyectois2backend.model.Producto;
import co.edu.uniquindio.proyectois2backend.repositories.ProductoRepository;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ProductoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImple implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<InformacionProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
    }

    // Método privado para convertir un Producto en un InformacionProductoDTO
    private InformacionProductoDTO convertirAProductoDTO(Producto producto) {
        return new InformacionProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getMarca()
        );
    }
}

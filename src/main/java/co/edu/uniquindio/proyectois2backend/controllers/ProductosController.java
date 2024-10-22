package co.edu.uniquindio.proyectois2backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.proyectois2backend.dto.cita.InformacionProductoDTO;
import co.edu.uniquindio.proyectois2backend.services.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    private final ProductoService productoService;

    // Endpoint para obtener todos los servicios
    @GetMapping("/obtener-productos")
    public ResponseEntity<List<InformacionProductoDTO>> obtenerTodosLosServicios() throws Exception {
        List<InformacionProductoDTO> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }
    
}

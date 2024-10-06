package co.edu.uniquindio.proyectois2backend;

import co.edu.uniquindio.proyectois2backend.dto.cita.ConfirmarCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.CrearCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.DetalleProductoCitaDTO;
import co.edu.uniquindio.proyectois2backend.dto.cita.DetalleServicioCitaDTO;
import co.edu.uniquindio.proyectois2backend.model.Cita;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/*
IMPORTANTE CORRER EL SCRIPT DEL DATASET.JS
PARA CREAR DATOS DE PRUEBA EN LAS TABLAS
 */
@SpringBootTest
public class CitaServiceImpleTest {

    @Autowired
    private CitaService citaService;

    @Test
    public void testCrearCita(){
        // CrearCitaDTO con servicios y productos inicializados
        CrearCitaDTO crearCitaDTO = new CrearCitaDTO(
                "2024-10-05T14:00:00", // Fecha y hora de la cita
                List.of(
                        new DetalleServicioCitaDTO(1L, 20000.00), // Servicio Corte de cabello
                        new DetalleServicioCitaDTO(2L, 15000.00)  // Servicio Peinado
                ), // Lista de detalles de servicios
                List.of(
                        new DetalleProductoCitaDTO(1L, 2, 5000.00), // Producto 1 con cantidad 2
                        new DetalleProductoCitaDTO(2L, 1, 15000.00) // Producto 2 con cantidad 1
                ), // Lista de detalles de productos
                1L, // ID del cliente
                1L  // ID del estilista
        );
        Assertions.assertDoesNotThrow(() -> citaService.crearCita(crearCitaDTO));
    }
    @Test
    public void testConfirmarCita(){
        //Crear Otra cita para Confirmarla
        CrearCitaDTO crearCitaDTO = new CrearCitaDTO(
                "2024-11-07T08:00:00", // Fecha y hora de la cita
                List.of(
                        new DetalleServicioCitaDTO(1L, 20000.00), // Servicio Corte de cabello
                        new DetalleServicioCitaDTO(2L, 15000.00)  // Servicio Peinado
                ), // Lista de detalles de servicios
                List.of(
                        new DetalleProductoCitaDTO(1L, 2, 5000.00), // Producto 1 con cantidad 2
                        new DetalleProductoCitaDTO(2L, 1, 15000.00) // Producto 2 con cantidad 1
                ), // Lista de detalles de productos
                1L, // ID del cliente
                1L  // ID del estilista
        );
        try{
            Long idCita = citaService.crearCita(crearCitaDTO);
            Assertions.assertDoesNotThrow(() -> citaService.confirmarCita(idCita));
        }catch (Exception e){
            Assertions.fail("Validacion de testConfirmarCita Falló" + e.getMessage());
        }

    }
}

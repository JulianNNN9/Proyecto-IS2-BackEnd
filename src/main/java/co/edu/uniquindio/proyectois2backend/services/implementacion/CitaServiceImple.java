package co.edu.uniquindio.proyectois2backend.services.implementacion;

import co.edu.uniquindio.proyectois2backend.dto.cita.*;
import co.edu.uniquindio.proyectois2backend.exceptions.HorarioNoDisponibleException;
import co.edu.uniquindio.proyectois2backend.model.*;
import co.edu.uniquindio.proyectois2backend.repositories.*;
import co.edu.uniquindio.proyectois2backend.services.interfaces.CitaService;
import co.edu.uniquindio.proyectois2backend.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CitaServiceImple implements CitaService {

    private final EmailService emailService;
    private final CitaRepository citaRepository;
    private final EstadoCitaRepository estadoCitaRepository;
    private final ProductoRepository productoRepository;
    private final ServicioRepository servicioRepository;
    private final DetalleProductoCitaRepository detalleProductoCitaRepository;
    private final DetalleServicioCitaRepository detalleServicioCitaRepository;
    private final EstilistaRepository estilistaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Scheduled(cron = "0 0 8 * * ?") // Ejecuta a las 8:00 AM todos los días
    public void enviarRecordatoriosDeCitas() throws MessagingException {
        LocalDate hoy = LocalDate.now();
        List<Cita> citasDeHoy = citaRepository.obtenerCitasPorFecha(hoy);

        for (Cita cita : citasDeHoy) {
            RecordatorioDTO recordatorioDTO = crearRecordatorioDTO(cita);
            emailService.enviarRecordatorioTemplateEmail(cita.getCliente().getCorreo(), recordatorioDTO);
        }
    }

    @Override
    public Long crearCita(CrearCitaDTO crearCitaDTO) throws Exception {

        Optional<EstadoCita> estadoCitaEncontrado = estadoCitaRepository.findByNombre("PENDIENTE");
        if(estadoCitaEncontrado.isEmpty()){
            throw new Exception("Estado de PENDIENTE de la cita no encontrado");
        }
        Optional<Estilista> estilistaEncontrado = estilistaRepository.findById(crearCitaDTO.estilistaId());
        if(estilistaEncontrado.isEmpty()){
            throw new Exception("Estilista no encontrada");
        }
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(crearCitaDTO.clienteId());
        if(clienteEncontrado.isEmpty()){
            throw new Exception("Cliente no encontrada");
        }
        EstadoCita estadoCita = estadoCitaEncontrado.get();
        Estilista estilista = estilistaEncontrado.get();
        Cliente cliente = clienteEncontrado.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime fechaHoraInicio = LocalDateTime.parse(crearCitaDTO.fecha(), formatter);
        Cita citaNueva = Cita.builder()
                .fecha(fechaHoraInicio)
                .confirmacion(false).direccion("Barrio los Pinares Mz 1 Casa 10 - Armenia/Quindio")
                .estadoCita(estadoCita)
                .cliente(cliente)
                .estilista(estilista)
                .build();
        List<DetalleServicioCita> detalleServicioCitasList = convertirDetallesServicioCita(citaNueva, crearCitaDTO.detalleServicioCitaDTOS());
        List<DetalleProductoCita> detalleProductoCitaList = convertirDetallesProductoCita(citaNueva, crearCitaDTO.detalleProductoCitaDTOS());
        int duracionTotalCita = obtenerDuracionTotalServicios(detalleServicioCitasList);

        LocalDateTime fechaHoraFin = fechaHoraInicio.plusMinutes(duracionTotalCita); // Hora de fin

        if (!isHorarioDisponible(fechaHoraInicio, fechaHoraFin)) {
            throw new HorarioNoDisponibleException("El horario no está disponible.");
        }

        double totalAPagar = calcularTotalAPagarServiciosYProductos(detalleServicioCitasList, detalleProductoCitaList);
        citaNueva.setTotalPago(totalAPagar);

        Cita citaGuardata = citaRepository.save(citaNueva);

        detalleServicioCitaRepository.saveAll(detalleServicioCitasList);
        detalleProductoCitaRepository.saveAll(detalleProductoCitaList);
        return citaGuardata.getId();
    }
    @Override
    public void confirmarCita(Long id) throws Exception {
        Optional<Cita> citaEncontrada = citaRepository.findById(id);
        if (citaEncontrada.isEmpty()) {
            throw new Exception("Cita no encontrada");
        }

        Cita cita = citaEncontrada.get();

        if (cita.getConfirmacion()) {
            throw new Exception("La cita ya está confirmada");
        }

        Optional<EstadoCita> estadoCitaConfirmada = estadoCitaRepository.findByNombre("CONFIRMADA");
        if (estadoCitaConfirmada.isEmpty()) {
            throw new Exception("Estado de CONFIRMADA de la cita no encontrado");
        }

        cita.setEstadoCita(estadoCitaConfirmada.get());
        cita.setConfirmacion(true);

        citaRepository.save(cita);

        ConfirmacionDTO confirmacionDTO = crearConfirmacionDTO(cita);
        emailService.enviarConfirmacionTemplateEmail(cita.getCliente().getCorreo(), confirmacionDTO );
    }

    private ConfirmacionDTO crearConfirmacionDTO(Cita cita) {
        String nombreServicios = obtenerNombreServicios(cita); // Llama al método para obtener los nombres de los servicios
        String formattedTime = formatearFecha(cita); // Llama al método para formatear la fecha

        return new ConfirmacionDTO(
                cita.getCliente().getNombre(),
                nombreServicios,
                "" + cita.getFecha().toLocalDate(),
                formattedTime,
                cita.getEstilista().getNombre(),
                "Barrio los Pinares Mz 1 Casa 10 - Armenia/Quindio",
                "3121111111"
        );
    }

    private RecordatorioDTO crearRecordatorioDTO(Cita cita) {
        String nombreServicios = obtenerNombreServicios(cita); // Llama al método para obtener los nombres de los servicios
        String formattedTime = formatearFecha(cita); // Llama al método para formatear la fecha

        return new RecordatorioDTO(
                cita.getCliente().getNombre(),
                nombreServicios,
                "" + cita.getFecha().toLocalDate(),
                formattedTime,
                cita.getEstilista().getNombre(),
                "Barrio los Pinares Mz 1 Casa 10 - Armenia/Quindio",
                "3121111111"
        );
    }

    private String obtenerNombreServicios(Cita cita) {
        StringBuilder nombreServiciosBuilder = new StringBuilder();
        for (DetalleServicioCita detalleServicio : cita.getDetalleServicioCitas()) {
            nombreServiciosBuilder.append(detalleServicio.getServicio().getNombre()).append(", ");
        }

        if (nombreServiciosBuilder.length() > 0) {
            nombreServiciosBuilder.setLength(nombreServiciosBuilder.length() - 2); // Elimina la última coma y espacio
        }

        return nombreServiciosBuilder.toString();
    }

    private String formatearFecha(Cita cita) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return cita.getFecha().toLocalTime().format(timeFormatter);
    }
    private boolean isHorarioDisponible(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        List<Cita> citas = citaRepository.obtenerCitasPorFecha(fechaHoraInicio.toLocalDate()); // Obtener citas del día

        for (Cita cita : citas) {
            LocalDateTime citaFin = cita.getFecha().plusMinutes(obtenerDuracionTotalServicios(cita.getDetalleServicioCitas()));

            // Verifica si hay conflicto de horarios
            if (fechaHoraInicio.isBefore(citaFin) && fechaHoraFin.isAfter(cita.getFecha())) {
                return false; // Hay conflicto, el horario no está disponible
            }
        }
        return true; // El horario está disponible
    }

    private int obtenerDuracionTotalServicios(List<DetalleServicioCita> detalles) {
        return detalles.stream()
                .mapToInt((detalle) -> detalle.getServicio().getDuracion())
                .sum();
    }
    private double calcularTotalAPagarServiciosYProductos(List<DetalleServicioCita> detallesServicios, List<DetalleProductoCita> detallesProductos) {
        double totalServicios = detallesServicios.stream()
                                    .mapToDouble(DetalleServicioCita::getPrecio)
                                    .sum();

        double totalProductos = detallesProductos.stream()
                                    .mapToDouble(DetalleProductoCita::getPrecio)
                                    .sum();

        return totalServicios + totalProductos;
    }
    private List<DetalleServicioCita> convertirDetallesServicioCita(Cita cita, List<DetalleServicioCitaDTO> dtos) throws Exception {
        List<DetalleServicioCita> detalleServicioCitasList = new ArrayList<>();
        for (DetalleServicioCitaDTO dto : dtos) {
            Optional<Servicio> servicioEncontrado = servicioRepository.findById(dto.idServicio());
            if(servicioEncontrado.isEmpty()){
                throw new Exception("Servicio no encontrado");
            }
            Servicio servicio = servicioEncontrado.get();

            DetalleServicioCita detalleServicioCita = new DetalleServicioCita();
            detalleServicioCita.setCita(cita);
            detalleServicioCita.setServicio(servicio);
            detalleServicioCita.setPrecio(dto.precio());

            detalleServicioCitasList.add(detalleServicioCita);

        }
        return detalleServicioCitasList;
    }

    private List<DetalleProductoCita> convertirDetallesProductoCita(Cita cita, List<DetalleProductoCitaDTO> dtos) throws Exception {
        List<DetalleProductoCita> detalleProductoCitaList = new ArrayList<>();
        for (DetalleProductoCitaDTO dto : dtos) {
            Optional<Producto> productoEncontrado = productoRepository.findById(dto.idProducto());
            if(productoEncontrado.isEmpty()){
                throw new Exception("Producto no encontrado");
            }
            Producto producto = productoEncontrado.get();

            DetalleProductoCita detalleProductoCita = new DetalleProductoCita();
            detalleProductoCita.setCita(cita);
            detalleProductoCita.setProducto(producto);
            detalleProductoCita.setCantidad(dto.cantidad());
            detalleProductoCita.setPrecio(dto.precio());

            detalleProductoCitaList.add(detalleProductoCita);

        }
        return detalleProductoCitaList;
    }

    @Override
    public Cita cancelarCita(Long citaId) throws Exception {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        EstadoCita estadoCancelado = estadoCitaRepository.findByNombre("CANCELADA")
                .orElseThrow(() -> new Exception("Estado de cita 'CANCELADA' no encontrado"));

        cita.setEstadoCita(estadoCancelado);

        NotificarCancelacionDTO notificarCancelacionDTO = crearNotificarCambiosDTO(cita);

        emailService.enviarEmailEstilistaCancelacionCita(cita.getEstilista().getCorreo(), notificarCancelacionDTO);

        return citaRepository.save(cita);
    }

    private NotificarCancelacionDTO crearNotificarCambiosDTO(Cita cita) {
        return new NotificarCancelacionDTO(
                cita.getCliente().getNombre(),
                cita.getFecha()
        );
    }

    @Override
    public Cita reprogramarCita(Long citaId, LocalDateTime nuevaFecha) throws Exception {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        //email

        // Cambiamos la fecha de la cita
        cita.setFecha(nuevaFecha);

        EstadoCita estadoReprogramada = estadoCitaRepository.findByNombre("REPROGRAMADA")
                .orElseThrow(() -> new Exception("Estado de cita 'REPROGRAMADA' no encontrado"));

        cita.setEstadoCita(estadoReprogramada);
        return citaRepository.save(cita);
    }

    private NotificarReprogramacionDTO crearNotificarReprogramacionDTO(Cita cita, LocalDateTime nuevaFecha){
        return new NotificarReprogramacionDTO(
                cita.getCliente().getNombre(),
                cita.getFecha(),
                nuevaFecha
        );
    }

}

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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        citaNueva.setPropina(0.0);
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
    @Override
    public List<InformacionCitasClienteDTO> obtenerHistorialCliente(Long clienteId) {
        List<Cita> citas = citaRepository.obtenerCitasPorCliente(clienteId); // Asume que existe un método que encuentra citas por cliente
        List<InformacionCitasClienteDTO> historialCitas = new ArrayList<>();

        for (Cita cita : citas) {
            List<InformacionDetallesServiciosCitaClienteDTO> serviciosDTO = crearServiciosDTO(cita);
            List<InformacionDetallesProductosCitaClienteDTO> productosDTO = crearProductosDTO(cita);
            InformacionCitasClienteDTO citaDTO = crearCitaDTO(cita, serviciosDTO, productosDTO);

            historialCitas.add(citaDTO);
        }

        return historialCitas;
    }

    // Método privado para crear la lista de servicios DTO
    private List<InformacionDetallesServiciosCitaClienteDTO> crearServiciosDTO(Cita cita) {
        return cita.getDetalleServicioCitas().stream()
                .map(detalleServicio -> new InformacionDetallesServiciosCitaClienteDTO(
                        detalleServicio.getServicio().getId(),
                        detalleServicio.getServicio().getNombre(),
                        detalleServicio.getPrecio()
                ))
                .collect(Collectors.toList());
    }

    // Método privado para crear la lista de productos DTO
    private List<InformacionDetallesProductosCitaClienteDTO> crearProductosDTO(Cita cita) {
        return cita.getDetalleProductoCitas().stream()
                .map(detalleProducto -> new InformacionDetallesProductosCitaClienteDTO(
                        detalleProducto.getProducto().getId(),
                        detalleProducto.getProducto().getNombre(),
                        detalleProducto.getCantidad(),
                        detalleProducto.getPrecio()
                ))
                .collect(Collectors.toList());
    }

    // Método privado para crear el DTO de la cita
    private InformacionCitasClienteDTO crearCitaDTO(Cita cita,
                                                    List<InformacionDetallesServiciosCitaClienteDTO> serviciosDTO,
                                                    List<InformacionDetallesProductosCitaClienteDTO> productosDTO) {
        return new InformacionCitasClienteDTO(
                cita.getId(),
                cita.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                serviciosDTO,
                productosDTO,
                cita.getComentario(),
                cita.getCliente().getNombre(),
                cita.getEstilista().getNombre()
        );
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
            Optional<Servicio> servicioEncontrado = servicioRepository.findById(dto.id());
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
            Optional<Producto> productoEncontrado = productoRepository.findById(dto.id());
            if(productoEncontrado.isEmpty()){
                throw new Exception("Producto no encontrado");
            }
            Producto producto = productoEncontrado.get();

            DetalleProductoCita detalleProductoCita = new DetalleProductoCita();
            detalleProductoCita.setCita(cita);
            detalleProductoCita.setProducto(producto);
            detalleProductoCita.setCantidad(dto.stock());
            detalleProductoCita.setPrecio(dto.precio());

            detalleProductoCitaList.add(detalleProductoCita);

        }
        return detalleProductoCitaList;
    }

    @Override
    public Cita cancelarCita(Long citaId) throws Exception {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));
        if(cita.getEstadoCita().getNombre().equals("CANCELADA")){
            throw new Exception("La cita ya se encuentra cancelada");
        }
        EstadoCita estadoCancelado = estadoCitaRepository.findByNombre("CANCELADA")
                .orElseThrow(() -> new Exception("Estado de cita 'CANCELADA' no encontrado"));

        cita.setEstadoCita(estadoCancelado);

        NotificarCancelacionDTO notificarCancelacionDTO = crearNotificarCambiosDTO(cita);

        emailService.enviarEmailEstilistaCancelacionCita(cita.getEstilista().getCorreo(), notificarCancelacionDTO);

        return citaRepository.save(cita);
    }
    @Override
    public void finalizarCita(Long citaId) throws Exception {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));
        if(cita.getEstadoCita().getNombre().equals("FINALIZADA")){
            throw new Exception("La cita ya se encuentra finalizada");
        }
        EstadoCita estadoCancelado = estadoCitaRepository.findByNombre("FINALIZADA")
                .orElseThrow(() -> new Exception("Estado de cita 'FINALIZADA' no encontrado"));

        cita.setEstadoCita(estadoCancelado);

    }
    private NotificarCancelacionDTO crearNotificarCambiosDTO(Cita cita) {
        return new NotificarCancelacionDTO(
                cita.getCliente().getNombre(),
                cita.getFecha()
        );
    }
    @Override
    public Cita modificarCitaPendiente(Long citaId, ModificarCitaDTO modificarCitaDTO) throws Exception {
        // Buscar la cita existente
        Cita citaExistente = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        // Verificar si la cita está PENDIENTE
        if (!citaExistente.getEstadoCita().getNombre().equals("PENDIENTE")) {
            throw new Exception("Solo se pueden modificar citas pendientes");
        }
        Estilista estilista = null;
        if(Objects.equals(citaExistente.getEstilista().getId(), modificarCitaDTO.estilistaId())){
            estilista = citaExistente.getEstilista();
        }
        estilista = estilistaRepository.findById(modificarCitaDTO.estilistaId())
                .orElseThrow(() -> new Exception("Estilista no encontrado"));

        // Actualizar la fecha si es necesario
        if (modificarCitaDTO.fecha() != null) {
            if(!citaExistente.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).equals(modificarCitaDTO.fecha())){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime nuevaFechaHoraInicio = LocalDateTime.parse(modificarCitaDTO.fecha(), formatter);

                // Obtener duración total de los servicios actuales de la cita
                int duracionTotalCita = obtenerDuracionTotalServicios(citaExistente.getDetalleServicioCitas());

                LocalDateTime nuevaFechaHoraFin = nuevaFechaHoraInicio.plusMinutes(duracionTotalCita); // Hora de fin

                // Verificar si el horario está disponible
                if (!isHorarioDisponible(nuevaFechaHoraInicio, nuevaFechaHoraFin)) {
                    throw new HorarioNoDisponibleException("El horario no está disponible.");
                }

                // Actualizar la fecha de la cita
                citaExistente.setFecha(nuevaFechaHoraInicio);
            }
        }

        // Actualizar los detalles de servicios si es necesario
        List<DetalleServicioCita> nuevosDetallesServicio = convertirDetallesServicioCita(citaExistente, modificarCitaDTO.detalleServicioCitaDTOS());
        if (!nuevosDetallesServicio.isEmpty()) {
            citaExistente.getDetalleServicioCitas().clear();
            citaExistente.getDetalleServicioCitas().addAll(nuevosDetallesServicio);
            detalleServicioCitaRepository.saveAll(nuevosDetallesServicio);
        }

        // Actualizar los detalles de productos si es necesario
        List<DetalleProductoCita> nuevosDetallesProducto = convertirDetallesProductoCita(citaExistente, modificarCitaDTO.detalleProductoCitaDTOS());
        if (!nuevosDetallesProducto.isEmpty()) {
            citaExistente.getDetalleProductoCitas().clear();
            citaExistente.getDetalleProductoCitas().addAll(nuevosDetallesProducto);
            detalleProductoCitaRepository.saveAll(nuevosDetallesProducto);
        }

        // Recalcular el total a pagar en base a los nuevos servicios/productos
        double totalAPagar = calcularTotalAPagarServiciosYProductos(citaExistente.getDetalleServicioCitas(), citaExistente.getDetalleProductoCitas());
        citaExistente.setTotalPago(totalAPagar);
        citaExistente.setEstilista(estilista);
        // Guardar la cita modificada y devolverla
        return citaRepository.save(citaExistente);
    }
    @Override
    public Cita modificarComentarioCita(Long citaId, String comentario) throws Exception {
        // Buscar la cita existente
        Cita citaExistente = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        // Verificar si la cita no está CONFIRMADA o REPROGRAMADA
        if (!citaExistente.getEstadoCita().getNombre().equals("CONFIRMADA") && !citaExistente.getEstadoCita().getNombre().equals("REPROGRAMADA")
                && !citaExistente.getEstadoCita().getNombre().equals("FINALIZADA")) {
            throw new Exception("Solo se pueden comentar citas confirmadas o reprogramadas o finalizadas");
        }

        if (comentario != null) {
            citaExistente.setComentario(comentario);
        }
        // Guardar la cita modificada y devolverla
        return citaRepository.save(citaExistente);
    }
    @Override
    public Cita agregarPropinaCita(Long citaId, double propina) throws Exception {
        // Buscar la cita existente
        Cita citaExistente = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        if (propina <= 0) {
            throw new Exception("Propina no Valida");
        }
        citaExistente.setPropina(citaExistente.getPropina() + propina);
        // Guardar la cita modificada y devolverla
        return citaRepository.save(citaExistente);
    }
    @Override
    public Cita reprogramarCita(Long citaId, LocalDateTime nuevaFecha) throws Exception {
        // Buscar la cita existente
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        // Verificar si la cita no está CONFIRMADA o REPROGRAMADA
        if (!cita.getEstadoCita().getNombre().equals("CONFIRMADA") && !cita.getEstadoCita().getNombre().equals("REPROGRAMADA")) {
            throw new Exception("Solo se pueden reprogramar citas confirmadas o reprogramadas");
        }

        // Obtener la duración total de los servicios actuales de la cita
        int duracionTotalCita = obtenerDuracionTotalServicios(cita.getDetalleServicioCitas());

        // Calcular la nueva hora de fin en base a la nueva fecha y duración
        LocalDateTime nuevaFechaHoraFin = nuevaFecha.plusMinutes(duracionTotalCita);

        // Verificar si el horario está disponible
        if (!isHorarioDisponible(nuevaFecha, nuevaFechaHoraFin)) {
            throw new HorarioNoDisponibleException("El horario no está disponible.");
        }

        // Cambiar la fecha de la cita si el horario es válido
        cita.setFecha(nuevaFecha);

        // Cambiar el estado de la cita a "REPROGRAMADA"
        EstadoCita estadoReprogramada = estadoCitaRepository.findByNombre("REPROGRAMADA")
                .orElseThrow(() -> new Exception("Estado de cita 'REPROGRAMADA' no encontrado"));

        cita.setEstadoCita(estadoReprogramada);

        // Guardar y devolver la cita reprogramada
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

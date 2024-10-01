package co.edu.uniquindio.proyectois2backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago {

    @EqualsAndHashCode.Include
    private String id;

    private Double total;
    private List<String> detallePago;
    private EstadoPago estadoPago;
    // TODO ¿Implementar pasarela?
}

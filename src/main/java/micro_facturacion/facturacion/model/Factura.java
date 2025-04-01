package micro_facturacion.facturacion.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    private int id;
    private String cliente;
    private List<ServicioVeterinario> servicios;
    private double total;
}

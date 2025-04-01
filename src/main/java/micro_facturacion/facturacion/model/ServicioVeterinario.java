package micro_facturacion.facturacion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioVeterinario {
    private int id;
    private String nombre;
    private double costo;
}


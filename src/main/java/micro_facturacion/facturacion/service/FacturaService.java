package micro_facturacion.facturacion.service;
import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.model.ServicioVeterinario;
import micro_facturacion.facturacion.exeption.FacturaNotFound;
import java.util.ArrayList;
import java.util.List;


public class FacturaService {
    private final List<Factura> facturas = new ArrayList<>();

    public FacturaService() {
        List<ServicioVeterinario> servicios1 = new ArrayList<>();
        servicios1.add(new ServicioVeterinario(1, "Consulta General", 30.0));
        servicios1.add(new ServicioVeterinario(2, "Vacunación", 20.0));

        List<ServicioVeterinario> servicios2 = new ArrayList<>();
        servicios2.add(new ServicioVeterinario(3, "Cirugía menor", 150.0));
        servicios2.add(new ServicioVeterinario(4, "Desparasitación", 25.0));

        facturas.add(new Factura(1, "Juan Pérez", servicios1, calcularTotal(servicios1)));
        facturas.add(new Factura(2, "María Gómez", servicios2, calcularTotal(servicios2)));
    }

    private double calcularTotal(List<ServicioVeterinario> servicios) {
        return servicios.stream().mapToDouble(ServicioVeterinario::getCosto).sum();
    }

    public List<Factura> obtenerFacturas() {
        return facturas;
    }

    public Factura obtenerFacturaPorId(int id) {
        return facturas.stream()
                .filter(factura -> factura.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FacturaNotFound((long) id));
    }
}


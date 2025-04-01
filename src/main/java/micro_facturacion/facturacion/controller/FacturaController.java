package micro_facturacion.facturacion.controller;
import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.model.ServicioVeterinario;
import micro_facturacion.facturacion.service.FacturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController() {
        this.facturaService = new FacturaService();
    }

    @GetMapping
    public List<Factura> obtenerFacturas() {
        return facturaService.obtenerFacturas();
    }

    @GetMapping("/{id}")
    public Factura obtenerFacturaPorId(@PathVariable int id) {
        return facturaService.obtenerFacturaPorId(id);
    }
}


package micro_facturacion.facturacion.controller;
import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.model.ServicioVeterinario;
import micro_facturacion.facturacion.service.FacturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> buscarRegistro() {
        return facturaService.buscarFactura();
    }

    @GetMapping("/{id}")
    public Optional<Factura> buscarId(@PathVariable Long id) {
        return facturaService.buscarId(id);
    }

    @PostMapping
    public Factura createFactura(@RequestBody Factura CreateFactura) {
        return facturaService.createFactura(CreateFactura);
    }

    @DeleteMapping("/{id}")
    public void eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
    }

    @PutMapping("/{id}")
    public Factura updateFactura(@PathVariable Long id, @RequestBody Factura updateFactura) {
        return facturaService.updateFactura(id, updateFactura);
    }
}


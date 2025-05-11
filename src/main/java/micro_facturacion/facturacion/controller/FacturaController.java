package micro_facturacion.facturacion.controller;
import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.model.ServicioVeterinario;
import micro_facturacion.facturacion.service.FacturaService;
import micro_facturacion.facturacion.exeption.FacturaNotFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel; 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    private static final Logger log = LoggerFactory.getLogger(FacturaController.class);
    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public CollectionModel<EntityModel<Factura>> buscarRegistro() {
        List<Factura> facturas = facturaService.buscarFactura();
        log.info("GET /facturas");
        log.info("Retornando todas las facturas");

        List<EntityModel<Factura>> facturaResources = facturas.stream()
                .map(facturaItem -> EntityModel.of(facturaItem,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarId(facturaItem.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());


        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro());
        CollectionModel<EntityModel<Factura>> resources = CollectionModel.of(facturaResources, linkTo.withRel("facturaItems"));
        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<Factura>buscarId(@PathVariable Long id) {
        Optional<Factura> factura = facturaService.buscarId(id);
        if (factura.isPresent()) {
            log.info("GET /facturas/{}", id);
            log.info("Retornando la factura con ID: {}", id);
            return EntityModel.of(factura.get(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarId(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro()).withRel("facturaItems"));
        } else {
            log.error("Factura no encontrada con ID: {}", id);
            throw new FacturaNotFound(id);
        }
    }

    // @PostMapping
    // public EntityModel<Factura> createFactura(@RequestBody Factura CreateFactura) {
    //     Factura newFactura = facturaService.createFactura(CreateFactura);
    //     return EntityModel.of(newFactura,
    //             WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarId(newFactura.getId())).withSelfRel(),
    //             WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro()).withRel("facturaItems"));
    // }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Factura> createFactura(@RequestBody Factura CreateFactura) {
        Factura newFactura = facturaService.createFactura(CreateFactura);
        return EntityModel.of(newFactura,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarId(newFactura.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro()).withRel("facturaItems"));
    }   


    // @DeleteMapping("/{id}")
    // public EntityModel<String> eliminarFactura(@PathVariable Long id) {
    //     facturaService.eliminarFactura(id);
    //     return EntityModel.of("Factura eliminada con éxito",
    //             WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro()).withRel("facturaItems"));
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFactura(@PathVariable Long id) {
        try {
            facturaService.eliminarFactura(id);
            return ResponseEntity.ok("Factura eliminada con éxito");
        } catch (FacturaNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Factura no encontrada");
        }
    }


    @PutMapping("/{id}")
    public EntityModel<Factura> updateFactura(@PathVariable Long id, @RequestBody Factura updateFactura) {
        Optional<Factura> existingFactura = facturaService.buscarId(id);
        if (existingFactura.isPresent()) {
            Factura updatedFactura = facturaService.updateFactura(id, updateFactura);
            return EntityModel.of(updatedFactura,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarId(id)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).buscarRegistro()).withRel("facturaItems"));
        } else {
            log.error("Factura no encontrada con ID: {}", id);
            throw new FacturaNotFound(id);
        }
    }
}
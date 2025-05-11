package micro_facturacion.facturacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.repository.FacturaRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class FacturaTestService {

    @InjectMocks
    private ServiceImplementation serviceImplementation;
    
    @Mock
    private FacturaRepository facturaRepository;

    @BeforeEach
    public void setUp() {
        facturaRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        facturaRepository.deleteAll();
    }

    @Test
    //se crear un objeto de tipo factura y se le asignan valores a sus atributos
    public void saveCliente() {
        Factura factura = new Factura();
        factura.setCliente("Cliente 1");
        factura.setServicio("Servicio 1");
        factura.setCosto(100);

        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        //act: se llama al método createFactura de la clase ServiceImplementation
        Factura resultado = serviceImplementation.createFactura(factura);

        //assert: se verifica que el resultado sea el esperado
        assertEquals(factura.getCliente(), resultado.getCliente());
        assertEquals(factura.getServicio(), resultado.getServicio());
        assertEquals(factura.getCosto(), resultado.getCosto()); 
    }

    @Test
    public void testBuscarFacturas() {
        Factura factura1 = new Factura(1L, "Cliente 1", "Servicio 1", 100);
        Factura factura2 = new Factura(2L, "Cliente 2", "Servicio 2", 200);
        List<Factura> facturas = List.of(factura1, factura2);
    
        when(facturaRepository.findAll()).thenReturn(facturas);
    
        List<Factura> resultado = serviceImplementation.buscarFactura();
    
        assertEquals(2, resultado.size());
        assertEquals("Cliente 1", resultado.get(0).getCliente());
        assertEquals("Cliente 2", resultado.get(1).getCliente());
    }

    @Test
    public void testBuscarFacturaPorId() {
        Factura factura = new Factura(1L, "Cliente 1", "Servicio 1", 100);
    
        when(facturaRepository.findById(1L)).thenReturn(java.util.Optional.of(factura));
    
        Factura resultado = serviceImplementation.buscarId(1L).orElse(null);
    
        assertEquals("Cliente 1", resultado.getCliente());
        assertEquals("Servicio 1", resultado.getServicio());
        assertEquals(100, resultado.getCosto());
    }

    @Test
    public void testActualizarFactura() {
        Factura original = new Factura(1L, "Original", "Servicio", 100);
        Factura actualizada = new Factura(1L, "Actualizado", "Nuevo Servicio", 200);
    
        when(facturaRepository.existsById(1L)).thenReturn(true);
        when(facturaRepository.save(actualizada)).thenReturn(actualizada);
    
        Factura resultado = serviceImplementation.updateFactura(1L, actualizada);
    
        assertEquals("Actualizado", resultado.getCliente());
        assertEquals("Nuevo Servicio", resultado.getServicio());
        assertEquals(200, resultado.getCosto());
    }

    @Test
    public void testEliminarFactura() {
        Factura factura = new Factura(1L, "Cliente 1", "Servicio 1", 100);
    
        when(facturaRepository.findById(1L)).thenReturn(java.util.Optional.of(factura));
    
        serviceImplementation.eliminarFactura(1L);
    
        // Verificar que la factura fue eliminada
        when(facturaRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Factura resultado = serviceImplementation.buscarId(1L).orElse(null);
    
        assertEquals(null, resultado);
    }
}

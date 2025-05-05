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
}

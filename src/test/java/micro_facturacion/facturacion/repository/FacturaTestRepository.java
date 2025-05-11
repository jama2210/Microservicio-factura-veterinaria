package micro_facturacion.facturacion.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import micro_facturacion.facturacion.model.Factura;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class FacturaTestRepository {
    @Autowired
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
    public void saveFactura() {
        Factura factura = new Factura();
        factura.setCliente("Cliente 1");
        factura.setServicio("Servicio 1");
        factura.setCosto(100);

        Factura resultado = facturaRepository.save(factura);

        assertNotNull(resultado.getId());
        assertEquals(factura.getCliente(), resultado.getCliente());
        assertEquals(factura.getServicio(), resultado.getServicio());
        assertEquals(factura.getCosto(), resultado.getCosto());
    }
}

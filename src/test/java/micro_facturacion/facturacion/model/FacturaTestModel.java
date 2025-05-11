package micro_facturacion.facturacion.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacturaTestModel {

    @Test
    void testConstructorConArgumentos() {
        Factura factura = new Factura(1L, "Juan Pérez", "Consulta", 30000.0);

        assertEquals(1L, factura.getId());
        assertEquals("Juan Pérez", factura.getCliente());
        assertEquals("Consulta", factura.getServicio());
        assertEquals(30000.0, factura.getCosto());
    }

    @Test
    void testSettersYGetters() {
        Factura factura = new Factura();
        factura.setId(2L);
        factura.setCliente("Ana Torres");
        factura.setServicio("Vacunación");
        factura.setCosto(15000.0);

        assertEquals(2L, factura.getId());
        assertEquals("Ana Torres", factura.getCliente());
        assertEquals("Vacunación", factura.getServicio());
        assertEquals(15000.0, factura.getCosto());
    }

    @Test
    void testEqualsYHashCode() {
        Factura f1 = new Factura(1L, "Cliente A", "Servicio A", 10000.0);
        Factura f2 = new Factura(1L, "Cliente A", "Servicio A", 10000.0);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void testToString() {
        Factura factura = new Factura(1L, "Cliente B", "Baño", 20000.0);

        String toString = factura.toString();
        assertTrue(toString.contains("Cliente B"));
        assertTrue(toString.contains("Baño"));
        assertTrue(toString.contains("20000.0"));
    }
}

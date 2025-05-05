package micro_facturacion.facturacion.controller;
import micro_facturacion.facturacion.service.FacturaService;
import  micro_facturacion.facturacion.model.Factura;


import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.stream.Collectors;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;


@WebMvcTest(FacturaController.class)
public class FacturaTestController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaServiceMock;

    @Test
    public void testGetFacturas() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L); // Ejemplo de ID
        factura.setCliente("Cliente 1");
        factura.setServicio("Servicio 1");
        factura.setCosto(100.0);

        List<Factura> facturas = List.of(factura);

        List<EntityModel<Factura>> entityModels = facturas.stream()
                .map(facturaItem -> EntityModel.of(facturaItem))
                .collect(Collectors.toList());

        when(facturaServiceMock.buscarFactura()).thenReturn(facturas);

        mockMvc.perform(MockMvcRequestBuilders.get("/facturas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.facturaList[0].cliente", Matchers.is("Cliente 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.facturaList[0].servicio", Matchers.is("Servicio 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.facturaList[0].costo", Matchers.is(100.0)));
}}
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Optional;


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
        }

        @Test 
        public void testGetFacturaPorId() throws Exception {
                Factura factura = new Factura(1L, "Cliente Test", "Servicio Test", 1234.0);
                when(facturaServiceMock.buscarId(1L)).thenReturn(Optional.of(factura));

                mockMvc.perform(get("/facturas/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.cliente").value("Cliente Test"))
                        .andExpect(jsonPath("$.servicio").value("Servicio Test"))
                        .andExpect(jsonPath("$.costo").value(1234.0));
        }

        @Test
        public void testGetFacturaPorIdNoEncontrada() throws Exception {
                when(facturaServiceMock.buscarId(999L)).thenReturn(Optional.empty());

                mockMvc.perform(get("/facturas/999"))
                        .andExpect(status().isNotFound());
        }

        @Test
        public void testCrearFactura() throws Exception {
                Factura factura = new Factura(1L, "Cliente Test", "Servicio Test", 1234.0);
                when(facturaServiceMock.createFactura(any(Factura.class))).thenReturn(factura);


                mockMvc.perform(MockMvcRequestBuilders.post("/facturas")
                                .contentType("application/json")
                                .content("{\"cliente\":\"Cliente Test\",\"servicio\":\"Servicio Test\",\"costo\":1234.0}"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.cliente").value("Cliente Test"))
                        .andExpect(jsonPath("$.servicio").value("Servicio Test"))
                        .andExpect(jsonPath("$.costo").value(1234.0));
        }

        // @Test
        // public void testEliminarFactura() throws Exception {
        //         mockMvc.perform(MockMvcRequestBuilders.delete("/facturas/1"))
        //                 .andExpect(status().isOk())
        //                 .andExpect(jsonPath("$").value("Factura eliminada con éxito"));
        // }
        @Test
        public void testEliminarFactura() throws Exception {
                doNothing().when(facturaServiceMock).eliminarFactura(1L);

                mockMvc.perform(MockMvcRequestBuilders.delete("/facturas/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").value("Factura eliminada con éxito"));
        }

        @Test
        public void testActualizarFactura() throws Exception {
                Factura facturaActualizada = new Factura(1L, "Cliente Actualizado", "Servicio Actualizado", 4321.0);

                when(facturaServiceMock.buscarId(1L)).thenReturn(Optional.of(facturaActualizada));
                when(facturaServiceMock.updateFactura(eq(1L), any(Factura.class))).thenReturn(facturaActualizada);

                mockMvc.perform(MockMvcRequestBuilders.put("/facturas/1")
                        .contentType("application/json")
                        .content("{\"cliente\":\"Cliente Actualizado\",\"servicio\":\"Servicio Actualizado\",\"costo\":4321.0}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.cliente").value("Cliente Actualizado"))
                        .andExpect(jsonPath("$.servicio").value("Servicio Actualizado"))
                        .andExpect(jsonPath("$.costo").value(4321.0));
        }

}
package com.dh.integradora.OdontologoTest;

import com.dh.integradora.entities.Odontologo;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.service.DomicilioService;
import com.dh.integradora.service.OdontologoService;
import com.dh.integradora.service.PacienteService;
import com.dh.integradora.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionOdontologosTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private DomicilioService domicilioService;
    @Autowired
    private MockMvc mockMvc;

    public void cargarDatosEnBD() throws BadRequestException {
        odontologoService.registrarOdontologo(new Odontologo("Specter","Olive",5746521));
    }
    @Test
    public void buscarOdontologos() throws Exception {
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void cargar() throws Exception {

        Odontologo odontologo = new Odontologo(5L,"Lucy","Harris",3785436);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String odontologoJSON = writer.writeValueAsString(odontologo);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void borrar() throws Exception {
        String respuesta = "Odontologo eliminado";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/odontologos/{id}","3").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void actualizar() throws Exception {
        Odontologo odontologo = new Odontologo(5L,"Olive","Specter",6519852);
        String respuesta = "{\"id\":5,\"nombre\":\"Olive\",\"apellido\":\"Specter\",\"matricula\":6519852}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String odontologoJSON = writer.writeValueAsString(odontologo);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }
}
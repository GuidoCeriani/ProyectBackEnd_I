package com.dh.integradora.TurnosTest;

import com.dh.integradora.entities.Domicilio;
import com.dh.integradora.entities.Odontologo;
import com.dh.integradora.entities.Paciente;
import com.dh.integradora.entities.Turno;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.service.DomicilioService;
import com.dh.integradora.service.OdontologoService;
import com.dh.integradora.service.PacienteService;
import com.dh.integradora.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnosTest {
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

    public void cargarDatosEnBD() throws ResourceNotFoundException, BadRequestException {

        domicilioService.registrarDomicilio(new Domicilio("Calle Sim",5,"Willow Creek","SimCity"));
        pacienteService.guardar(new Paciente("Goth","Bella","bella.goth@gmail.com",3785161,LocalDate.of(2022,02,03),domicilioService.buscar(1L).get()));
        odontologoService.registrarOdontologo(new Odontologo("Specter","Olive",5746521));
        turnoService.registrarTurno(new Turno(pacienteService.buscar(1L).get(), odontologoService.buscar(1L).get(), LocalDate.of(2022,05,01)));
    }
    @Test
    public void listarTurnos() throws Exception {
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void cargar() throws Exception {

        Turno turno = new Turno(3L,pacienteService.buscar(1L).get(), odontologoService.buscar(1L).get(), LocalDate.of(2022,05,01));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        String turnoJSON = objectMapper.writeValueAsString(turno);


        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void Borrar() throws Exception {
        String respuesta = "Turno con id=3 eliminado";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/turnos/{id}","3").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void actualizar() throws Exception {
        Turno turno = new Turno(1L,pacienteService.buscar(1L).get(), odontologoService.buscar(1L).get(), LocalDate.of(2022,05,01));
        String respuesta = "{\"id\":1,\"paciente\":{\"id\":1,\"apellido\":\"Goth\",\"nombre\":\"Bella\",\"email\":\"Bella.Goth@gmail.com\",\"dni\":37805662,\"fechaIngreso\":\"2022-12-03\",\"domicilio\":{\"id\":1,\"calle\":\"Calle Sim\",\"numero\":5,\"localidad\":\"Willow Creek\",\"provincia\":\"SimCity\"}},\"odontologo\":{\"id\":1,\"nombre\":\"Olive\",\"apellido\":\"Specter\",\"matricula\":457515},\"fecha\":\"2022-05-01\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        String turnoJSON = objectMapper.writeValueAsString(turno);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }
}

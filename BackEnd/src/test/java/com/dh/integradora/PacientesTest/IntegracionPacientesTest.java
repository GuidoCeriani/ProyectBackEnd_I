package com.dh.integradora.PacientesTest;

import com.dh.integradora.entities.Domicilio;
import com.dh.integradora.entities.Paciente;
import com.dh.integradora.exceptions.BadRequestException;
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
public class IntegracionPacientesTest {
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

        pacienteService.guardar(new Paciente("Goth","Bella","bella.goth@gmail.com",3785161, LocalDate.of(2022,02,03),new Domicilio("Calle sim",5,"Willow Creek","SimCity")));

    }
    @Test
    public void buscarPacientes() throws Exception {
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void cargar() throws Exception {

        Paciente paciente = new Paciente(5L,"Goth","Mortimer","Mortimer.goth@gmail.com",65168461,LocalDate.of(2022,02,07),new Domicilio("Calle sim",5,"Willow Creek","SimCity"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        String pacienteJSON = objectMapper.writeValueAsString(paciente);


        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void Borrar() throws Exception {
        String respuesta = "Paciente eliminado con id: 5";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/pacientes/{id}","5").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void actualizar() throws Exception {
        Paciente paciente = new Paciente(3L,"Goth","Cassandra","Mortimer.goth@gmail.com",65168461,LocalDate.of(2022,02,07),new Domicilio("Calle sim",5,"Willow Creek","SimCity"));
        String respuesta = "{\"id\":3,\"apellido\":\"Goth\",\"nombre\":\"Cassandra\",\"email\":\"Mortimer.goth@gmail.com\",\"dni\":65168461,\"fechaIngreso\":\"2022-02-07\",\"domicilio\":{\"id\":6,\"calle\":\"Calle sim\",\"numero\":5,\"localidad\":\"Willow Creek\",\"provincia\":\"SimCity\"}}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        String pacienteJSON = objectMapper.writeValueAsString(paciente);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }
}
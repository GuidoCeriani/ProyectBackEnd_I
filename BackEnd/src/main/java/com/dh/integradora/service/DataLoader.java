package com.dh.integradora.service;

import com.dh.integradora.entities.*;
import com.dh.integradora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OdontologoRepository odontologoRepository;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    DomicilioRepository domicilioRepository;
    @Autowired
    DomicilioService domicilioService;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    TurnoRepository turnoRepository;
    @Autowired
    TurnoService turnoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String pass = passwordEncoder.encode("digital");
        String passAdmin = passwordEncoder.encode("admin");

        userRepository.save(new AppUser("Bella","Bella","bella.goth@gmail.com",pass, AppUsuarioRoles.ROLE_USER));
        userRepository.save(new AppUser("Admin","Admin","gmceriani888@hotmail.com",passAdmin, AppUsuarioRoles.ROLE_ADMIN));

        Odontologo odontologo1 = odontologoRepository.save(odontologoService.registrarOdontologo(new Odontologo("Olive","Specter",457515)));
        Odontologo odontologo2 = odontologoRepository.save(odontologoService.registrarOdontologo(new Odontologo("Ophelia","Specter",65165)));
        Odontologo odontologo3 = odontologoRepository.save(odontologoService.registrarOdontologo(new Odontologo("Birch","Juno",541615)));
        Odontologo odontologo4 = odontologoRepository.save(odontologoService.registrarOdontologo(new Odontologo("Judith","Ward",564568)));

        Paciente paciente1 = pacienteRepository.save(new Paciente("Goth","Bella","Bella.Goth@gmail.com",37805662,LocalDate.of(2022,12,03),new Domicilio("Calle Sim",5,"Willow Creek","SimCity")));
        Paciente paciente2 = pacienteRepository.save(new Paciente("Goth","Mortimer","mortimer.Goth@gmail.com",45345345,LocalDate.of(2022,02,23),new Domicilio("Calle Sim",5,"Willow Creek","SimCity")));
        Paciente paciente3 = pacienteRepository.save(new Paciente("Goth","Cassandra","Cass.Goth@gmail.com",453453453,LocalDate.of(2022,04,13),new Domicilio("Calle Sim",5,"Willow Creek","SimCity")));
        Paciente paciente4 = pacienteRepository.save(new Paciente("Goth","Alejandro","Al.Goth@gmail.com",378035662,LocalDate.of(2022,01,14),new Domicilio("Calle Sim",5,"Willow Creek","SimCity")));

        turnoRepository.save(new Turno(paciente1, odontologo1,LocalDate.of(2022,8,20)));
        turnoRepository.save(new Turno(paciente2, odontologo2, LocalDate.of(2022,12,22)));
        turnoRepository.save(new Turno(paciente3, odontologo3, LocalDate.of(2022,4,5)));
        turnoRepository.save(new Turno(paciente4, odontologo4, LocalDate.of(2022,11,1)));
    }
}

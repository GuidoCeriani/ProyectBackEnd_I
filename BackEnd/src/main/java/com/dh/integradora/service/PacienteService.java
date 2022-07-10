package com.dh.integradora.service;

import com.dh.integradora.entities.Paciente;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PacienteService{
    @Autowired
    PacienteRepository repository;

    Logger logger = Logger.getLogger(PacienteService.class);

    public List<Paciente> buscarTodos()throws ResourceNotFoundException {
        List<Paciente> pacientes=repository.findAll();
        if (pacientes.isEmpty())
            throw new ResourceNotFoundException("No existen pacientes.");
        else{
            logger.info("Se encontraron "+pacientes.size()+" pacientes.");
            return pacientes;
        }
    }

    public Paciente guardar(Paciente p) throws BadRequestException {
        Optional<Paciente> pacienteBuscado= repository.findByEmail(p.getEmail());
        if (pacienteBuscado.isPresent())
            throw new BadRequestException("Ya existe un paciente con id: "+p.getId()+", no se puede guardar.");
        else
            return repository.save(p);
    }

    public void eliminar(Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado=buscar(id);
        if (pacienteBuscado.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("No existe el paciente con id: "+id+", no se puede borrar.");
        }
    }

    public Optional<Paciente> buscar(Long id)throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=repository.findById(id);
        if (pacienteBuscado.isPresent()) {
            logger.info("Se encontró el paciente con id: " + id);
            return pacienteBuscado;
        }else
            throw new ResourceNotFoundException("No existe el paciente con id: "+id+".");
    }

    public Optional<Paciente> buscarXEmail(String email)throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=buscarXEmail(email);
        if (pacienteBuscado.isPresent()) {
            logger.info("Se encontró el paciente con email: " + email);
            return pacienteBuscado;
        }else
            throw new ResourceNotFoundException("No existe el paciente con email: "+email+".");
    }

    public Paciente actualizar(Paciente p) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=buscar(p.getId());
        if (pacienteBuscado.isPresent())
            return repository.save(p);
        else
            return null;
    }

}

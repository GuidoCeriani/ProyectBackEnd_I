package com.dh.integradora.service;

import com.dh.integradora.entities.Odontologo;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    OdontologoRepository repository;

    Logger logger = Logger.getLogger(OdontologoService.class);

    public Optional<Odontologo> buscar(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= repository.findById(id);
        if (odontologoBuscado.isPresent()){
            logger.info("Se encontro el odontologo con id: "+id);
            return odontologoBuscado;
        }else{
            throw new ResourceNotFoundException("No se puede encontrar el odontologo con id: "+id+", error al buscar");
        }
    }

    public Optional<Odontologo> buscarXMatricula(int matricula)throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= repository.findByMatricula(matricula);
        if (odontologoBuscado.isPresent()){
            logger.info("Odontologo encontrado: "+odontologoBuscado.get().getMatricula());
            return odontologoBuscado;
        } else {
            throw new ResourceNotFoundException("No se puede encontrar el odontologo con matricula: "+matricula+".");
        }
    }

    public List<Odontologo> buscarTodos()throws BadRequestException {
        List<Odontologo> odontologos= repository.findAll();
        if (odontologos.isEmpty()){
            throw new BadRequestException("No hay odontologos registrados.");
        }else{
            return odontologos;
        }
    }

    public Odontologo registrarOdontologo(Odontologo odontologo)throws BadRequestException{
        Optional<Odontologo> odontologoBuscado = repository.findByMatricula(odontologo.getMatricula());
        if (odontologoBuscado.isPresent())
            throw new BadRequestException("Ya existe un odontologo con matricula: "+odontologo.getMatricula()+".");
        else
            return repository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= buscar(id);
        if (odontologoBuscado.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("No se puede borrar el odontologo con id: "+id+", error al borrar");
        }

    }

    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException {
        if (buscar(odontologo.getId()).isPresent())
            return repository.save(odontologo);
        else
            return null;
    }
}

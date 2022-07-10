package com.dh.integradora.service;

import com.dh.integradora.entities.Domicilio;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.repository.DomicilioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService {
    @Autowired
    DomicilioRepository repository;

    private static final Logger logger = Logger.getLogger(DomicilioService.class);

    public Optional<Domicilio> buscar(Long id) throws ResourceNotFoundException {
        Optional<Domicilio> domicilio = repository.findById(id);
        if (domicilio.isPresent())
            return domicilio;
        else {
            logger.error("No se encontro el domicilio con id: " + id);
            throw new ResourceNotFoundException("No se encontro el domicilio con id: " + id);
        }
    }

    public List<Domicilio> buscarTodos() throws ResourceNotFoundException {
        List<Domicilio> domicilios = repository.findAll();
        if (domicilios.isEmpty()) {
            logger.error("No se encontraron domicilios");
            throw new ResourceNotFoundException("No se encontraron domicilios");
        }else
            return domicilios;
    }

    public Domicilio registrarDomicilio(Domicilio domicilio)throws BadRequestException {
        if (domicilio.getId() != null)
            throw new BadRequestException("El domicilio ya existe");
        else
            return repository.save(domicilio);
    }

    public void eliminarDomicilio(Long id)throws ResourceNotFoundException {
        if (buscar(id).isPresent()){
            logger.info("Se elimino el domicilio con id: " + id);
            repository.deleteById(id);
        }else
            throw new ResourceNotFoundException("No se encontro el domicilio con id: " + id);
    }

    public Domicilio actualizar(Domicilio domicilio) throws ResourceNotFoundException {
        if (buscar(domicilio.getId()).isPresent())
            return repository.save(domicilio);
        else
            return null;
    }
}

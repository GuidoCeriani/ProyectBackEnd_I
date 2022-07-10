package com.dh.integradora.service;

import com.dh.integradora.entities.Turno;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    TurnoRepository repository;

    Logger logger = Logger.getLogger(TurnoService.class);

    public Turno registrarTurno(Turno turno){
        return repository.save(turno);
    }

    public List<Turno> listarTurno()throws ResourceNotFoundException {
        List<Turno> turnos = repository.findAll();
        if (turnos.isEmpty())
            throw new ResourceNotFoundException("No hay turnos registrados");
        else{
            logger.info("Lista de turnos obtenida");
            return turnos;
        }
    }

    public void eliminar (Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado= buscar(id);
        if (turnoBuscado.isPresent())
            repository.deleteById(id);
        else
            throw new ResourceNotFoundException("No existe el turno con id: "+id+", no se pudo borrar");
    }

    public Turno actualizar(Turno turno)throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado=buscar(turno.getId());
        if (turnoBuscado.isPresent()) {
            logger.info("Turno actualizado");
            return repository.save(turno);
        }
        else
            return null;
    }

    public Optional<Turno> buscar(Long id)throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado=repository.findById(id);
        if (turnoBuscado.isPresent()) {
            logger.info("Turno encontrado");
            return turnoBuscado;
        }
        else
            throw new ResourceNotFoundException("No existe el turno con id: " + id);

    }
}

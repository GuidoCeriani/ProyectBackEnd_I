package com.dh.integradora.controller;

import com.dh.integradora.entities.Odontologo;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    OdontologoService service;

    @GetMapping
    public List<Odontologo> buscarOdontologos() throws BadRequestException {
        return service.buscarTodos();
    }

    @PostMapping
    public Odontologo registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        return service.registrarOdontologo(odontologo);
    }
    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Odontologo odontologoActualizado=service.actualizar(odontologo);
        if (odontologoActualizado!=null){
            return ResponseEntity.ok(odontologoActualizado);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoActualizado=service.buscar(id);
        if(odontologoActualizado.isPresent()){
            return ResponseEntity.ok(odontologoActualizado.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("matricula={matricula}")
    public ResponseEntity<Odontologo> buscarOdontologoPorMatricula(@PathVariable int matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoActualizado=service.buscarXMatricula(matricula);
        if(odontologoActualizado.isPresent()){
            return ResponseEntity.ok(odontologoActualizado.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarOdontologo(id);
           return ResponseEntity.ok("Odontologo eliminado");

    }
}

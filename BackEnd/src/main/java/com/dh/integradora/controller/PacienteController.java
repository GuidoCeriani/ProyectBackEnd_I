package com.dh.integradora.controller;

import com.dh.integradora.entities.Paciente;
import com.dh.integradora.exceptions.BadRequestException;
import com.dh.integradora.exceptions.ResourceNotFoundException;
import com.dh.integradora.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> buscarPacientes() throws ResourceNotFoundException {
        return pacienteService.buscarTodos();
    }

    @PostMapping
    public Paciente registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        return pacienteService.guardar(paciente);
    }
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Paciente pacienteActualizado=pacienteService.actualizar(paciente);
        if (pacienteActualizado!=null){
            return ResponseEntity.ok(pacienteActualizado);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscar(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
            pacienteService.eliminar(id);
            return ResponseEntity.ok("Paciente eliminado con id: "+id);
    }

    @GetMapping("/email={email}")
    public ResponseEntity<Paciente> buscarPacienteXEmail(@PathVariable String email) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarXEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
        /*
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

     */
}

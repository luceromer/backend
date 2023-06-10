package com.dh.clinica.controller;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.persistence.dao.impl.DomicilioDaoH2;
import com.dh.clinica.persistence.dao.impl.OdontologoDaoH2;
import com.dh.clinica.persistence.dao.impl.PacienteDaoH2;
import com.dh.clinica.persistence.dao.impl.TurnoListDao;
import com.dh.clinica.model.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) {
        ResponseEntity<Turno> respuesta;
        if(pacienteService.buscar(turno.getPaciente().getId()) != null
                && odontologoService.buscar(turno.getOdontologo().getId()) != null){
            respuesta = ResponseEntity.ok(turnoService.registrarTurno(turno));
        }else{
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    @PutMapping()
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        ResponseEntity<Turno> response = null;
        if (turno.getId() != null && turnoService.buscar(turno.getId()).isPresent())
            response = ResponseEntity.ok(turnoService.actualizar(turno));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        ResponseEntity<String> response = null;
        if (turnoService.buscar(id).isPresent()) {
            turnoService.eliminar(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

}

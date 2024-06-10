package com.escola.domain.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;
import com.escola.domain.service.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Aluno alunoToCreate) throws CustomException {
        try{
            Aluno alunoCreated = alunoService.create(alunoToCreate);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alunoCreated.getId())
                .toUri();
    
            return ResponseEntity.created(location).body(alunoCreated);
        }
        catch (CustomException ex) {
            throw ex;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);

        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Aluno> findByName(@PathVariable String nome) {
        Aluno aluno = alunoService.findByName(nome);

        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/serie/{serie}")
    public ResponseEntity<List<Aluno>> findBySerie(@PathVariable String serie) {
        List<Aluno> alunos = alunoService.findBySerie(serie.toUpperCase());

        return ResponseEntity.ok(alunos);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> getAll() {
        List<Aluno> alunos = alunoService.getAll();

        return ResponseEntity.ok(alunos);
    }

    @PatchMapping
    public ResponseEntity<Aluno> update(@RequestBody Aluno alunoToUpdate) {
        Aluno alunoUpdated = alunoService.update(alunoToUpdate.getId(), alunoToUpdate.getNome(), alunoToUpdate.getDataNascimento(), alunoToUpdate.getSerie(), alunoToUpdate.getSegmento(), alunoToUpdate.getNomeMae(), alunoToUpdate.getNomePai());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(alunoUpdated.getId())
            .toUri();

        return ResponseEntity.created(location).body(alunoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Aluno> delete(@PathVariable Long id) {
        Aluno alunoDeleted = alunoService.delete(id);

        return ResponseEntity.ok(alunoDeleted);
    }

}

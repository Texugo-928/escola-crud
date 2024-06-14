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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/aluno")
@Tag(name = "Aluno Controller", description = "RESTful API for managing alunos.")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @Operation(summary = "Create a new aluno", description = "Create a new aluno and return the created aluno's data")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Aluno created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }")
                    )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Treated Exception",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        ),
        //@ApiResponse(responseCode = "422", description = "Invalid aluno data provided"),
        @ApiResponse(
            responseCode = "500",
            description = "Unexpected Server Error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Unexpected Server Error, see the logs.")
            )
        )
    })
    public ResponseEntity<Object> create(@RequestBody Aluno alunoToCreate) throws CustomException {
        try{
            Aluno alunoCreated = alunoService.create(alunoToCreate);

            URI location = generateUriById(alunoCreated);
    
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
    @Operation(summary = "Get a aluno by ID", description = "Retrieve a specific aluno based on its ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }")
            )            
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Aluno not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Resource Not Found")
            )
        )
    })
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);

        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Get a aluno by name", description = "Retrieve a specific aluno based on its name")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }")
            )            
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Aluno not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Resource Not Found")
            )
        )
    })
    public ResponseEntity<Aluno> findByName(@PathVariable String nome) {
        Aluno aluno = alunoService.findByName(nome);

        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/serie/{serie}")
    @Operation(summary = "Get alunos by serie", description = "Retrieve a list of alunos based on its serie")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "[{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }, {\\\"id\\\": 2, \\\"name\\\": \\\"John Edgar\\\", \\\"dataNascimento\\\": \\\"2020-02-25\\\", \\\"endereco\\\": [{ \\\"id\\\": 2, \\\"tipo\\\": \\\"Residencial\\\", \\\"rua\\\": \\\"Rua dos calçados\\\", \\\"numero\\\": \\\"123\\\", \\\"cep\\\": \\\"82222-585\\\", \\\"complemento\\\": \\\"casa 1\\\" }], \\\"serie\\\": \\\"G1\\\", \\\"segmento\\\": \\\"Infantil\\\", \\\"nomePai\\\": \\\"São João\\\", \\\"nomeMae\\\": \\\"Alice Pires\\\" }]")
            )            
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Treated Exception",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<List<Aluno>> findBySerie(@PathVariable String serie) throws CustomException {
        try {
            List<Aluno> alunos = alunoService.findBySerie(serie);
    
            return ResponseEntity.ok(alunos);
        }
        catch (CustomException ex) {
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Get all alunos", description = "Retrieve a list of all registered alunos")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "[{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }, {\\\"id\\\": 2, \\\"name\\\": \\\"John Edgar\\\", \\\"dataNascimento\\\": \\\"2020-02-25\\\", \\\"endereco\\\": [{ \\\"id\\\": 2, \\\"tipo\\\": \\\"Residencial\\\", \\\"rua\\\": \\\"Rua dos calçados\\\", \\\"numero\\\": \\\"123\\\", \\\"cep\\\": \\\"82222-585\\\", \\\"complemento\\\": \\\"casa 1\\\" }], \\\"serie\\\": \\\"G1\\\", \\\"segmento\\\": \\\"Infantil\\\", \\\"nomePai\\\": \\\"São João\\\", \\\"nomeMae\\\": \\\"Alice Pires\\\" }]")
            )            
        )
    })
    public ResponseEntity<List<Aluno>> getAll() {
        List<Aluno> alunos = alunoService.getAll();

        return ResponseEntity.ok(alunos);
    }

    @PatchMapping
    public ResponseEntity<Aluno> update(@RequestBody Aluno alunoToUpdate) {
        Aluno alunoUpdated = alunoService.update(alunoToUpdate.getId(), alunoToUpdate.getNome(), alunoToUpdate.getDataNascimento(), alunoToUpdate.getSerie(), alunoToUpdate.getSegmento(), alunoToUpdate.getNomeMae(), alunoToUpdate.getNomePai());

        URI location = generateUriById(alunoUpdated);

        return ResponseEntity.created(location).body(alunoUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a aluno", description = "Delete an existing aluno based on its ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Aluno.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"John Doe\", \"dataNascimento\": \"2020-02-25\", \"endereco\": [{ \"id\": 1, \"tipo\": \"Residencial\", \"rua\": \"Rua dos calçados\", \"numero\": \"123\", \"cep\": \"82222-585\", \"complemento\": \"casa 1\" }], \"serie\": \"G1\", \"segmento\": \"Infantil\", \"nomePai\": \"São João\", \"nomeMae\": \"Alice Pires\" }")
            )            
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Aluno not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Resource Not Found")
            )
        )
    })
    public ResponseEntity<Aluno> delete(@PathVariable Long id) {
        Aluno alunoDeleted = alunoService.delete(id);

        return ResponseEntity.ok(alunoDeleted);
    }

    private URI generateUriById(Aluno aluno) {
        
        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(aluno.getId())
            .toUri();
            
        return location;
    }

}

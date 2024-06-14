package com.escola.domain.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTestIntegrated {

    @Autowired
    private MockMvc mockMvc;

    private static Boolean consulta = false;
 
    private List<Aluno> listAlunoExistente = generateListAluno();

    @BeforeEach
    public void setUp() {

        if (!consulta) {
            try {
                mockMvc.perform(
                    MockMvcRequestBuilders
                    .post("/aluno")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(listAlunoExistente.get(0))));
        
                mockMvc.perform(
                    MockMvcRequestBuilders
                    .post("/aluno")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(listAlunoExistente.get(1))));
            }
            catch (Exception e) {};
        }

        consulta = true;
    }

    @Test
    public void createAlunoTest() throws Exception {
        // Arrange
        Aluno aluno = listAlunoExistente.get(2);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/aluno")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(aluno)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(aluno.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(aluno.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(aluno.getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].id").value(aluno.getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].tipo").value(aluno.getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].rua").value(aluno.getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].numero").value(aluno.getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].cep").value(aluno.getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].complemento").value(aluno.getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].id").value(aluno.getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].tipo").value(aluno.getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].rua").value(aluno.getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].numero").value(aluno.getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].cep").value(aluno.getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].complemento").value(aluno.getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serie").value(aluno.getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.segmento").value(aluno.getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePai").value(aluno.getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeMae").value(aluno.getNomeMae()));

    }

    @Test
    public void createReturn400Test() throws Exception {
        //Arrange
        Aluno entradaCustom = generateAluno();
        entradaCustom.setDataNascimento("2011-03-18");

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/aluno")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(entradaCustom)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("A idade do aluno não está dentro da faixa etária permitida para o segmento Infantil."));

    }

    @Test
    public void findByIdReturn200Test() throws Exception {
        //Arrange
        Long id = listAlunoExistente.get(0).getId();

        Aluno aluno = listAlunoExistente.get(0);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/" + id))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(aluno.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(aluno.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(aluno.getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].id").value(aluno.getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].tipo").value(aluno.getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].rua").value(aluno.getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].numero").value(aluno.getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].cep").value(aluno.getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].complemento").value(aluno.getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].id").value(aluno.getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].tipo").value(aluno.getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].rua").value(aluno.getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].numero").value(aluno.getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].cep").value(aluno.getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].complemento").value(aluno.getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serie").value(aluno.getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.segmento").value(aluno.getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePai").value(aluno.getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeMae").value(aluno.getNomeMae()));

    }

    @Test
    public void findByIdReturn404Test() throws Exception {
        //Arrange
        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Resource Not Found"));

    }

    @Test
    public void findByNameReturn200Test() throws Exception {
        //Arrange
        String nome = listAlunoExistente.get(0).getNome();

        Aluno aluno = listAlunoExistente.get(0);

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/nome/" + nome))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(aluno.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(aluno.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(aluno.getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].id").value(aluno.getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].tipo").value(aluno.getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].rua").value(aluno.getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].numero").value(aluno.getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].cep").value(aluno.getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].complemento").value(aluno.getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].id").value(aluno.getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].tipo").value(aluno.getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].rua").value(aluno.getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].numero").value(aluno.getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].cep").value(aluno.getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].complemento").value(aluno.getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serie").value(aluno.getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.segmento").value(aluno.getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePai").value(aluno.getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeMae").value(aluno.getNomeMae()));

    }

    @Test
    public void findByNameReturn404Test() throws Exception {
        //Arrange
        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/nome/string"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Resource Not Found"));

    }

    @Test
    public void findBySerieReturn200Test() throws Exception {
        //Arrange
        String serie = listAlunoExistente.get(0).getSerie();

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/serie/" + serie))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(listAlunoExistente.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(listAlunoExistente.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataNascimento").value(listAlunoExistente.get(0).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].id").value(listAlunoExistente.get(0).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].tipo").value(listAlunoExistente.get(0).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].rua").value(listAlunoExistente.get(0).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].numero").value(listAlunoExistente.get(0).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].cep").value(listAlunoExistente.get(0).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].complemento").value(listAlunoExistente.get(0).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].id").value(listAlunoExistente.get(0).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].tipo").value(listAlunoExistente.get(0).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].rua").value(listAlunoExistente.get(0).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].numero").value(listAlunoExistente.get(0).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].cep").value(listAlunoExistente.get(0).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].complemento").value(listAlunoExistente.get(0).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serie").value(listAlunoExistente.get(0).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].segmento").value(listAlunoExistente.get(0).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomePai").value(listAlunoExistente.get(0).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeMae").value(listAlunoExistente.get(0).getNomeMae()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(listAlunoExistente.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value(listAlunoExistente.get(1).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataNascimento").value(listAlunoExistente.get(1).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].id").value(listAlunoExistente.get(1).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].tipo").value(listAlunoExistente.get(1).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].rua").value(listAlunoExistente.get(1).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].numero").value(listAlunoExistente.get(1).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].cep").value(listAlunoExistente.get(1).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].complemento").value(listAlunoExistente.get(1).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].id").value(listAlunoExistente.get(1).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].tipo").value(listAlunoExistente.get(1).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].rua").value(listAlunoExistente.get(1).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].numero").value(listAlunoExistente.get(1).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].cep").value(listAlunoExistente.get(1).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].complemento").value(listAlunoExistente.get(1).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serie").value(listAlunoExistente.get(1).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].segmento").value(listAlunoExistente.get(1).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomePai").value(listAlunoExistente.get(1).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeMae").value(listAlunoExistente.get(1).getNomeMae()));
            
    }

    @Test
    public void findBySerieReturn400Test() throws Exception {
        //Arrange
        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno/serie/string"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Série string não identificada"));
    }

    @Test
    public void getAllReturn200Test() throws Exception {
        //Arrange
        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .get("/aluno"))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(listAlunoExistente.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(listAlunoExistente.get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataNascimento").value(listAlunoExistente.get(0).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].id").value(listAlunoExistente.get(0).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].tipo").value(listAlunoExistente.get(0).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].rua").value(listAlunoExistente.get(0).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].numero").value(listAlunoExistente.get(0).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].cep").value(listAlunoExistente.get(0).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[0].complemento").value(listAlunoExistente.get(0).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].id").value(listAlunoExistente.get(0).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].tipo").value(listAlunoExistente.get(0).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].rua").value(listAlunoExistente.get(0).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].numero").value(listAlunoExistente.get(0).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].cep").value(listAlunoExistente.get(0).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endereco[1].complemento").value(listAlunoExistente.get(0).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serie").value(listAlunoExistente.get(0).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].segmento").value(listAlunoExistente.get(0).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomePai").value(listAlunoExistente.get(0).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeMae").value(listAlunoExistente.get(0).getNomeMae()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(listAlunoExistente.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value(listAlunoExistente.get(1).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dataNascimento").value(listAlunoExistente.get(1).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].id").value(listAlunoExistente.get(1).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].tipo").value(listAlunoExistente.get(1).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].rua").value(listAlunoExistente.get(1).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].numero").value(listAlunoExistente.get(1).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].cep").value(listAlunoExistente.get(1).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[0].complemento").value(listAlunoExistente.get(1).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].id").value(listAlunoExistente.get(1).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].tipo").value(listAlunoExistente.get(1).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].rua").value(listAlunoExistente.get(1).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].numero").value(listAlunoExistente.get(1).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].cep").value(listAlunoExistente.get(1).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endereco[1].complemento").value(listAlunoExistente.get(1).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serie").value(listAlunoExistente.get(1).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].segmento").value(listAlunoExistente.get(1).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomePai").value(listAlunoExistente.get(1).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeMae").value(listAlunoExistente.get(1).getNomeMae()));
            
    }

    @Test
    public void deleteReturn200Test() throws Exception {
        //Arrange
        Long id = listAlunoExistente.get(2).getId();

        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .delete("/aluno/" + id))
            //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(listAlunoExistente.get(2).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(listAlunoExistente.get(2).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(listAlunoExistente.get(2).getDataNascimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].id").value(listAlunoExistente.get(2).getEndereco().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].tipo").value(listAlunoExistente.get(2).getEndereco().get(0).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].rua").value(listAlunoExistente.get(2).getEndereco().get(0).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].numero").value(listAlunoExistente.get(2).getEndereco().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].cep").value(listAlunoExistente.get(2).getEndereco().get(0).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[0].complemento").value(listAlunoExistente.get(2).getEndereco().get(0).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].id").value(listAlunoExistente.get(2).getEndereco().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].tipo").value(listAlunoExistente.get(2).getEndereco().get(1).getTipo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].rua").value(listAlunoExistente.get(2).getEndereco().get(1).getRua()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].numero").value(listAlunoExistente.get(2).getEndereco().get(1).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].cep").value(listAlunoExistente.get(2).getEndereco().get(1).getCep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco[1].complemento").value(listAlunoExistente.get(2).getEndereco().get(1).getComplemento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serie").value(listAlunoExistente.get(2).getSerie()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.segmento").value(listAlunoExistente.get(2).getSegmento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePai").value(listAlunoExistente.get(2).getNomePai()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeMae").value(listAlunoExistente.get(2).getNomeMae()));
            
    }

    @Test
    public void deleteReturn404Test() throws Exception {
        //Arrange
        // Act
        // Assert
        mockMvc.perform(
            MockMvcRequestBuilders
            .delete("/aluno/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Resource Not Found"));
    }

    /*
    TODO: Implementar uma factory ou algo semelhante para
          fornecer esse método para mais classes
    */

    private String asJsonString(Aluno aluno) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String alunoJson = objectMapper.writeValueAsString(aluno);

        return alunoJson;
    }

    private List<Aluno> generateListAluno() {
        Aluno aluno1 = generateAluno();
        Aluno aluno2 = generateAluno();
        Aluno aluno3 = generateAluno(); // Modifiquei
        Aluno aluno4 = generateAluno(); // Modifiquei

        aluno2.setId(2L);
        aluno2.setNome("Aluno2");
        aluno2.getEndereco().get(0).setId(3L);
        aluno2.getEndereco().get(1).setId(4L);

        aluno3.setId(3L); // Modifiquei
        aluno3.setNome("Aluno3"); // Modifiquei
        aluno3.getEndereco().get(0).setId(5L); // Modifiquei
        aluno3.getEndereco().get(1).setId(6L); // Modifiquei

        aluno4.setId(4L); // Modifiquei
        aluno4.setNome("Aluno4"); // Modifiquei
        aluno4.getEndereco().get(0).setId(7L); // Modifiquei
        aluno4.getEndereco().get(1).setId(8L); // Modifiquei

        List<Aluno> listAluno = new ArrayList<>();

        listAluno.add(aluno1);
        listAluno.add(aluno2);
        listAluno.add(aluno3); // Modifiquei
        listAluno.add(aluno4); // Modifiquei

        return listAluno;
    }

    private Aluno generateAluno() {
        Aluno aluno = new Aluno();
        List<Endereco> listEndereco = generateListEndereco();

        aluno.setId(1L);
        aluno.setNome("Aluno1");
        aluno.setDataNascimento("2020-03-18"); // Modifiquei
        aluno.setEndereco(listEndereco);
        aluno.setSerie("G1");
        aluno.setSegmento("Infantil"); // Modifiquei
        aluno.setNomePai("Pai do Aluno1");
        aluno.setNomeMae("Mãe do Aluno1");

        return aluno;
    }

    private List<Endereco> generateListEndereco() {
        List<Endereco> listEndereco = new ArrayList<>();
        Endereco endereco1 = new Endereco();
        Endereco endereco2 = new Endereco();

        endereco1.setId(1L);
        endereco1.setTipo("residencial");
        endereco1.setRua("Rua dos calçados");
        endereco1.setNumero("123");
        endereco1.setCep("82222-585");
        endereco1.setComplemento("casa 1");

        endereco2.setId(2L);
        endereco2.setTipo("cobrança");
        endereco2.setRua("Rua dos pés");
        endereco2.setNumero("456");
        endereco2.setCep("82222-585");
        endereco2.setComplemento("casa 3");

        listEndereco.add(endereco1);
        listEndereco.add(endereco2);

        return listEndereco;
    }    
}

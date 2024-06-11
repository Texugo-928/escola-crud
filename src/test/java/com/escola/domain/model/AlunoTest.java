package com.escola.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AlunoTest {

    @Test
    public void getterSetterTest() {
        Aluno aluno = generateAluno();
        List<Endereco> listEndereco = generateListEndereco();

        assertEquals(1L, aluno.getId());
        assertEquals("Aluno1", aluno.getNome());
        assertEquals("2011-03-18", aluno.getDataNascimento());
        assertEquals(2, aluno.getEndereco().size());        
        assertEquals(listEndereco.get(0).getId(), aluno.getEndereco().get(0).getId());
        assertEquals(listEndereco.get(0).getTipo(), aluno.getEndereco().get(0).getTipo());
        assertEquals(listEndereco.get(0).getRua(), aluno.getEndereco().get(0).getRua());
        assertEquals(listEndereco.get(0).getNumero(), aluno.getEndereco().get(0).getNumero());
        assertEquals(listEndereco.get(0).getCep(), aluno.getEndereco().get(0).getCep());
        assertEquals(listEndereco.get(0).getComplemento(), aluno.getEndereco().get(0).getComplemento());
        assertEquals(listEndereco.get(1).getId(), aluno.getEndereco().get(1).getId());
        assertEquals(listEndereco.get(1).getTipo(), aluno.getEndereco().get(1).getTipo());
        assertEquals(listEndereco.get(1).getRua(), aluno.getEndereco().get(1).getRua());
        assertEquals(listEndereco.get(1).getNumero(), aluno.getEndereco().get(1).getNumero());
        assertEquals(listEndereco.get(1).getCep(), aluno.getEndereco().get(1).getCep());
        assertEquals(listEndereco.get(1).getComplemento(), aluno.getEndereco().get(1).getComplemento());
        assertEquals("G1", aluno.getSerie());
        assertEquals("infantil", aluno.getSegmento());
        assertEquals("Pai do Aluno1", aluno.getNomePai());
        assertEquals("Mãe do Aluno1", aluno.getNomeMae());
    }

    @Test
    public void instanciationTest() {
        Aluno aluno = new Aluno();

        assertNotNull(aluno);

        assertNull(aluno.getId());
        assertNull(aluno.getNome());
        assertNull(aluno.getDataNascimento());
        assertNull(aluno.getEndereco());
        assertNull(aluno.getSerie());
        assertNull(aluno.getSegmento());
        assertNull(aluno.getNomePai());
        assertNull(aluno.getNomeMae());
    }

    @Test
    public void relationalEnderecoTest() {
        Aluno aluno = new Aluno();
        List<Endereco> listEndereco = new ArrayList<>();
        Endereco endereco = new Endereco();

        aluno.setEndereco(listEndereco);
        aluno.getEndereco().add(endereco);

        assertTrue(aluno.getEndereco().contains(endereco));

        aluno.getEndereco().remove(endereco);

        assertFalse(aluno.getEndereco().contains(endereco));
    }

    @Test
    public void serializationTest() throws Exception {
        String alunoJson = generateAlunoSerialization();
        String alunoJsonExpected = generateAlunoJson();

        assertEquals(alunoJson, alunoJsonExpected);
    }

    @Test
    public void deserializationTest() throws Exception {
        Aluno aluno = generateAluno();
        Aluno alunoDeserializado = generateAlunoDeserialization();

        List<Endereco> listAluno = aluno.getEndereco();
        List<Endereco> listAlunoDeserializado = alunoDeserializado.getEndereco();

        assertEquals(aluno.getNome(), alunoDeserializado.getNome());
        assertEquals(aluno.getDataNascimento(), alunoDeserializado.getDataNascimento());
        assertEquals(listAluno.size(), alunoDeserializado.getEndereco().size());        
        assertEquals(listAluno.get(0).getId(), listAlunoDeserializado.get(0).getId());
        assertEquals(listAluno.get(0).getTipo(), listAlunoDeserializado.get(0).getTipo());
        assertEquals(listAluno.get(0).getRua(), listAlunoDeserializado.get(0).getRua());
        assertEquals(listAluno.get(0).getNumero(), listAlunoDeserializado.get(0).getNumero());
        assertEquals(listAluno.get(0).getCep(), listAlunoDeserializado.get(0).getCep());
        assertEquals(listAluno.get(0).getComplemento(), listAlunoDeserializado.get(0).getComplemento());
        assertEquals(listAluno.get(1).getId(), listAlunoDeserializado.get(1).getId());
        assertEquals(listAluno.get(1).getTipo(), listAlunoDeserializado.get(1).getTipo());
        assertEquals(listAluno.get(1).getRua(), listAlunoDeserializado.get(1).getRua());
        assertEquals(listAluno.get(1).getNumero(), listAlunoDeserializado.get(1).getNumero());
        assertEquals(listAluno.get(1).getCep(), listAlunoDeserializado.get(1).getCep());
        assertEquals(listAluno.get(1).getComplemento(), listAlunoDeserializado.get(1).getComplemento());
        assertEquals(aluno.getSerie(), alunoDeserializado.getSerie());
        assertEquals(aluno.getSegmento(), alunoDeserializado.getSegmento());
        assertEquals(aluno.getNomePai(), alunoDeserializado.getNomePai());
        assertEquals(aluno.getNomeMae(), alunoDeserializado.getNomeMae());
    }

    private Aluno generateAluno() {
        Aluno aluno = new Aluno();
        List<Endereco> listEndereco = generateListEndereco();

        aluno.setId(1L);
        aluno.setNome("Aluno1");
        aluno.setDataNascimento("2011-03-18");
        aluno.setEndereco(listEndereco);
        aluno.setSerie("G1");
        aluno.setSegmento("infantil");
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

    private String generateAlunoSerialization() throws Exception {
        Aluno aluno = generateAluno();

        ObjectMapper objectMapper = new ObjectMapper();
        String alunoJson = objectMapper.writeValueAsString(aluno);

        return alunoJson;
    }

    private Aluno generateAlunoDeserialization() throws Exception {
        String alunoJson = generateAlunoSerialization();

        ObjectMapper objectMapper = new ObjectMapper();
        Aluno alunoDeserializado = objectMapper.readValue(alunoJson, Aluno.class);

        return alunoDeserializado;
    }

    private String generateAlunoJson() {
        String alunoJson = "{" +
        "\"id\":1," +
        "\"nome\":\"Aluno1\"," +
        "\"dataNascimento\":\"2011-03-18\"," +
        "\"endereco\":[" +
        "{" +
        "\"id\":1," +
        "\"tipo\":\"residencial\"," +
        "\"rua\":\"Rua dos calçados\"," +
        "\"numero\":\"123\"," +
        "\"cep\":\"82222-585\"," +
        "\"complemento\":\"casa 1\"" +
        "}," +
        "{" +
        "\"id\":2," +
        "\"tipo\":\"cobrança\"," +
        "\"rua\":\"Rua dos pés\"," +
        "\"numero\":\"456\"," +
        "\"cep\":\"82222-585\"," +
        "\"complemento\":\"casa 3\"" +
        "}" +
        "]," +
        "\"serie\":\"G1\"," +
        "\"segmento\":\"infantil\"," +
        "\"nomePai\":\"Pai do Aluno1\"," +
        "\"nomeMae\":\"Mãe do Aluno1\"" +
        "}";

        return alunoJson;
    }

}

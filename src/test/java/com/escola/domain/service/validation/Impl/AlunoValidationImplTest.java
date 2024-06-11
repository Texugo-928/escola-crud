package com.escola.domain.service.validation.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;
import com.escola.domain.service.validation.AlunoValidation;

public class AlunoValidationImplTest {

    private AlunoValidation alunoValidation;
    private LocalDate hoje = LocalDate.now();

    @BeforeEach
    public void setUp() {
        alunoValidation = new AlunoValidationImpl();
    }

    @Test
    public void invalidAlunoThrowsCustomExceptionTest() {
        Aluno aluno = new Aluno();
        assertThrows(CustomException.class, () -> alunoValidation.validateAluno(aluno));
    }

    @Test
    public void validateExceptionMessageFromAlunoNomeNullTest() {
        Aluno aluno = new Aluno();

        try {
            alunoValidation.validateAluno(aluno);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("O atributo nome não pode ser nulo ou vazio", e.getMessage());
        }
    }

    @Test
    public void validateExceptionMessageFromAlunoDataNascimentoNullTest() {
        Aluno aluno = new Aluno();

        aluno.setNome("string");

        try {
            alunoValidation.validateAluno(aluno);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("O atributo dataNascimento não pode ser nulo ou vazio", e.getMessage());
        }
    }

    @Test
    public void validateExceptionMessageFromAlunoSerieNullTest() {
        Aluno aluno = new Aluno();

        aluno.setNome("string");
        aluno.setDataNascimento("string");

        try {
            alunoValidation.validateAluno(aluno);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("O atributo serie não pode ser nulo ou vazio", e.getMessage());
        }
    }

    @Test
    public void validateExceptionMessageFromAlunoSegmentoNullTest() {
        Aluno aluno = new Aluno();
        String string = "string";

        aluno.setNome(string);
        aluno.setDataNascimento(string);
        aluno.setSerie(string);

        try {
            alunoValidation.validateAluno(aluno);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("O atributo segmento não pode ser nulo ou vazio", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidSerie")
    public void validateSerieTest(String key, String expected) {
        try {
            String actual = alunoValidation.validateSerie(key);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidSegmento")
    public void validateSegmentoTest(String key, String expected) {
        try {
            String actual = alunoValidation.validateSegmento(key);

            assertEquals(expected, actual);
            assertEquals("Anos iniciais", alunoValidation.validateSegmento("aNoSiNiciAis"));
        } catch (Exception e) {
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @Test
    public void validateDataNascimentoTest() {
        try {
            assertEquals(LocalDate.of(2000, 1, 1), alunoValidation.validateDataNascimento("2000-01-01"));
            assertEquals(LocalDate.of(1995, 12, 31), alunoValidation.validateDataNascimento("1995-12-31"));
        } catch (Exception e) {
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidSerieAndSegmento")
    public void validateCombinationSerieSegmentoTest(String serie, String segmento) {
        try {
            alunoValidation.validateCombinationSerieSegmento(serie, segmento);
        } catch (Exception e) {
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidSegmentoAndIdade")
    public void validateCombinationSegmentoIdadeTest(String segmento, LocalDate dataNascimento) throws Exception {
        alunoValidation.validateCombinationSegmentoIdade(segmento, dataNascimento);
    }

    @Test
    public void invalidSerieThrowsCustomExceptionTest() {
        assertThrows(CustomException.class, () -> alunoValidation.validateSerie("invalida"));
    }

    @Test
    public void invalidSegmentoThrowsCustomExceptionTest() {
        assertThrows(CustomException.class, () -> alunoValidation.validateSegmento("invalida"));
    }

    @Test
    public void invalidDataNascimentoThrowsCustomExceptionTest() {
        assertThrows(CustomException.class, () -> alunoValidation.validateDataNascimento("invalida"));
    }

    @Test
    public void invalidCombinationSerieSegmentoThrowsCustomExceptionTest() {
        assertThrows(CustomException.class, () -> alunoValidation.validateCombinationSerieSegmento("G1", "Anos iniciais"));
    }

    @Test
    public void invalidCombinationSegmentoIdadeThrowsCustomExceptionTest() {
        assertThrows(CustomException.class, () -> alunoValidation.validateCombinationSegmentoIdade("Anos iniciais", hoje.minusYears(4)));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidStringEntries")
    public void validateExceptionMessageFromInvalidSerieTest(String entrada) {
        try {
            alunoValidation.validateSerie(entrada);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("Série " + entrada + " não identificada", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidStringEntries")
    public void validateExceptionMessageFromInvalidSegmentoTest(String entrada) {
        try {
            alunoValidation.validateSegmento(entrada);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("Segmento " + entrada + " não identificado", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidStringEntries")
    public void validateExceptionMessageFromInvalidDataNascimentoTest(String entrada) {
        try {
            alunoValidation.validateDataNascimento(entrada);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("DateTimeParseException: A data " + entrada + " deve ser data válida (yyyy-MM-dd)", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSerieAndSegmento")
    public void validateExceptionMessageFromInvalidCombinationSerieSegmentoTest(String serie, String segmento) {
        try {
            alunoValidation.validateCombinationSerieSegmento(serie, segmento);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("Combinação de série " + serie + " e segmento " + segmento + " não encontrada.", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSegmentoIdade")
    public void validateExceptionMessageFromInvalidCombinationSegmentoIdadeTest(String segmento, LocalDate idade) {
        try {
            alunoValidation.validateCombinationSegmentoIdade(segmento, idade);
        } catch (CustomException e) {
            //System.out.println("Mensagem da Exceção: " + e.getMessage());
            assertEquals("A idade do aluno não está dentro da faixa etária permitida para o segmento " + segmento + ".", e.getMessage());
        }
    }

    private static Stream<String> provideInvalidStringEntries() {
        return Stream.of(
                "invalida",
                "1994-23-05",
                "23-05-1994",
                "1994/02/25"
        );
    }

    private static Stream<Arguments> provideInvalidSegmentoIdade() {
        LocalDate hoje = LocalDate.now();
        LocalDate data = hoje.minusYears(4);

        return Stream.of(
            Arguments.of("Anos iniciais", data),
            Arguments.of("Anos finais", data),
            Arguments.of("Ensino Médio", data)
        );
    }

    private static Stream<Arguments> provideInvalidSerieAndSegmento() {
        return Stream.of(
                Arguments.of("1°", "Infantil"),
                Arguments.of("G1", "Anos iniciais"),
                Arguments.of("2° EM", "Anos finais"),
                Arguments.of("6°", "Ensino Médio")
        );
    }

    private static Stream<Arguments> provideValidSerie() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlunoValidationImpl alunoValidation = new AlunoValidationImpl();

        // Obtém o método privado usando reflexão
        Method method = AlunoValidationImpl.class.getDeclaredMethod("mapearSerie");
        method.setAccessible(true); // Permite acessar o método privado
        
        // Invoca o método para obter o mapa
        @SuppressWarnings("unchecked") // Suprimir o aviso de "Type safety: Unchecked cast from Object to Map<String,String>"
        Map<String, String> result = (Map<String, String>) method.invoke(alunoValidation);

        // Converte o mapa em uma stream de argumentos
        return result.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));

    }

    private static Stream<Arguments> provideValidSegmento() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlunoValidationImpl alunoValidation = new AlunoValidationImpl();

        // Obtém o método privado usando reflexão
        Method method = AlunoValidationImpl.class.getDeclaredMethod("mapearSegmento");
        method.setAccessible(true); // Permite acessar o método privado
        
        // Invoca o método para obter o mapa
        @SuppressWarnings("unchecked") // Suprimir o aviso de "Type safety: Unchecked cast from Object to Map<String,String>"
        Map<String, String> result = (Map<String, String>) method.invoke(alunoValidation);

        // Converte o mapa em uma stream de argumentos
        return result.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));

    }

    private static Stream<Arguments> provideValidSegmentoAndIdade() {
        LocalDate data = LocalDate.now();

        return Stream.of(
                Arguments.of("Infantil", data.minusYears(4)),
                Arguments.of("Anos iniciais", data.minusYears(8)),
                Arguments.of("Anos finais", data.minusYears(13)),
                Arguments.of("Ensino Médio", data.minusYears(16)),

                // Teste de Limites (Bordas)
                Arguments.of("Infantil", data.minusYears(3)),
                Arguments.of("Infantil", data.minusYears(5)),
                Arguments.of("Anos iniciais", data.minusYears(6)),
                Arguments.of("Anos iniciais", data.minusYears(10)),
                Arguments.of("Anos finais", data.minusYears(11)),
                Arguments.of("Anos finais", data.minusYears(14)),
                Arguments.of("Ensino Médio", data.minusYears(15)),
                Arguments.of("Ensino Médio", data.minusYears(17))
        );
    }

    private static Stream<Arguments> provideValidSerieAndSegmento() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlunoValidationImpl alunoValidation = new AlunoValidationImpl();

        // Obtém o método privado usando reflexão
        Method method = AlunoValidationImpl.class.getDeclaredMethod("mapearSeriePorSegmento");
        method.setAccessible(true); // Permite acessar o método privado
        
        // Invoca o método para obter o mapa
        @SuppressWarnings("unchecked") // Suprimir o aviso de "Type safety: Unchecked cast from Object to Map<String,String>"
        Map<String, String> result = (Map<String, String>) method.invoke(alunoValidation);

        // Converte o mapa em uma stream de argumentos
        return result.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));

    }

}
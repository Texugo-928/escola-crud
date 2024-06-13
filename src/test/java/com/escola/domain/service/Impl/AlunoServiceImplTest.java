package com.escola.domain.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;
import com.escola.domain.repository.AlunoRepository;
import com.escola.domain.service.AlunoService;
import com.escola.domain.service.validation.AlunoValidation;

public class AlunoServiceImplTest {

    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepositoryMock;
    
    @Mock
    private AlunoValidation alunoValidationMock;

    private String serieInvalida = "G20";

    private Aluno alunoExistente = generateAluno();
    private List<Aluno> listAlunoExistente = generateListAluno();
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        alunoService = new AlunoServiceImpl(alunoRepositoryMock);
    }
    
    @Test
    public void instanceOfAlunoRepositoryTest() {
        // Verifica se o mock é uma instância de AlunoRepository
        assertEquals(true, alunoRepositoryMock instanceof AlunoRepository);
    }

    @Test
    public void instanceOfAlunoValidationTest() {
        // Verifica se o mock é uma instância de AlunoValidation
        assertEquals(true, alunoValidationMock instanceof AlunoValidation);
    }

    @Test
    public void createReturnSucess() throws Exception {
        // Arrange
        // Mock do comportamento do validation
        when(alunoValidationMock.validateSerie(alunoExistente.getSerie())).thenReturn(alunoExistente.getSerie());
        when(alunoValidationMock.validateSegmento(alunoExistente.getSegmento())).thenReturn(alunoExistente.getSegmento());
        when(alunoValidationMock.validateDataNascimento(alunoExistente.getDataNascimento())).thenReturn(LocalDate.of(2020, 3, 18));

        // Mock do comportamento do repository
        when(alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(alunoExistente.getNome(), alunoExistente.getDataNascimento(), alunoExistente.getNomeMae(), alunoExistente.getNomePai())).thenReturn(false);
        when(alunoRepositoryMock.save(alunoExistente)).thenReturn(generateAluno());

        // Act
        Aluno aluno = alunoService.create(alunoExistente);

        // Assert
        assertNotEquals(alunoExistente, aluno);
        assertEquals(alunoExistente.getNome(), aluno.getNome());
        assertEquals(alunoExistente.getDataNascimento(), aluno.getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), aluno.getNomeMae());
        assertEquals(alunoExistente.getNomePai(), aluno.getNomePai());
    }

    @Test
    public void createReturnFail() throws Exception {
        // Arrange
        // Mock do comportamento do validation
        when(alunoValidationMock.validateSerie(alunoExistente.getSerie())).thenReturn(alunoExistente.getSerie());
        when(alunoValidationMock.validateSegmento(alunoExistente.getSegmento())).thenReturn(alunoExistente.getSegmento());
        when(alunoValidationMock.validateDataNascimento(alunoExistente.getDataNascimento())).thenReturn(LocalDate.of(2020, 3, 18));

        // Mock do comportamento do repository
        when(alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(alunoExistente.getNome(), alunoExistente.getDataNascimento(), alunoExistente.getNomeMae(), alunoExistente.getNomePai())).thenReturn(true);

        // Act
            // Assert
        assertThrows(CustomException.class, () -> alunoService.create(alunoExistente));
    }

    @Test
    public void deleteReturnSucess() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(alunoExistente.getId())).thenReturn(Optional.of(generateAluno()));       
        
        // Act
        Aluno aluno = alunoService.delete(alunoExistente.getId());

        // Assert
        assertNotEquals(alunoExistente, aluno);
        assertEquals(alunoExistente.getNome(), aluno.getNome());
        assertEquals(alunoExistente.getDataNascimento(), aluno.getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), aluno.getNomeMae());
        assertEquals(alunoExistente.getNomePai(), aluno.getNomePai());
    }

    @Test
    public void deleteReturnFail() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(alunoExistente.getId())).thenReturn(Optional.empty());       
        
        // Act
        // Assert
        assertThrows(NoSuchElementException.class, () -> alunoService.delete(alunoExistente.getId()));
    }

    @Test
    public void findByIdReturnSucess() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(alunoExistente.getId())).thenReturn(Optional.of(generateAluno()));       
        
        // Act
        Aluno aluno = alunoService.findById(alunoExistente.getId());

        // Assert
        assertNotEquals(alunoExistente, aluno);
        assertEquals(alunoExistente.getNome(), aluno.getNome());
        assertEquals(alunoExistente.getDataNascimento(), aluno.getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), aluno.getNomeMae());
        assertEquals(alunoExistente.getNomePai(), aluno.getNomePai());
    }

    @Test
    public void findByIdReturnFail() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(alunoExistente.getId())).thenReturn(Optional.empty());       
        
        // Act
        // Assert
        assertThrows(NoSuchElementException.class, () -> alunoService.findById(alunoExistente.getId()));
    }

    @Test
    public void findByNameReturnSucess() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findFirstByNome(alunoExistente.getNome())).thenReturn(Optional.of(generateAluno()));       
        
        // Act
        Aluno aluno = alunoService.findByName(alunoExistente.getNome());

        // Assert
        assertNotEquals(alunoExistente, aluno);
        assertEquals(alunoExistente.getNome(), aluno.getNome());
        assertEquals(alunoExistente.getDataNascimento(), aluno.getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), aluno.getNomeMae());
        assertEquals(alunoExistente.getNomePai(), aluno.getNomePai());
    }

    @Test
    public void findByNameReturnFail() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findFirstByNome(alunoExistente.getNome())).thenReturn(Optional.empty());       
        
        // Act
        // Assert
        assertThrows(NoSuchElementException.class, () -> alunoService.findByName(alunoExistente.getNome()));
    }

    @Test
    public void findBySerieReturnSucess() throws CustomException {
        // Arrange
        // Mock do comportamento do repository
        when(alunoValidationMock.validateSerie(alunoExistente.getSerie())).thenReturn(alunoExistente.getSerie());
        when(alunoRepositoryMock.findBySerie(alunoExistente.getSerie())).thenReturn(generateListAluno());       
        
        // Act
        List<Aluno> listAlunoEncontrado = alunoService.findBySerie(alunoExistente.getSerie());

        // Assert
        assertEquals(false, listAlunoEncontrado.isEmpty());
        assertEquals(2, listAlunoEncontrado.size());
        assertNotEquals(listAlunoExistente, listAlunoEncontrado);
        assertNotEquals(listAlunoExistente.get(0), listAlunoEncontrado.get(0));
        assertNotEquals(listAlunoExistente.get(1), listAlunoEncontrado.get(1));
        assertEquals(listAlunoExistente.get(0).getNome(), listAlunoEncontrado.get(0).getNome());
        assertEquals(listAlunoExistente.get(0).getDataNascimento(), listAlunoEncontrado.get(0).getDataNascimento());
        assertEquals(listAlunoExistente.get(0).getNomeMae(), listAlunoEncontrado.get(0).getNomeMae());
        assertEquals(listAlunoExistente.get(0).getNomePai(), listAlunoEncontrado.get(0).getNomePai());
        assertEquals(listAlunoExistente.get(1).getNome(), listAlunoEncontrado.get(1).getNome());
        assertEquals(listAlunoExistente.get(1).getDataNascimento(), listAlunoEncontrado.get(1).getDataNascimento());
        assertEquals(listAlunoExistente.get(1).getNomeMae(), listAlunoEncontrado.get(1).getNomeMae());
        assertEquals(listAlunoExistente.get(1).getNomePai(), listAlunoEncontrado.get(1).getNomePai());
    }

    @Test
    public void findBySerieReturnFail() {
        // Arrange
        // Act
        // Assert
        assertThrows(CustomException.class, () -> alunoService.findBySerie(serieInvalida));
    }

    @Test
    public void getAllReturnSucess1() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findAll()).thenReturn(generateListAluno());       
        
        // Act
        List<Aluno> listAlunoEncontrado = alunoService.getAll();

        // Assert
        assertEquals(false, listAlunoEncontrado.isEmpty());
        assertEquals(2, listAlunoEncontrado.size());
        assertNotEquals(listAlunoExistente, listAlunoEncontrado);
        assertNotEquals(listAlunoExistente.get(0), listAlunoEncontrado.get(0));
        assertNotEquals(listAlunoExistente.get(1), listAlunoEncontrado.get(1));
        assertEquals(listAlunoExistente.get(0).getNome(), listAlunoEncontrado.get(0).getNome());
        assertEquals(listAlunoExistente.get(0).getDataNascimento(), listAlunoEncontrado.get(0).getDataNascimento());
        assertEquals(listAlunoExistente.get(0).getNomeMae(), listAlunoEncontrado.get(0).getNomeMae());
        assertEquals(listAlunoExistente.get(0).getNomePai(), listAlunoEncontrado.get(0).getNomePai());
        assertEquals(listAlunoExistente.get(1).getNome(), listAlunoEncontrado.get(1).getNome());
        assertEquals(listAlunoExistente.get(1).getDataNascimento(), listAlunoEncontrado.get(1).getDataNascimento());
        assertEquals(listAlunoExistente.get(1).getNomeMae(), listAlunoEncontrado.get(1).getNomeMae());
        assertEquals(listAlunoExistente.get(1).getNomePai(), listAlunoEncontrado.get(1).getNomePai());
    }

    @Test
    public void getAllReturnSucess2() {
        // Arrange
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findAll()).thenReturn(new ArrayList<>());       
        
        // Act
        List<Aluno> listAlunoEncontrado = alunoService.getAll();

        // Assert
        assertEquals(true, listAlunoEncontrado.isEmpty());
        assertEquals(0, listAlunoEncontrado.size());
    }

    // update

    /*
    TODO: Implementar uma factory ou algo semelhante para
          fornecer esse método para mais classes
    */
    private List<Aluno> generateListAluno() {
        Aluno aluno1 = generateAluno();
        Aluno aluno2 = generateAluno();

        aluno2.setId(2L);
        aluno2.setNome("Aluno2");
        aluno2.getEndereco().get(0).setId(3L);
        aluno2.getEndereco().get(1).setId(4L);

        List<Aluno> listAluno = new ArrayList<>();

        listAluno.add(aluno1);
        listAluno.add(aluno2);

        return listAluno;
    }

    private Aluno generateAluno() {
        Aluno aluno = new Aluno();
        List<Endereco> listEndereco = generateListEndereco();

        aluno.setId(1L);
        aluno.setNome("Aluno1");
        aluno.setDataNascimento("2020-03-18"); // modifiquei
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

}

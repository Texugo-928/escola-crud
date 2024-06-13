package com.escola.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;

// Classe realiza testes sem a necessidade de integração com o BD
public class AlunoRepositoryTest {

    @Mock
    private AlunoRepository alunoRepositoryMock;

    private Long id = 1L;
    private String nome = "Aluno1";
    private String serie = "G1";

    private Aluno alunoExistente = generateAluno();
    private List<Aluno> listAlunoExistente = generateListAluno();

    // Inicializa os mocks antes de cada teste
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void instanceOfAlunoRepositoryTest() {
        // Verifica se o mock é uma instância de AlunoRepository
        assertEquals(true, alunoRepositoryMock instanceof AlunoRepository);
    }

    /*
    Para o cenário (Sem Integração com BD) o teste nos métodos:
        - default <S extends Aluno> S save(S entity);
        - default void deleteById(Long id);
        não faz muito sentido.
    */

    @Test
    public void findAll_ExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findAll()).thenReturn(generateListAluno());

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepositoryMock.findAll();

        // Assert //
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
    public void findAll_NonExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepositoryMock.findAll();

        // Assert //
        assertEquals(true, listAlunoEncontrado.isEmpty());
        assertEquals(0, listAlunoEncontrado.size());
    }   

    @Test
    public void findById_ExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(id)).thenReturn(Optional.of(generateAluno()));

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepositoryMock.findById(id);

        // Assert //
        assertEquals(true, alunoEncontradoOptional.isPresent());
        assertNotEquals(alunoExistente, alunoEncontradoOptional);
        assertEquals(alunoExistente.getNome(), alunoEncontradoOptional.get().getNome());
        assertEquals(alunoExistente.getDataNascimento(), alunoEncontradoOptional.get().getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), alunoEncontradoOptional.get().getNomeMae());
        assertEquals(alunoExistente.getNomePai(), alunoEncontradoOptional.get().getNomePai());
    }

    @Test
    public void findById_NonExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepositoryMock.findById(id);

        // Assert //
        assertEquals(false, alunoEncontradoOptional.isPresent());
        assertThrows(NoSuchElementException.class, () -> alunoRepositoryMock.findById(id).orElseThrow());
    }

    @Test
    public void findFirstByNome_ExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findFirstByNome(nome)).thenReturn(Optional.of(generateAluno()));

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepositoryMock.findFirstByNome(nome);

        // Assert //
        assertEquals(true, alunoEncontradoOptional.isPresent());
        assertNotEquals(alunoExistente, alunoEncontradoOptional);
        assertEquals(alunoExistente.getNome(), alunoEncontradoOptional.get().getNome());
        assertEquals(alunoExistente.getDataNascimento(), alunoEncontradoOptional.get().getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), alunoEncontradoOptional.get().getNomeMae());
        assertEquals(alunoExistente.getNomePai(), alunoEncontradoOptional.get().getNomePai());
    }

    @Test
    public void findFirstByNome_NonExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findFirstByNome(nome)).thenReturn(Optional.empty());

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepositoryMock.findFirstByNome(nome);

        // Assert //
        assertEquals(false, alunoEncontradoOptional.isPresent());
        assertThrows(NoSuchElementException.class, () -> alunoRepositoryMock.findFirstByNome(nome).orElseThrow());
    }    

    @Test
    public void findBySerie_ExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findBySerie(serie)).thenReturn(generateListAluno());

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepositoryMock.findBySerie(serie);

        // Assert //
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
    public void findBySerie_NonExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.findBySerie(serie)).thenReturn(new ArrayList<>());

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepositoryMock.findBySerie(serie);

        // Assert //
        assertEquals(true, listAlunoEncontrado.isEmpty());
        assertEquals(0, listAlunoEncontrado.size());
    }    

    //Para o cenário (Sem Integração com BD) esse teste não faz muito sentido
    @Test
    public void existsByNomeAndDataNascimentoAndNomeMaeAndNomePai_ExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(nome, nome, nome, nome)).thenReturn(true);

        // Act //
        boolean result = alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(nome, nome, nome, nome);

        // Assert //
        assertEquals(true, result);
    }

    //Para o cenário (Sem Integração com BD) esse teste não faz muito sentido
    @Test
    public void existsByNomeAndDataNascimentoAndNomeMaeAndNomePai_NonExistingAlunoTest() {
        // Arrange //
        // Mock do comportamento do repository
        when(alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(nome, nome, nome, nome)).thenReturn(false);

        // Act //
        boolean result = alunoRepositoryMock.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(nome, nome, nome, nome);

        // Assert //
        assertEquals(false, result);
    }    

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

}

package com.escola.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;

// Classe realiza testes integrado com o BD
@DataJpaTest //Metodologia: o Spring Boot configurará automaticamente o ambiente de teste e redefinirá o BD após cada teste.
public class AlunoRepositoryIntegratedTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private static Long contador = -1L; //O Id no BD vai aumentando para cada teste. Essa variável serve para controlar esse aumento.
    private String nome = "Aluno3";
    private String dataNascimento = "2020-02-25";
    private String serie = "G2";
    private String nomeMae = "Mãe do Aluno3";
    private String nomePai = "Pai do Aluno3";

    private Aluno alunoExistente = generateAluno();
    private List<Aluno> listAlunoExistente = generateListAluno();

    @BeforeEach
    public void setUp() {
        // Adicione os dados de exemplo ao banco de dados
        for(int i = 0; i < listAlunoExistente.size(); i++) {
            alunoRepository.save(listAlunoExistente.get(i));
            contador++;
        }
    }

    @Test
    public void deleteByIdTest() {
        // Arrange //
        boolean result = false;

        // Act //
        try {
            alunoRepository.deleteById(alunoExistente.getId());
            result = true;
        }
        catch (Exception e) {}

        // Assert //
        assertEquals(true, result);
    }

    // Não faz muito sentido testar o caso de Id inválido já que o retorno do método é void.

    @Test
    public void findAll_ExistingAlunoTest() {
        // Arrange //

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepository.findAll();

        // Assert //
        assertEquals(false, listAlunoEncontrado.isEmpty());
        assertEquals(2, listAlunoEncontrado.size());
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
        // Remove os dados de exemplo no banco de dados
        alunoRepository.deleteAll();

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepository.findAll();

        // Assert //
        assertEquals(true, listAlunoEncontrado.isEmpty());
        assertEquals(0, listAlunoEncontrado.size());
    }

    @Test
    public void findById_ExistingAlunoTest() {
        // Arrange //

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepository.findById(contador);

        // Assert //
        assertEquals(true, alunoEncontradoOptional.isPresent());
        assertEquals(alunoExistente.getNome(), alunoEncontradoOptional.get().getNome());
        assertEquals(alunoExistente.getDataNascimento(), alunoEncontradoOptional.get().getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), alunoEncontradoOptional.get().getNomeMae());
        assertEquals(alunoExistente.getNomePai(), alunoEncontradoOptional.get().getNomePai());
    }

    @Test
    public void findById_NonExistingAlunoTest() {
        // Arrange //

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepository.findById(contador + 4L);

        // Assert //
        assertEquals(false, alunoEncontradoOptional.isPresent());
        assertThrows(NoSuchElementException.class, () -> alunoRepository.findById(contador + 4L).orElseThrow());
    }

    @Test
    public void findFirstByNome_ExistingAlunoTest() {
        // Arrange //

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepository.findFirstByNome(alunoExistente.getNome());

        // Assert //
        assertEquals(true, alunoEncontradoOptional.isPresent());
        assertEquals(alunoExistente.getNome(), alunoEncontradoOptional.get().getNome());
        assertEquals(alunoExistente.getDataNascimento(), alunoEncontradoOptional.get().getDataNascimento());
        assertEquals(alunoExistente.getNomeMae(), alunoEncontradoOptional.get().getNomeMae());
        assertEquals(alunoExistente.getNomePai(), alunoEncontradoOptional.get().getNomePai());
    }

    @Test
    public void findFirstByNome_NonExistingAlunoTest() {
        // Arrange //

        // Act //
        Optional<Aluno> alunoEncontradoOptional = alunoRepository.findFirstByNome(nome);

        // Assert //
        assertEquals(false, alunoEncontradoOptional.isPresent());
        assertThrows(NoSuchElementException.class, () -> alunoRepository.findFirstByNome(nome).orElseThrow());
    }    

    @Test
    public void findBySerie_ExistingAlunoTest() {
        // Arrange //
 
        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepository.findBySerie(listAlunoExistente.get(0).getSerie());

        // Assert //
        assertEquals(false, listAlunoEncontrado.isEmpty());
        assertEquals(2, listAlunoEncontrado.size());
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

        // Act //
        List<Aluno> listAlunoEncontrado = alunoRepository.findBySerie(serie);

        // Assert //
        assertEquals(true, listAlunoEncontrado.isEmpty());
        assertEquals(0, listAlunoEncontrado.size());
    }   

    @Test
    public void existsByNomeAndDataNascimentoAndNomeMaeAndNomePai_ExistingAlunoTest() {
        // Arrange //

        // Act //
        boolean result = alunoRepository.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(alunoExistente.getNome(), alunoExistente.getDataNascimento(), alunoExistente.getNomeMae(), alunoExistente.getNomePai());

        // Assert //
        assertEquals(true, result);
    }
    
    @Test
    public void existsByNomeAndDataNascimentoAndNomeMaeAndNomePai_NonExistingAlunoTest() {
        // Arrange //

        // Act //
        boolean result = alunoRepository.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(nome, dataNascimento, nomeMae, nomePai);

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

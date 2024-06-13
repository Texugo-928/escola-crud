package com.escola.domain.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;
import com.escola.domain.repository.AlunoRepository;
import com.escola.domain.service.AlunoService;
import com.escola.domain.service.validation.AlunoValidation;
import com.escola.domain.service.validation.Impl.AlunoValidationImpl;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoValidation alunoValidation;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
        this.alunoValidation = new AlunoValidationImpl();
    }

    @Override
    public Aluno create(Aluno alunoToCreate) throws Exception {

        validacoesCreate(alunoToCreate);

        Aluno retorno = alunoRepository.save(alunoToCreate);

        return retorno;
    }

    @Override
    public Aluno delete(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();
        alunoRepository.deleteById(id);

        return aluno;
    }

    @Override
    public Aluno findById(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();

        return aluno;
    }

    @Override
    public Aluno findByName(String nome) {
        Aluno aluno = alunoRepository.findFirstByNome(nome).orElseThrow();

        return aluno;
    }

    @Override
    public List<Aluno> findBySerie(String serie) throws CustomException {

        String serieAjustada = alunoValidation.validateSerie(serie);

        List<Aluno> alunos = alunoRepository.findBySerie(serieAjustada);

        return alunos;
    }

    @Override
    public List<Aluno> getAll() {
        return alunoRepository.findAll();
    }

    // TODO: Precisa implementar corretamente
    @Override
    public Aluno update(Long id, String nome, String dataNascimento, String serie, String segmento, String nomeMae, String nomePai) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow();

        if (!nome.equalsIgnoreCase("")) {
            aluno.setNome(nome);
        }

        if (!dataNascimento.equalsIgnoreCase("")) {
            aluno.setDataNascimento(dataNascimento);
        }

        /* 

        //Teria que ver se estou atualizando algum endereço da lista,
        //ou se estou incluindo um novo endereco.

        if (!endereco.isEmpty()) {
            aluno.setEndereco(null);
        }
        */

        if (!serie.equalsIgnoreCase("")) {
            aluno.setSerie(serie);
        }

        if (!segmento.equalsIgnoreCase("")) {
            aluno.setSegmento(segmento);
        }

        if (!nomeMae.equalsIgnoreCase("")) {
            aluno.setNomeMae(nomeMae);
        }

        if (!nomePai.equalsIgnoreCase("")) {
            aluno.setNomePai(nomePai);
        }

        alunoRepository.save(aluno);

        return aluno;
    }

    private void validacoesCreate(Aluno aluno) throws Exception {
          
        // Validar Aluno
        alunoValidation.validateAluno(aluno);
        String serie = alunoValidation.validateSerie(aluno.getSerie());
        String segmento = alunoValidation.validateSegmento(aluno.getSegmento());
        LocalDate data = alunoValidation.validateDataNascimento(aluno.getDataNascimento());

        // Validar a combinação Série e Segmento
        alunoValidation.validateCombinationSerieSegmento(serie, segmento);

        // Validar a combinação Segmento e Faixa Etária
        alunoValidation.validateCombinationSegmentoIdade(segmento, data);

        // Validar se o Aluno já existe no BD
        validarExistenciaAluno(aluno);

        // Reatribui os valores Pré-Fixados
        aluno.setSerie(serie);
        aluno.setSegmento(segmento);

    }

    private void validarExistenciaAluno(Aluno aluno) throws CustomException {

        String nome = aluno.getNome();
        String dataNascimento = aluno.getDataNascimento();
        String nomeMae = aluno.getNomeMae();
        String nomePai = aluno.getNomePai();
    
        Boolean verificacao = alunoRepository
            .existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(
                nome,
                dataNascimento,
                nomeMae,
                nomePai
            );

        if (verificacao) {
            throw new CustomException("Aluno já foi cadastrado");
        }

    }

}

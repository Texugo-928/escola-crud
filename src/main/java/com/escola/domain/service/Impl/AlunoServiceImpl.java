package com.escola.domain.service.Impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.model.Aluno;
import com.escola.domain.repository.AlunoRepository;
import com.escola.domain.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Aluno create(Aluno alunoToCreate) throws CustomException {
        Boolean verificacao = alunoRepository.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(alunoToCreate.getNome(), alunoToCreate.getDataNascimento(), alunoToCreate.getNomeMae(), alunoToCreate.getNomePai());

        if (verificacao) {
            throw new CustomException("Aluno já foi cadastrado");
        }

        String validacao = validarSegmento(alunoToCreate.getSerie(), alunoToCreate.getSegmento());

        if (!validacao.equalsIgnoreCase("")) {
            throw new CustomException(validacao);
        }

        validacao = validarFaixaEtaria(alunoToCreate.getSegmento(),  alunoToCreate.getDataNascimento());

        if (!validacao.equalsIgnoreCase("")) {
            throw new CustomException(validacao);
        }
        else {
            Aluno retorno = alunoRepository.save(alunoToCreate);
            return retorno;
        }
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
    public List<Aluno> findBySerie(String serie) {
        List<Aluno> alunos = alunoRepository.findBySerie(serie).orElseThrow();

        return alunos;
    }

    @Override
    public List<Aluno> getAll() {
        return alunoRepository.findAll();
    }

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

    private String validarSegmento(String serie, String segmento) {
    
        Map<String, String> SeriePorSegmento = new HashMap<>();
        SeriePorSegmento.put("G1", "Infantil");
        SeriePorSegmento.put("G2", "Infantil");
        SeriePorSegmento.put("G3", "Infantil");
        SeriePorSegmento.put("1°", "Anos iniciais");
        SeriePorSegmento.put("2°", "Anos iniciais");
        SeriePorSegmento.put("3°", "Anos iniciais");
        SeriePorSegmento.put("4°", "Anos iniciais");
        SeriePorSegmento.put("5°", "Anos iniciais");
        SeriePorSegmento.put("6°", "Anos finais");
        SeriePorSegmento.put("7°", "Anos finais");
        SeriePorSegmento.put("8°", "Anos finais");
        SeriePorSegmento.put("9°", "Anos finais");
        SeriePorSegmento.put("1° EM", "Ensino Médio");
        SeriePorSegmento.put("2° EM", "Ensino Médio");
        SeriePorSegmento.put("3° EM", "Ensino Médio");

        if (SeriePorSegmento.get(serie) == null) {
            String msg = "A série " + serie + " informada não foi encontrada.";
            return msg;            
        }

        if (!SeriePorSegmento.get(serie).equals(segmento)) {
            String msg = "Combinação de série " + serie + " e segmento " + segmento + " não encontrada.";
            return msg;
        }

        return "";
    }

    private String validarFaixaEtaria(String segmento, String dataNascimento) throws CustomException {
    
        Map<String, int[]> faixaEtariaPorSegmento = new HashMap<>();
        faixaEtariaPorSegmento.put("Infantil", new int[]{3, 5});
        faixaEtariaPorSegmento.put("Anos iniciais", new int[]{6, 10});
        faixaEtariaPorSegmento.put("Anos finais", new int[]{11, 14});
        faixaEtariaPorSegmento.put("Ensino Médio", new int[]{15, 17});

        int[] faixaEtaria = faixaEtariaPorSegmento.get(segmento);

        try{
            // Formato da string de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // Convertendo a string para LocalDate
            LocalDate data = LocalDate.parse(dataNascimento, formatter);

            LocalDate hoje = LocalDate.now();
            int idade = hoje.getYear() - data.getYear() -
                    ((hoje.getMonthValue() < data.getMonthValue() ||
                            (hoje.getMonthValue() == data.getMonthValue() &&
                                    hoje.getDayOfMonth() < data.getDayOfMonth())) ? 1 : 0);
    
            if (!(faixaEtaria[0] <= idade && idade <= faixaEtaria[1])) {
                String msg1 = "A idade do aluno não está dentro da faixa etária";
                String msg2 = " permitida para o segmento " + segmento + ".";
                return msg1 + msg2;
            }
    
            return "";
        }
        catch (DateTimeParseException ex) {
            throw new CustomException("DateTimeParseException: " + ex.getMessage());
        }

    }

}

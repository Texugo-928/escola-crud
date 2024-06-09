package com.escola.domain.service.Impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.escola.domain.model.Aluno;
import com.escola.domain.model.Endereco;
import com.escola.domain.repository.AlunoRepository;
import com.escola.domain.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Aluno create(Aluno alunoToCreate) {
        Boolean verificacao = alunoRepository.existsByNomeAndDataNascimentoAndNomeMaeAndNomePai(alunoToCreate.getNome(), alunoToCreate.getData_nascimento(), alunoToCreate.getNome_mae(), alunoToCreate.getNome_pai());

        if (verificacao) {
            throw new Exception("Aluno já foi cadastrado");
        }

        String errorMessage = validarFaixaEtaria(alunoToCreate.getSerie(), alunoToCreate.getSegmento(),  alunoToCreate.getData_nascimento());

        if (errorMessage.equalsIgnoreCase("")) {
           Aluno retorno = alunoRepository.save(alunoToCreate);

           return retorno;
        }
        else {
            throw new Exception(errorMessage);
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
            aluno.setData_nascimento(dataNascimento);
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
            aluno.setNome_mae(nomeMae);
        }

        if (!nomePai.equalsIgnoreCase("")) {
            aluno.setNome_pai(nomePai);
        }

        alunoRepository.save(aluno);

        return aluno;
    }

    private String validarFaixaEtaria(String serie, String segmento, String dataNascimento) {
    
        Map<String[], int[]> faixaEtariaPorSerieSegmento = new HashMap<>();
        faixaEtariaPorSerieSegmento.put(new String[]{"Infantil", "G1"}, new int[]{3, 5});
        faixaEtariaPorSerieSegmento.put(new String[]{"Infantil", "G2"}, new int[]{3, 5});
        faixaEtariaPorSerieSegmento.put(new String[]{"Infantil", "G3"}, new int[]{3, 5});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos iniciais", "1°"}, new int[]{6, 10});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos iniciais", "2°"}, new int[]{6, 10});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos iniciais", "3°"}, new int[]{6, 10});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos iniciais", "4°"}, new int[]{6, 10});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos iniciais", "5°"}, new int[]{6, 10});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos finais", "6°"}, new int[]{11, 14});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos finais", "7°"}, new int[]{11, 14});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos finais", "8°"}, new int[]{11, 14});
        faixaEtariaPorSerieSegmento.put(new String[]{"Anos finais", "9°"}, new int[]{11, 14});
        faixaEtariaPorSerieSegmento.put(new String[]{"Ensino Médio", "1° EM"}, new int[]{15, 17});
        faixaEtariaPorSerieSegmento.put(new String[]{"Ensino Médio", "2° EM"}, new int[]{15, 17});
        faixaEtariaPorSerieSegmento.put(new String[]{"Ensino Médio", "3° EM"}, new int[]{15, 17});

        String[] serieSegmento = new String[]{segmento, serie};

        if (!faixaEtariaPorSerieSegmento.containsKey(serieSegmento)) {
            String msg1 = "Combinação de série " + serie + " e segmento " + segmento;
            String msg2 = " não encontrada no mapeamento de faixa etária.";
            return msg1 + msg2;
        }

        int[] faixaEtaria = faixaEtariaPorSerieSegmento.get(serieSegmento);

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
            String msg2 = " permitida para a série " + serie + " e o segmento " + segmento + ".";
            return msg1 + msg2;
        }

        return "";
    }

}

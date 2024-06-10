package com.escola.domain.service.validation.Impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.escola.domain.controller.exception.CustomException;
import com.escola.domain.service.validation.AlunoValidation;

public class AlunoValidationImpl implements AlunoValidation {

    @Override
    public String validateSerie(String serie) throws CustomException {

        String serieLower = serie.toLowerCase();

        Map<String, String> serieMap = mapearSerie();

        if (serieMap.containsKey(serieLower)) {
            return serieMap.get(serieLower);
        }
        else {
            throw new CustomException("Série " + serie + " não identificada");
        }
    
    }

    @Override
    public String validateSegmento(String segmento) throws CustomException {

        String segmentoLower = segmento.toLowerCase();

        Map<String, String> segmentoMap = mapearSegmento();

        if (segmentoMap.containsKey(segmentoLower)) {
            return segmentoMap.get(segmentoLower);
        }
        else {
            throw new CustomException("Segmento " + segmento + " não identificado");
        }

    }

    @Override
    public LocalDate validateDataNascimento(String dataNascimento) throws CustomException {
        try{
            // Formato da string de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // Convertendo a string para LocalDate
            return LocalDate.parse(dataNascimento, formatter);
    
        }
        catch (DateTimeParseException ex) {
            throw new CustomException("DateTimeParseException: A data " + dataNascimento + " deve ser data válida (yyyy-MM-dd)");
        }

    }

    @Override
    public void validateCombinationSerieSegmento(String serie, String segmento) throws CustomException {
    
        Map<String, String> SeriePorSegmento = mapearSeriePorSegmento();

        if (!SeriePorSegmento.get(serie).equals(segmento)) {
            String msg = "Combinação de série " + serie + " e segmento " + segmento + " não encontrada.";
            throw new CustomException(msg);
        }

    }

    @Override
    public void validateCombinationSegmentoIdade(String segmento, LocalDate dataNascimento) throws CustomException {
    
        Map<String, int[]> faixaEtariaPorSegmento = mapearIdadePorSegmento();

        int[] faixaEtaria = faixaEtariaPorSegmento.get(segmento);

        LocalDate hoje = LocalDate.now();

        int idade = hoje.getYear() - dataNascimento.getYear() -
                ((hoje.getMonthValue() < dataNascimento.getMonthValue() ||
                        (hoje.getMonthValue() == dataNascimento.getMonthValue() &&
                                hoje.getDayOfMonth() < dataNascimento.getDayOfMonth())) ? 1 : 0);

        if (!(faixaEtaria[0] <= idade && idade <= faixaEtaria[1])) {
            String msg = "A idade do aluno não está dentro da faixa etária permitida para o segmento " + segmento + ".";
            throw new CustomException(msg);
        }

    }

    private Map<String, String> mapearSerie() {
        Map<String, String> serieMap = new HashMap<>();

        serieMap.put("g1", "G1");
        serieMap.put("g2", "G2");
        serieMap.put("g3", "G3");
        serieMap.put("1", "1°");
        serieMap.put("1°", "1°");
        serieMap.put("2", "2°");
        serieMap.put("2°", "2°");
        serieMap.put("3", "3°");
        serieMap.put("3°", "3°");
        serieMap.put("4", "4°");
        serieMap.put("4°", "4°");
        serieMap.put("5", "5°");
        serieMap.put("5°", "5°");
        serieMap.put("6", "6°");
        serieMap.put("6°", "6°");
        serieMap.put("7", "7°");
        serieMap.put("7°", "7°");
        serieMap.put("8", "8°");
        serieMap.put("8°", "8°");
        serieMap.put("9", "9°");
        serieMap.put("9°", "9°");
        serieMap.put("1em", "1° EM");
        serieMap.put("1 em", "1° EM");
        serieMap.put("1°em", "1° EM");
        serieMap.put("1° em", "1° EM");
        serieMap.put("2em", "2° EM");
        serieMap.put("2 em", "2° EM");
        serieMap.put("2°em", "2° EM");
        serieMap.put("2° em", "2° EM");
        serieMap.put("3em", "3° EM");
        serieMap.put("3 em", "3° EM");
        serieMap.put("3°em", "3° EM");
        serieMap.put("3° em", "3° EM");

        return serieMap;
    }

    private Map<String, String> mapearSegmento() {
        Map<String, String> segmentoMap = new HashMap<>();

        segmentoMap.put("infantil", "Infantil");
        segmentoMap.put("anosiniciais", "Anos iniciais");
        segmentoMap.put("anos iniciais", "Anos iniciais");
        segmentoMap.put("anosfinais", "Anos finais");
        segmentoMap.put("anos finais", "Anos finais");
        segmentoMap.put("ensinomedio", "Ensino Médio");
        segmentoMap.put("ensino medio", "Ensino Médio");
        segmentoMap.put("ensinomédio", "Ensino Médio");
        segmentoMap.put("ensino médio", "Ensino Médio");

        return segmentoMap;
    }

    private Map<String, String> mapearSeriePorSegmento() {
        Map<String, String> seriePorSegmentoMap = new HashMap<>();

        seriePorSegmentoMap.put("G1", "Infantil");
        seriePorSegmentoMap.put("G2", "Infantil");
        seriePorSegmentoMap.put("G3", "Infantil");
        seriePorSegmentoMap.put("1°", "Anos iniciais");
        seriePorSegmentoMap.put("2°", "Anos iniciais");
        seriePorSegmentoMap.put("3°", "Anos iniciais");
        seriePorSegmentoMap.put("4°", "Anos iniciais");
        seriePorSegmentoMap.put("5°", "Anos iniciais");
        seriePorSegmentoMap.put("6°", "Anos finais");
        seriePorSegmentoMap.put("7°", "Anos finais");
        seriePorSegmentoMap.put("8°", "Anos finais");
        seriePorSegmentoMap.put("9°", "Anos finais");
        seriePorSegmentoMap.put("1° EM", "Ensino Médio");
        seriePorSegmentoMap.put("2° EM", "Ensino Médio");
        seriePorSegmentoMap.put("3° EM", "Ensino Médio");

        return seriePorSegmentoMap;
    }

    private Map<String, int[]> mapearIdadePorSegmento() {
        Map<String, int[]> idadePorSegmentoMap = new HashMap<>();

        idadePorSegmentoMap.put("Infantil", new int[]{3, 5});
        idadePorSegmentoMap.put("Anos iniciais", new int[]{6, 10});
        idadePorSegmentoMap.put("Anos finais", new int[]{11, 14});
        idadePorSegmentoMap.put("Ensino Médio", new int[]{15, 17});

        return idadePorSegmentoMap;
    }

}

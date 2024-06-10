package com.escola.domain.service.validation;

import java.time.LocalDate;

import com.escola.domain.controller.exception.CustomException;

public interface AlunoValidation {

        String validateSerie(String serie) throws CustomException;

        String validateSegmento(String segmento) throws CustomException;

        LocalDate validateDataNascimento(String dataNascimento) throws CustomException;

        void validateCombinationSerieSegmento(String serie, String segmento) throws CustomException;

        void validateCombinationSegmentoIdade(String segmento, LocalDate dataNascimento) throws CustomException;

}

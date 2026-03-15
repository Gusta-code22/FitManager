package com.gusta.academia.controller.Dto.treinoDto;

import com.gusta.academia.controller.Dto.exercicioDto.ExercicioResponseDto;

import java.util.List;
import java.util.UUID;

public record TreinoResponseDto(UUID id,
                                String nome,
                                String alunoNome,
                                String professorNome,
                                List<ExercicioResponseDto> exercicios) {
}

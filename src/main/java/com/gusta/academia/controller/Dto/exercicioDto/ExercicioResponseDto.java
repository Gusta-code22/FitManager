package com.gusta.academia.controller.Dto.exercicioDto;

import java.util.UUID;

public record ExercicioResponseDto(UUID id,
                                   String nome,
                                   String grupoMuscular,
                                   String descricao) {
}

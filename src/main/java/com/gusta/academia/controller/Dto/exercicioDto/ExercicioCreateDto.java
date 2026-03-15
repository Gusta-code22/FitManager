package com.gusta.academia.controller.Dto.exercicioDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExercicioCreateDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "o tamanho minímo é de tres digitos")
        String nome,
        @NotBlank(message = "O grupoMuscular é obrigatório")
        String grupoMuscular,
        @NotBlank(message = "A descricao é obrigatório")
        String descricao) {
}

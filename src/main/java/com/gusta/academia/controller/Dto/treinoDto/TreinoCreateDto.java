package com.gusta.academia.controller.Dto.treinoDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record TreinoCreateDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3)
        String nome,
        @NotBlank(message = "O Id do aluno é obrigatório")
        UUID idAluno,
        @NotBlank(message = "O Id do professor é obrigatório")
        UUID idProfessor,
        List<UUID> idExercicios) {
}

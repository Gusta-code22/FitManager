package com.gusta.academia.controller.Dto.professorDto;

import jakarta.validation.constraints.NotBlank;

public record ProfessorCreateDto(
        @NotBlank(message = "Nome do Professor é obrigatorio")
        String nome,
        @NotBlank(message = "Especialidade do Professor é obrigatorio")
        String especialidade

){
}

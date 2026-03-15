package com.gusta.academia.controller.Dto.professorDto;

import java.util.UUID;

public record ProfessorDto(
        UUID id,
        String nome
) {}
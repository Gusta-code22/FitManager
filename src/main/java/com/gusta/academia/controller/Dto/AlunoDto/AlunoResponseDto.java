package com.gusta.academia.controller.Dto.AlunoDto;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoResponseDto(UUID id,
                               String nome,
                               String email,
                               LocalDate dataNascimento
) {
}

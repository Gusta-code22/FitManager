// DTOs
package com.gusta.academia.controller.Dto.AlunoDto;



import com.gusta.academia.controller.Dto.professorDto.ProfessorDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AlunoResponseCompletoDto(
        UUID id,
        String nome,
        String email,
        LocalDate dataNascimento,
        ProfessorDto professor,
        List<TreinoDto> treinos
) {}
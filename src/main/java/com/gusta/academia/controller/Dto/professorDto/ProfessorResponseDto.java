package com.gusta.academia.controller.Dto.professorDto;

import com.gusta.academia.model.Aluno;
import com.gusta.academia.model.Treino;

import java.util.List;
import java.util.UUID;

public record ProfessorResponseDto(UUID id,
                                   String nome,
                                   String especialidade,
                                   List<Aluno> alunos,
                                   List<Treino> treinos){
}

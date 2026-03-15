package com.gusta.academia.service;

import com.gusta.academia.controller.Dto.exercicioDto.ExercicioCreateDto;
import com.gusta.academia.controller.Dto.exercicioDto.ExercicioResponseDto;
import com.gusta.academia.exceptions.EntidadeNaoEncontradaException;
import com.gusta.academia.model.Exercicio;
import com.gusta.academia.repository.ExercicioRepository;
import com.gusta.academia.repository.TreinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExercicioService {
    private final ExercicioRepository repository;
    private final TreinoRepository treinoRepository;


    public List<ExercicioResponseDto> listarTodos(){
        List<Exercicio> exercicioList = repository.findAll();
        return
                exercicioList.stream().map(exercicio -> new ExercicioResponseDto(exercicio.getId(),
                        exercicio.getNome(),
                        exercicio.getGrupoMuscular(),
                        exercicio.getDescricao())).toList();
    }

    public ExercicioResponseDto buscarPorId(UUID id){
        return repository.findById(id)
                .map(exercicio -> new ExercicioResponseDto(
                        exercicio.getId(),
                        exercicio.getNome(),
                        exercicio.getGrupoMuscular(),
                        exercicio.getDescricao()
                ))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));
    }


    public void deletePorId(UUID id){
        Optional<Exercicio> exercicioOptional = repository.findById(id);

        if (exercicioOptional.isEmpty()){
            Error();
        }

        Exercicio exercicio = exercicioOptional.get();

        if (treinoRepository.countTreinosByExercicioId(exercicio.getId()) > 0){
            Error();
        }

        repository.delete(exercicio);
    }



    public void salvar(ExercicioCreateDto dto){

        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.nome());
        exercicio.setDescricao(dto.descricao());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
        repository.save(exercicio);
        var idExercicio = exercicio.getId();
    }

    public ExercicioResponseDto atualizar(UUID id, ExercicioCreateDto dto){

        Exercicio exercicioExistente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exercicio não encontrado"));

        exercicioExistente.setGrupoMuscular(dto.grupoMuscular());
        exercicioExistente.setNome(dto.nome());
        exercicioExistente.setDescricao(dto.descricao());

        Exercicio exercicioAtualizado = repository.save(exercicioExistente);
        return new ExercicioResponseDto(exercicioAtualizado.getId(),
                exercicioAtualizado.getNome(),
                exercicioAtualizado.getGrupoMuscular(),
                exercicioAtualizado.getDescricao());
    }

    private static void Error() {
        throw new EntidadeNaoEncontradaException("Exercício vinculado a treinos. Não pode ser excluído.");
    }
}

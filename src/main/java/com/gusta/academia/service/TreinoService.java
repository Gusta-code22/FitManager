package com.gusta.academia.service;

import com.gusta.academia.controller.Dto.exercicioDto.ExercicioResponseDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoCreateDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoResponseDto;
import com.gusta.academia.exceptions.EntidadeNaoEncontradaException;
import com.gusta.academia.model.Aluno;
import com.gusta.academia.model.Exercicio;
import com.gusta.academia.model.Professor;
import com.gusta.academia.model.Treino;
import com.gusta.academia.repository.AlunoRepository;
import com.gusta.academia.repository.ExercicioRepository;
import com.gusta.academia.repository.ProfessorRepository;
import com.gusta.academia.repository.TreinoRepository;
import com.gusta.academia.validator.TreinoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final ExercicioRepository exercicioRepository;
    private final TreinoValidator validator;


    public void salvar(TreinoCreateDto dto){
        Aluno aluno = alunoRepository.findById(dto.idAluno())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));

        Professor professor = professorRepository.findById(dto.idProfessor())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));


        List<Exercicio> exerciciosList = exercicioRepository.findAllById(dto.idExercicios());

        if (exerciciosList.size() < dto.idExercicios().size()){
            throw new EntidadeNaoEncontradaException("Um ou mais exercícios não foram encontrados");
        }

        Treino treino = new Treino();
        treino.setAluno(aluno);
        treino.setNome(dto.nome());
        treino.setProfessor(professor);
        treino.setExercicios(exerciciosList);

        validator.validarTreino(treino, aluno);
        treinoRepository.save(treino);
    }

    public List<TreinoResponseDto> listarTodos(){
        var listTreinos = treinoRepository.findAll();
        return
                listTreinos.stream()
                .map(treino -> new TreinoResponseDto(
                        treino.getId(),
                        treino.getNome(),
                        treino.getAluno().getNome(),
                        treino.getProfessor().getNome(),
                        treino.getExercicios().stream().map(exercicio -> new ExercicioResponseDto(
                                exercicio.getId(),
                                exercicio.getNome(),
                                exercicio.getGrupoMuscular(),
                                exercicio.getDescricao()
                        )).toList())).toList();
    }

    public TreinoResponseDto buscarPorId(UUID id){
        return
                treinoRepository.findById(id)
                        .map(treino -> new TreinoResponseDto(
                                treino.getId(),
                                treino.getNome(),
                                treino.getAluno().getNome(),
                                treino.getProfessor().getNome(),
                                treino.getExercicios().stream().map(exercicio -> new ExercicioResponseDto(
                                        exercicio.getId(),
                                        exercicio.getNome(),
                                        exercicio.getGrupoMuscular(),
                                        exercicio.getDescricao()
                                )).toList()
                        ))
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Treino não encontrado"));
    }

    public void deletar(UUID id){
        Optional<Treino> treinoOptional = treinoRepository.findById(id);

        if (treinoOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Treino não encontrado");
        }

        Treino treino = treinoOptional.get();


        treinoRepository.delete(treino);
    }

    public TreinoResponseDto atualizar(UUID id, TreinoCreateDto dto){
        Aluno aluno = alunoRepository.findById(dto.idAluno())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));

        Professor professor = professorRepository.findById(dto.idProfessor())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));

        List<Exercicio> exerciciosList = exercicioRepository.findAllById(dto.idExercicios());

        if (exerciciosList.size() > dto.idExercicios().size()){
            throw new EntidadeNaoEncontradaException("Um ou mais exercícios não foram encontrados");
        }

        Treino treinoExistente = treinoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Treino não encontrado"));

        treinoExistente.setNome(dto.nome());
        treinoExistente.setAluno(aluno);
        treinoExistente.setProfessor(professor);
        treinoExistente.setExercicios(exerciciosList);

        validator.validarTreino(treinoExistente, aluno);
        Treino treinoAtualizado = treinoRepository.save(treinoExistente);

        return new TreinoResponseDto(
                treinoExistente.getId(),
                treinoExistente.getNome(),
                treinoExistente.getAluno().getNome(),
                treinoExistente.getProfessor().getNome(),
                treinoExistente.getExercicios().stream().map(exercicio -> new ExercicioResponseDto(
                        exercicio.getId(),
                        exercicio.getNome(),
                        exercicio.getGrupoMuscular(),
                        exercicio.getDescricao()
                )).toList());
    }



}


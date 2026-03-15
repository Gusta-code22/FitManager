package com.gusta.academia.service;

import com.gusta.academia.controller.Dto.AlunoDto.AlunoCreateDto;
import com.gusta.academia.controller.Dto.AlunoDto.AlunoResponseCompletoDto;
import com.gusta.academia.controller.Dto.AlunoDto.AlunoResponseDto;
import com.gusta.academia.controller.Dto.professorDto.ProfessorDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoDto;
import com.gusta.academia.exceptions.EntidadeNaoEncontradaException;
import com.gusta.academia.exceptions.OperacaoNaoPermitidaException;
import com.gusta.academia.model.Aluno;
import com.gusta.academia.model.Professor;
import com.gusta.academia.repository.AlunoRepository;
import com.gusta.academia.repository.ProfessorRepository;
import com.gusta.academia.validator.AlunoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final ProfessorRepository professorRepository;
    private final AlunoValidator validator;


    public List<AlunoResponseCompletoDto> listarAlunos(){
        List<Aluno> alunos = repository.findAll();

        return alunos.stream()
                .map(aluno -> new AlunoResponseCompletoDto(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getEmail(),
                        aluno.getDataNascimento(),
                        new ProfessorDto(
                                aluno.getProfessor().getId(),
                                aluno.getProfessor().getNome()
                        ),
                        aluno.getTreinos().stream().map(treino -> new TreinoDto(
                                treino.getId(),
                                treino.getNome()
                        )).toList()
                ))
                .toList();
    }

    public AlunoResponseCompletoDto buscarAlunoPorID(UUID id) {
        return repository.findById(id)
                .map(aluno -> new AlunoResponseCompletoDto(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getEmail(),
                        aluno.getDataNascimento(),
                        new ProfessorDto(
                                aluno.getProfessor().getId(),
                                aluno.getProfessor().getNome()
                        ),
                        aluno.getTreinos().stream().map(treino -> new TreinoDto(
                                treino.getId(),
                                treino.getNome()
                        ))
                                .toList()
                ))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));
    }

    public void salvar(AlunoCreateDto dto){

        Professor professor = professorRepository.findById(dto.idProfessor())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));

        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setProfessor(professor);
        aluno.setDataNascimento(dto.dataNascimento());

        validator.validarAluno(aluno, professor);
        repository.save(aluno);
    }

    public void deletar(UUID id){
        Optional<Aluno> alunoOptional = repository.findById(id);

        if (alunoOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Aluno não encontrado");
        }

        Aluno aluno = alunoOptional.get();

        repository.delete(aluno);
    }

    public AlunoResponseDto atualizar(UUID id, AlunoCreateDto dto){
        Aluno alunoExistente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));

        Professor professor = professorRepository.findById(dto.idProfessor())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));

//        // Valida aqui a regra do professor único
    if (alunoExistente.getProfessor() != null &&
        !alunoExistente.getProfessor().getId().equals(professor.getId())) {
        throw new OperacaoNaoPermitidaException("Aluno já está vinculado a outro professor.");
    }

        alunoExistente.setNome(dto.nome());
        alunoExistente.setEmail(dto.email());
        alunoExistente.setDataNascimento(dto.dataNascimento());
        alunoExistente.setProfessor(professor);

        validator.validarAluno(alunoExistente, professor); // pode até remover se validar manualmente acima

        Aluno alunoAtualizado = repository.save(alunoExistente);

        return new AlunoResponseDto(
                alunoAtualizado.getId(),
                alunoAtualizado.getNome(),
                alunoAtualizado.getEmail(),
                alunoAtualizado.getDataNascimento()
        );
    }
}

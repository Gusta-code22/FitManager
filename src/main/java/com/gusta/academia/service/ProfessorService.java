package com.gusta.academia.service;

import com.gusta.academia.controller.Dto.professorDto.ProfessorCreateDto;
import com.gusta.academia.controller.Dto.professorDto.ProfessorResponseDto;
import com.gusta.academia.exceptions.EntidadeNaoEncontradaException;
import com.gusta.academia.exceptions.OperacaoNaoPermitidaException;
import com.gusta.academia.model.Professor;
import com.gusta.academia.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;

    public List<ProfessorResponseDto> listarTodos(){
        var listaProf = repository.findAll();

        return listaProf.stream().map(prof -> new ProfessorResponseDto(
                prof.getId(),
                prof.getNome(),
                prof.getEspecialidade(),
                prof.getAlunos(),
                prof.getTreinos())).toList();

    }

    public ProfessorResponseDto BuscarProfPorId(UUID id){
        return repository.findById(id).map(professor -> new ProfessorResponseDto(
                professor.getId(),
                professor.getNome(),
                professor.getEspecialidade(),
                professor.getAlunos(),
                professor.getTreinos()
        )).orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));
    }

    public void delete(UUID id){
        var profEncontrado = repository.findById(id);

        if (profEncontrado.isEmpty()){
            throw new EntidadeNaoEncontradaException("Professor não encontrado");
        }

        Professor professor = profEncontrado.get();

        if (repository.countById(id) > 0){
            throw new OperacaoNaoPermitidaException("Não é possível excluir: professor possui alunos vinculados.");
        }

        repository.delete(professor);
    }

    public void salvar(ProfessorCreateDto dto){
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEspecialidade(dto.especialidade());

        repository.save(professor);
    }

    public ProfessorResponseDto atualizar(UUID id, ProfessorCreateDto dto){

        Professor professorExistente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado"));

        professorExistente.setEspecialidade(dto.especialidade());
        professorExistente.setNome(dto.nome());

        Professor professorAtualizado = repository.save(professorExistente);

        return new ProfessorResponseDto(
                professorAtualizado.getId(),
                professorAtualizado.getNome(),
                professorAtualizado.getEspecialidade(),
                professorAtualizado.getAlunos(),
                professorAtualizado.getTreinos());
    }


}

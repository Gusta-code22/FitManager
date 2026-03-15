package com.gusta.academia.validator;

import com.gusta.academia.exceptions.OperacaoNaoPermitidaException;
import com.gusta.academia.exceptions.RegistoDuplicadoException;
import com.gusta.academia.model.Aluno;
import com.gusta.academia.model.Professor;
import com.gusta.academia.repository.AlunoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlunoValidator {

    private final AlunoRepository repository;

    public AlunoValidator(AlunoRepository repository) {
        this.repository = repository;
    }

    /**
     * Valida regras de negócio para aluno:
     * - Um aluno não pode estar vinculado a mais de um professor
     * - O email do aluno deve ser único no sistema
     *
     * @param aluno    Objeto aluno que será validado
     * @param professor Professor ao qual o aluno será vinculado
     * @throws RuntimeException se alguma regra for violada
     */
    public void validarAluno(Aluno aluno, Professor professor) {
        if (existeOutroProfessorVinculado(aluno, professor)) {
            throw new OperacaoNaoPermitidaException("Aluno já está vinculado a outro professor.");
        }

        if (emailJaCadastradoParaOutroAluno(aluno.getEmail(), aluno)) {
            throw new RegistoDuplicadoException("Já existe um aluno com esse e-mail.");
        }
    }

    /**
     * Verifica se o aluno já está vinculado a outro professor diferente do passado
     *
     * @param aluno     Aluno que está sendo verificado
     * @param novoProfessor Professor que se quer vincular ao aluno
     * @return true se já existe professor diferente vinculado; false caso contrário
     */
    private boolean existeOutroProfessorVinculado(Aluno aluno, Professor novoProfessor) {
        // Se aluno já existe no banco (possui id), e já tem um professor vinculado
        // e esse professor é diferente do que está tentando vincular, retorna true


        // nao é um primeiro cadastro
        if (aluno.getId() == null){
            return false;
        }

        Optional<Aluno> alunoOptional = repository.findById(aluno.getId());

        if (alunoOptional.isEmpty()){
            return false;
        }

        // professor ja vinculado
        Professor professorAtual = alunoOptional.get().getProfessor();

        // se o o aluno estive vinculado a um professor ou seja id != null, verifica se o id do novo professor
        // nao é igual ao professor ja vinculado, gereando true(Exception)
        return  professorAtual != null &&!novoProfessor.getId().equals(professorAtual.getId());
    }

    /**
     * Verifica se o e-mail já está cadastrado para outro aluno (diferente do atual)
     *
     * @param email Email a ser verificado
     * @param aluno Aluno que está sendo verificado (para não comparar com ele mesmo)
     * @return true se o email está cadastrado para outro aluno; false caso contrário
     */
    private boolean emailJaCadastradoParaOutroAluno(String email, Aluno aluno) {
        Optional<Aluno> alunoOptional = repository.findByEmail(email);

        if (alunoOptional.isEmpty()) {
            return false; // email não existe no banco, está liberado
        }

        Aluno alunoBanco = alunoOptional.get();
        // Retorna true se o aluno encontrado no banco não for o mesmo (diferente id)
        return !alunoBanco.getId().equals(aluno.getId());
    }
}
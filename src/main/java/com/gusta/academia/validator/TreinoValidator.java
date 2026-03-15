package com.gusta.academia.validator;

import com.gusta.academia.exceptions.CampoInvalidoException;
import com.gusta.academia.exceptions.RegistoDuplicadoException;
import com.gusta.academia.model.Aluno;
import com.gusta.academia.model.Treino;
import com.gusta.academia.repository.ExercicioRepository;
import org.springframework.stereotype.Component;

@Component
public class TreinoValidator {

    private final ExercicioRepository exercicioRepository;

    public TreinoValidator(ExercicioRepository exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    /**
     * Valida regras de negócio para o treino:
     * - Não permitir treinos com o mesmo nome para o mesmo aluno
     * - Um treino deve ter pelo menos um exercício vinculado
     *
     * @param treino treino que será validado
     * @param aluno  aluno ao qual o treino está vinculado
     * @throws RuntimeException se alguma regra for violada
     */
    public void validarTreino(Treino treino, Aluno aluno) {
        if (existeTreinoComMesmoNomeParaAluno(treino, aluno)) {
            throw new RegistoDuplicadoException("Já existe um treino com esse nome vinculado a esse aluno.");
        }

        if (naoPossuiExercicios(treino)) {
            throw new CampoInvalidoException("exercicios", "Um treino deve ter pelo menos 1 exercício.");
        }
    }

    /**
     * Verifica se o aluno já possui um treino com o mesmo nome (ignorando maiúsculas/minúsculas)
     *
     * @param treino treino que está sendo validado
     * @param aluno  aluno para verificar os treinos existentes
     * @return true se já existe treino com o mesmo nome para o aluno; false caso contrário
     */
    private boolean existeTreinoComMesmoNomeParaAluno(Treino treino, Aluno aluno) {
        return aluno.getTreinos().stream()
                .map(Treino::getNome)
                .anyMatch(nome -> nome.equalsIgnoreCase(treino.getNome().trim()));
    }

    /**
     * Verifica se o treino possui pelo menos um exercício
     *
     * @param treino treino a ser verificado
     * @return true se não possuir exercícios; false caso possua ao menos 1
     */
    private boolean naoPossuiExercicios(Treino treino) {
        return treino.getExercicios() == null || treino.getExercicios().isEmpty();
    }
}
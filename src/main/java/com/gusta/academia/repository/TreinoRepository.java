package com.gusta.academia.repository;

import com.gusta.academia.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, UUID> {

    @Query("SELECT COUNT(t) FROM Treino t JOIN t.exercicios e WHERE e.id = :exercicioId")
    long countTreinosByExercicioId(@Param("exercicioId") UUID exercicioId);
}

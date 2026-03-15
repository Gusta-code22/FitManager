package com.gusta.academia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    @ManyToOne
    @JsonIgnore
    private Professor professor;

    @ManyToOne
    @JsonIgnore
    private Aluno aluno;

    @ManyToMany
    @JoinTable(
            name = "treino_exercicios",
            joinColumns = @JoinColumn(name = "treino_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicio_id")

    )
    @JsonIgnore
    private List<Exercicio> exercicios;
}

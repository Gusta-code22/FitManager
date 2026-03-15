package com.gusta.academia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String grupoMuscular;
    private String descricao;

    @ManyToMany(mappedBy = "exercicios")
    @JsonIgnore
    private List<Treino> treinos;

    // tem muitos treinos para muitos exercicios
}


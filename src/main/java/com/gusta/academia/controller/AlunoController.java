package com.gusta.academia.controller;

import com.gusta.academia.controller.Dto.AlunoDto.AlunoCreateDto;
import com.gusta.academia.controller.Dto.AlunoDto.AlunoResponseCompletoDto;
import com.gusta.academia.controller.Dto.AlunoDto.AlunoResponseDto;
import com.gusta.academia.repository.AlunoRepository;
import com.gusta.academia.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AlunoCreateDto dto) {
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<List<AlunoResponseCompletoDto>> listarAlunos() {
        return ResponseEntity.ok(service.listarAlunos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR', 'SCOPE_USER')")
    public ResponseEntity<AlunoResponseCompletoDto> listarAlunosPorID(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(service.buscarAlunoPorID(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<AlunoResponseDto> atualizar(@PathVariable @Valid UUID id, @RequestBody @Valid AlunoCreateDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> deletar(@PathVariable @Valid UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }



}


//* GET /students → Listar alunos
//* GET /students/{id} → Buscar aluno por ID
//* POST /students → Criar novo aluno
//* PUT /students/{id} → Atualizar aluno
//* DELETE /students/{id} → Deletar aluno
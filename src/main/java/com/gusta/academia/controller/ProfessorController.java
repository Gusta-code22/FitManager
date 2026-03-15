package com.gusta.academia.controller;

import com.gusta.academia.controller.Dto.professorDto.ProfessorCreateDto;
import com.gusta.academia.controller.Dto.professorDto.ProfessorResponseDto;
import com.gusta.academia.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<List<ProfessorResponseDto>> listarTodos(){
        var list = service.listarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<ProfessorResponseDto> buscarPorId(@PathVariable("id") @Valid UUID id){
        return ResponseEntity.ok(service.BuscarProfPorId(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> deletePorID(@PathVariable("id") @Valid UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> salvarProf(@RequestBody @Valid ProfessorCreateDto dto){
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProfessorResponseDto> atualizar(@PathVariable("id") @Valid UUID id,
                                                          @RequestBody @Valid ProfessorCreateDto dto){
        ProfessorResponseDto professorAtualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(professorAtualizado);
    }
}

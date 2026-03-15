package com.gusta.academia.controller;

import com.gusta.academia.controller.Dto.exercicioDto.ExercicioCreateDto;
import com.gusta.academia.controller.Dto.exercicioDto.ExercicioResponseDto;
import com.gusta.academia.service.ExercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercicios")
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR', 'SCOPE_USER')")
    public ResponseEntity<List<ExercicioResponseDto>> listarTodos(){
        List<ExercicioResponseDto> exercicioResponseDtos = service.listarTodos();
        return ResponseEntity.ok(exercicioResponseDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR', 'SCOPE_USER')")
    public ResponseEntity<ExercicioResponseDto> buscarPorId(@PathVariable("id") @Valid UUID id){
        ExercicioResponseDto responseDto = service.buscarPorId(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid ExercicioCreateDto dto){
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<ExercicioResponseDto> atualizar(@PathVariable("id") @Valid UUID id,
                                                          @RequestBody @Valid ExercicioCreateDto dto){
        ExercicioResponseDto exercicioResponseDto = service.atualizar(id, dto);
        return ResponseEntity.ok(exercicioResponseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> deletePorId(@PathVariable("id") @Valid UUID id){
        service.deletePorId(id);
        return ResponseEntity.noContent().build();
    }
}

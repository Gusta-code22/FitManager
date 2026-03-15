package com.gusta.academia.controller;

import com.gusta.academia.controller.Dto.exercicioDto.ExercicioCreateDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoCreateDto;
import com.gusta.academia.controller.Dto.treinoDto.TreinoResponseDto;
import com.gusta.academia.service.TreinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<List<TreinoResponseDto>> listarTodos(){
        List<TreinoResponseDto> treinoResponseDtos = service.listarTodos();
        return ResponseEntity.ok(treinoResponseDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<TreinoResponseDto> buscarPorId(@PathVariable("id")UUID id){
        TreinoResponseDto treinoResponseDto = service.buscarPorId(id);
        return ResponseEntity.ok(treinoResponseDto);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> salvar(@RequestBody TreinoCreateDto dto){
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<Void> deletarTreino(@PathVariable("id") UUID id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_PROFESSOR')")
    public ResponseEntity<TreinoResponseDto> atualizar(@PathVariable("id") UUID id,
                                                       @RequestBody TreinoCreateDto dto){
        TreinoResponseDto treinoResponseDto = service.atualizar(id, dto);
        return ResponseEntity.ok(treinoResponseDto);
    }

}

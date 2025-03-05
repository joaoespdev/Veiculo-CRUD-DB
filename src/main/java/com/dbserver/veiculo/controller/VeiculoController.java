package com.dbserver.veiculo.controller;

import com.dbserver.veiculo.dto.VeiculoDTO;
import com.dbserver.veiculo.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Autowired
    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> criarVeiculo(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO veiculoSalvo = veiculoService.save(veiculoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarTodosVeiculos() {
        List<VeiculoDTO> veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> buscarVeiculoPorId(@PathVariable Long id) {
        Optional<VeiculoDTO> veiculo = veiculoService.findById(id);
        return veiculo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoDTO veiculoDTO
    ) {
        if (!id.equals(veiculoDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        VeiculoDTO veiculoAtualizado = veiculoService.save(veiculoDTO);
        return ResponseEntity.ok(veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

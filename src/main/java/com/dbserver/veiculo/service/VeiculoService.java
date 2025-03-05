package com.dbserver.veiculo.service;

import com.dbserver.veiculo.dto.VeiculoDTO;
import com.dbserver.veiculo.exception.VeiculoNotFoundException;
import com.dbserver.veiculo.model.Veiculo;
import com.dbserver.veiculo.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoDTO.toEntity();

        Veiculo savedVeiculo = veiculoRepository.save(veiculo);

        return VeiculoDTO.fromEntity(savedVeiculo);
    }

    public List<VeiculoDTO> findAll() {
        return veiculoRepository.findAll().stream()
                .map(VeiculoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<VeiculoDTO> findById(Long id) {
        return veiculoRepository.findById(id)
                .map(VeiculoDTO::fromEntity);
    }

    public void deleteById(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new VeiculoNotFoundException(id);
        }
        veiculoRepository.deleteById(id);
    }
}
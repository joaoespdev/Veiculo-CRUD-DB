package com.dbserver.veiculo;

import com.dbserver.veiculo.dto.CarroDTO;
import com.dbserver.veiculo.dto.VeiculoDTO;
import com.dbserver.veiculo.exception.VeiculoNotFoundException;
import com.dbserver.veiculo.model.Carro;
import com.dbserver.veiculo.repository.VeiculoRepository;
import com.dbserver.veiculo.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @Test
    void deveSalvarVeiculo() {
        Carro carro = new Carro();
        carro.setId(1L);
        when(veiculoRepository.save(any())).thenReturn(carro);

        VeiculoDTO dto = new CarroDTO();
        dto.setMarca("Toyota");
        VeiculoDTO savedDto = veiculoService.save(dto);

        assertEquals(1L, savedDto.getId());
    }

    @Test
    void deveLancarExcecaoAoExcluirVeiculoInexistente() {
        Long idInexistente = 999L;

        when(veiculoRepository.existsById(idInexistente)).thenReturn(false);

        assertThrows(VeiculoNotFoundException.class, () -> {
            veiculoService.deleteById(idInexistente);
        });

        verify(veiculoRepository, never()).deleteById(idInexistente);
    }

    @Test
    void deveRetornarVazioAoBuscarIdInexistente() {
        when(veiculoRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<VeiculoDTO> dto = veiculoService.findById(999L);
        assertTrue(dto.isEmpty());
    }
}

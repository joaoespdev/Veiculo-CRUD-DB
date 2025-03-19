package com.dbserver.veiculo;

import com.dbserver.veiculo.dto.CarroDTO;
import com.dbserver.veiculo.dto.VeiculoDTO;
import com.dbserver.veiculo.exception.VeiculoNotFoundException;
import com.dbserver.veiculo.model.Carro;
import com.dbserver.veiculo.model.Veiculo;
import com.dbserver.veiculo.repository.VeiculoRepository;
import com.dbserver.veiculo.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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
    void deveRetornarListaDeVeiculos() {
        Veiculo veiculo1 = new Carro();
        veiculo1.setId(1L);
        Veiculo veiculo2 = new Carro();
        veiculo2.setId(2L);
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo1, veiculo2));

        List<VeiculoDTO> resultado = veiculoService.findAll();

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        verify(veiculoRepository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaVeiculos() {
        when(veiculoRepository.findAll()).thenReturn(Collections.emptyList());

        List<VeiculoDTO> resultado = veiculoService.findAll();

        assertTrue(resultado.isEmpty());
        verify(veiculoRepository).findAll();
    }

    @Test
    void deveRetornarVeiculoDTOQuandoIdExistir() {
        Veiculo veiculo = new Carro();
        veiculo.setId(1L);
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        Optional<VeiculoDTO> resultado = veiculoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(veiculoRepository).findById(1L);
    }

    @Test
    void deveDeletarVeiculoQuandoIdExistir() {
        Long id = 1L;
        when(veiculoRepository.existsById(id)).thenReturn(true);

        veiculoService.deleteById(id);

        verify(veiculoRepository).deleteById(id);
    }

    @Test
    void deveSalvarVeiculo() {
        Carro carro = new Carro();
        carro.setId(1L);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(carro);

        VeiculoDTO dto = new CarroDTO();
        dto.setMarca("Toyota");
        VeiculoDTO savedDto = veiculoService.save(dto);

        assertEquals(1L, savedDto.getId());
        verify(veiculoRepository).save(any(Veiculo.class));
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
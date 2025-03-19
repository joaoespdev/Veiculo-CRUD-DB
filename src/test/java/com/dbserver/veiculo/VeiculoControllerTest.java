package com.dbserver.veiculo;

import com.dbserver.veiculo.controller.VeiculoController;
import com.dbserver.veiculo.dto.CaminhaoDTO;
import com.dbserver.veiculo.dto.CarroDTO;
import com.dbserver.veiculo.dto.MotoDTO;
import com.dbserver.veiculo.exception.VeiculoNotFoundException;
import com.dbserver.veiculo.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarVeiculo() throws Exception {
        CarroDTO dto = new CarroDTO();
        dto.setTipo("Carro");
        dto.setMarca("Toyota");
        dto.setModelo("Corolla");
        dto.setNumeroPortas(4);
        dto.setAnoFabricacao(2005);

        when(veiculoService.save(any())).thenReturn(dto);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveListarTodosVeiculos() throws Exception {
        CarroDTO carroDTO = new CarroDTO();
        carroDTO.setId(1L);
        carroDTO.setTipo("Carro");
        carroDTO.setMarca("Toyota");
        carroDTO.setModelo("Corolla");
        carroDTO.setAnoFabricacao(2005);
        carroDTO.setNumeroPortas(4);

        MotoDTO motoDTO = new MotoDTO();
        motoDTO.setId(2L);
        motoDTO.setTipo("Moto");
        motoDTO.setMarca("Honda");
        motoDTO.setModelo("CB500");
        motoDTO.setAnoFabricacao(2010);
        motoDTO.setTemPartidaEletrica(true);

        CaminhaoDTO caminhaoDTO = new CaminhaoDTO();
        caminhaoDTO.setId(3L);
        caminhaoDTO.setTipo("Caminhao");
        caminhaoDTO.setMarca("Test");
        caminhaoDTO.setModelo("Test2");
        caminhaoDTO.setAnoFabricacao(2002);
        caminhaoDTO.setCapacidadeCarga(1000);

        when(veiculoService.findAll()).thenReturn(Arrays.asList(carroDTO, motoDTO, caminhaoDTO));

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(carroDTO.getId()))
                .andExpect(jsonPath("$[0].tipo").value(carroDTO.getTipo()))
                .andExpect(jsonPath("$[0].marca").value(carroDTO.getMarca()))
                .andExpect(jsonPath("$[0].modelo").value(carroDTO.getModelo()))
                .andExpect(jsonPath("$[0].anoFabricacao").value(carroDTO.getAnoFabricacao()))
                .andExpect(jsonPath("$[0].numeroPortas").value(carroDTO.getNumeroPortas()))
                .andExpect(jsonPath("$[1].id").value(motoDTO.getId()))
                .andExpect(jsonPath("$[1].tipo").value(motoDTO.getTipo()))
                .andExpect(jsonPath("$[1].marca").value(motoDTO.getMarca()))
                .andExpect(jsonPath("$[1].modelo").value(motoDTO.getModelo()))
                .andExpect(jsonPath("$[1].anoFabricacao").value(motoDTO.getAnoFabricacao()))
                .andExpect(jsonPath("$[1].temPartidaEletrica").value(motoDTO.getTemPartidaEletrica()))
                .andExpect(jsonPath("$[2].id").value(caminhaoDTO.getId()))
                .andExpect(jsonPath("$[2].tipo").value(caminhaoDTO.getTipo()))
                .andExpect(jsonPath("$[2].marca").value(caminhaoDTO.getMarca()))
                .andExpect(jsonPath("$[2].modelo").value(caminhaoDTO.getModelo()))
                .andExpect(jsonPath("$[2].anoFabricacao").value(caminhaoDTO.getAnoFabricacao()))
                .andExpect(jsonPath("$[2].capacidadeCarga").value(caminhaoDTO.getCapacidadeCarga()));
    }

    @Test
    void deveBuscarVeiculoPorIdExistente() throws Exception {
        CarroDTO dto = new CarroDTO();
        dto.setId(1L);
        dto.setTipo("Carro");
        dto.setMarca("Toyota");
        dto.setModelo("Corolla");
        dto.setAnoFabricacao(2005);

        when(veiculoService.findById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.tipo").value(dto.getTipo()))
                .andExpect(jsonPath("$.marca").value(dto.getMarca()))
                .andExpect(jsonPath("$.modelo").value(dto.getModelo()))
                .andExpect(jsonPath("$.anoFabricacao").value(dto.getAnoFabricacao()));
    }

    @Test
    void deveRetornar404AoBuscarVeiculoInexistente() throws Exception {
        when(veiculoService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/veiculos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoAtualizarComIdInvalido() throws Exception {
        CarroDTO dto = new CarroDTO();
        dto.setId(2L);
        dto.setTipo("Carro");
        dto.setMarca("Toyota");
        dto.setNumeroPortas(4);

        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarVeiculoComIdValido() throws Exception {
        CarroDTO dto = new CarroDTO();
        dto.setId(1L);
        dto.setTipo("Carro");
        dto.setMarca("Toyota");
        dto.setModelo("Corolla");
        dto.setNumeroPortas(4);
        dto.setAnoFabricacao(2005);

        when(veiculoService.save(any())).thenReturn(dto);

        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.tipo").value(dto.getTipo()))
                .andExpect(jsonPath("$.marca").value(dto.getMarca()))
                .andExpect(jsonPath("$.modelo").value(dto.getModelo()))
                .andExpect(jsonPath("$.anoFabricacao").value(dto.getAnoFabricacao()));
    }

    @Test
    void deveExcluirVeiculo() throws Exception {
        doNothing().when(veiculoService).deleteById(1L);
        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoExcluirVeiculoInexistente() throws Exception {
        doThrow(new VeiculoNotFoundException(999L)).when(veiculoService).deleteById(999L);
        mockMvc.perform(delete("/veiculos/999"))
                .andExpect(status().isNotFound());
    }
}
package com.dbserver.veiculo;

import com.dbserver.veiculo.controller.VeiculoController;
import com.dbserver.veiculo.dto.CarroDTO;
import com.dbserver.veiculo.exception.VeiculoNotFoundException;
import com.dbserver.veiculo.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        dto.setNumeroPortas(4);

        when(veiculoService.save(any())).thenReturn(dto);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
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

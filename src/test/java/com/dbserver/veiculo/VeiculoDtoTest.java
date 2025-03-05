package com.dbserver.veiculo;


import com.dbserver.veiculo.dto.CarroDTO;
import com.dbserver.veiculo.model.Carro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoDtoTest {

    @Test
    void deveFuncionarGetsESetsConstrutorEValorIdClasseEClasseDtoDevemBater() {

        CarroDTO carroDTO = new CarroDTO();
        Carro carro = new Carro();
        carro.setId(1L);
        carroDTO.setId(1L);
        carroDTO.setMarca("Fiat");
        carroDTO.setModelo("Uno");
        carroDTO.setNumeroPortas(4);
        carroDTO.setAnoFabricacao(2000);

        assertEquals(carro.getId(),carroDTO.getId());
    }
}

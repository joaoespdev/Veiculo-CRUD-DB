package com.dbserver.veiculo.dto;

import com.dbserver.veiculo.model.Carro;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarroDTO extends VeiculoDTO {

    @NotNull(message = "O campo 'numeroPortas' não pode ser nulo.")
    private Integer numeroPortas;

    public static CarroDTO fromEntity(Carro carro) {
        CarroDTO carroDTO = new CarroDTO();
        carroDTO.setId(carro.getId());
        carroDTO.setTipo(carro.getTipo());
        carroDTO.setMarca(carro.getMarca());
        carroDTO.setModelo(carro.getModelo());
        carroDTO.setAnoFabricacao(carro.getAnoFabricacao());
        carroDTO.setNumeroPortas(carro.getNumeroPortas());
        return carroDTO;
    }

    @Override
    public Carro toEntity() {
        Carro carro = new Carro();
        carro.setId(this.getId());
        carro.setTipo(this.getTipo());
        carro.setMarca(this.getMarca());
        carro.setModelo(this.getModelo());
        carro.setAnoFabricacao(this.getAnoFabricacao());
        carro.setNumeroPortas(this.getNumeroPortas());
        return carro;
    }
}

package com.dbserver.veiculo.dto;

import com.dbserver.veiculo.model.Caminhao;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CaminhaoDTO extends VeiculoDTO {

    @NotNull(message = "O campo 'capacidadeCarga' não pode ser nulo.")
    private Integer capacidadeCarga;

    public static CaminhaoDTO fromEntity(Caminhao caminhao) {
        CaminhaoDTO caminhaoDTO = new CaminhaoDTO();
        caminhaoDTO.setId(caminhao.getId());
        caminhaoDTO.setTipo(caminhao.getTipo());
        caminhaoDTO.setMarca(caminhao.getMarca());
        caminhaoDTO.setModelo(caminhao.getModelo());
        caminhaoDTO.setAnoFabricacao(caminhao.getAnoFabricacao());
        caminhaoDTO.setCapacidadeCarga(caminhao.getCapacidadeCarga());
        return caminhaoDTO;
    }

    @Override
    public Caminhao toEntity() {
        Caminhao caminhao = new Caminhao();
        caminhao.setId(this.getId());
        caminhao.setTipo(this.getTipo());
        caminhao.setMarca(this.getMarca());
        caminhao.setModelo(this.getModelo());
        caminhao.setAnoFabricacao(this.getAnoFabricacao());
        caminhao.setCapacidadeCarga(this.getCapacidadeCarga());
        return caminhao;
    }
}

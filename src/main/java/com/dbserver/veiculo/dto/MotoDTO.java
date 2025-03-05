package com.dbserver.veiculo.dto;

import com.dbserver.veiculo.model.Moto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotoDTO extends VeiculoDTO {

    @NotNull(message = "O campo 'temPartidaEletrica' não pode ser nulo.")
    private Boolean temPartidaEletrica;

    public static MotoDTO fromEntity(Moto moto) {
        MotoDTO motoDTO = new MotoDTO();
        motoDTO.setId(moto.getId());
        motoDTO.setTipo(moto.getTipo());
        motoDTO.setMarca(moto.getMarca());
        motoDTO.setModelo(moto.getModelo());
        motoDTO.setAnoFabricacao(moto.getAnoFabricacao());
        motoDTO.setTemPartidaEletrica(moto.getTemPartidaEletrica());
        return motoDTO;
    }

    @Override
    public Moto toEntity() {
        Moto moto = new Moto();
        moto.setId(this.getId());
        moto.setTipo(this.getTipo());
        moto.setMarca(this.getMarca());
        moto.setModelo(this.getModelo());
        moto.setAnoFabricacao(this.getAnoFabricacao());
        moto.setTemPartidaEletrica(this.getTemPartidaEletrica());
        return moto;
    }
}

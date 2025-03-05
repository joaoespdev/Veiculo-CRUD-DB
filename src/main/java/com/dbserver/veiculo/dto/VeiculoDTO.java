package com.dbserver.veiculo.dto;

import com.dbserver.veiculo.model.Caminhao;
import com.dbserver.veiculo.model.Carro;
import com.dbserver.veiculo.model.Moto;
import com.dbserver.veiculo.model.Veiculo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarroDTO.class, name = "Carro"),
        @JsonSubTypes.Type(value = MotoDTO.class, name = "Moto"),
        @JsonSubTypes.Type(value = CaminhaoDTO.class, name = "Caminhao")
})
public abstract class VeiculoDTO {
    private Long id;

    private String tipo;

    @NotNull(message = "O nome da marca não pode ser nulo.")
    private String  marca;

    @NotNull(message = "O modelo não pode ser nulo.")
    private String modelo;

    private Integer anoFabricacao;

    public static VeiculoDTO fromEntity(Veiculo veiculo) {
        VeiculoDTO dto;
        if (veiculo instanceof Carro) {
            dto = CarroDTO.fromEntity((Carro) veiculo);
        } else if (veiculo instanceof Moto) {
            dto = MotoDTO.fromEntity((Moto) veiculo);
        } else if (veiculo instanceof Caminhao) {
            dto = CaminhaoDTO.fromEntity((Caminhao) veiculo);
        } else {
            throw new IllegalArgumentException("Tipo de veículo não suportado");
        }
        dto.setTipo(veiculo.getClass().getSimpleName());
        return dto;
    }

    public abstract Veiculo toEntity();
}

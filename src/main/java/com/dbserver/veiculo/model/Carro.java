package com.dbserver.veiculo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Carro extends Veiculo {

    private Integer numeroPortas;

    public Carro(String tipo, String marca, String modelo, Integer anoFabricacao, Integer numeroPortas) {
        super(tipo, marca, modelo, anoFabricacao);
        this.numeroPortas = numeroPortas;
    }
}

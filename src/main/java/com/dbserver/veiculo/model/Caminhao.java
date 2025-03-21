package com.dbserver.veiculo.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Caminhao extends Veiculo {

    private Integer capacidadeCarga;

    public Caminhao(String tipo, String marca, String modelo, Integer anoFabricacao, Integer capacidadeCarga) {
        super(tipo, marca, modelo, anoFabricacao);
        this.capacidadeCarga = capacidadeCarga;
    }
}

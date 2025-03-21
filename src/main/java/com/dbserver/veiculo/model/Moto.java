package com.dbserver.veiculo.model;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Moto extends Veiculo {

    private Boolean temPartidaEletrica;

    public Moto(String tipo, String marca, String modelo, Integer anoFabricacao, Boolean temPartidaEletrica) {
        super(tipo, marca, modelo, anoFabricacao);
        this.temPartidaEletrica = temPartidaEletrica;
    }
}

package com.dbserver.veiculo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public abstract class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String marca;
    private String modelo;
    private Integer anoFabricacao;

    public Veiculo(String tipo, String marca, String modelo, Integer anoFabricacao) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
    }
}

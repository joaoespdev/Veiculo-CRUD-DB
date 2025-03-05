package com.dbserver.veiculo.exception;

public class VeiculoNotFoundException extends RuntimeException{
    public VeiculoNotFoundException(Long id) {
        super("Veículo com ID " + id + "não encontrado.");
    }
}

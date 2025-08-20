package com.api.Loja.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LojaDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;
}

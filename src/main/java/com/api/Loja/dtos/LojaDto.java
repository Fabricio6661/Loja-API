package com.api.Loja.dtos;

import com.api.Loja.models.ProdutoModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Data
public class LojaDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;


    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;

    private List<ProdutoModel> produtos = new ArrayList<>();
}

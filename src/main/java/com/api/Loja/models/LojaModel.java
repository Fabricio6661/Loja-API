package com.api.Loja.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_lojas")
public class LojaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String endereco;
    private String cnpj;

    @OneToMany(mappedBy = "lojaModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference

    private List<ProdutoModel> produtos = new ArrayList<>();

}

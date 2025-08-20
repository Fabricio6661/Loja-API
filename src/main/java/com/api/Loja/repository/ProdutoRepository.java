package com.api.Loja.repository;

import com.api.Loja.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

    //List<ProdutoModel> findByNome(String nome);

    List<ProdutoModel> findByNomeContainsIgnoreCase(String nome);
}

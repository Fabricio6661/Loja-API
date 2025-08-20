package com.api.Loja.repository;

import com.api.Loja.models.LojaModel;
import com.api.Loja.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LojaRepository extends JpaRepository<LojaModel, UUID> {

    List<LojaModel> findByNomeContainsIgnoreCase(String nome);
}

package com.api.Loja.controller;

import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.services.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ProdutoDto dto){

        ProdutoModel produtoSalvo = produtoService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping("/listar")
    public List<ProdutoModel> listar(){
        return produtoService.listar();
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid ProdutoDto dto,
            @PathVariable(value = "id")UUID id
    ){
        try {
            ProdutoModel produtoEditado = produtoService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoEditado);
        }catch (Exception e){
            //RETORNA ERRO 500 COM AMENSAGEM DE ERRO PARA O FRONT
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> excluir(@PathVariable(value = "id")UUID id){
        try {
            produtoService.delete(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deu ruim");
        }
    }

    @GetMapping("/buscar")
    public List<ProdutoModel> buscar(
            @RequestParam String nomeBusca
    ){
        return produtoService.buscarPorNome(nomeBusca);
    }
}

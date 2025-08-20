package com.api.Loja.controller;

import com.api.Loja.dtos.LojaDto;
import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.LojaModel;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.services.LojaService;
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
@RequestMapping("/lojas")
public class LojaController {
    private final LojaService lojaService;
    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }


    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid LojaDto dto){

        LojaModel lojaSalva = lojaService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(lojaSalva);
    }

    @GetMapping("/listar")
    public List<LojaModel> listar(){
        return lojaService.listar();
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid LojaDto dto,
            @PathVariable(value = "id")UUID id
    ){
        try {
            LojaModel lojaEditada = lojaService.atualizar(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(lojaEditada);
        }catch (Exception e){
            //RETORNA ERRO 500 COM AMENSAGEM DE ERRO PARA O FRONT
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> excluir(@PathVariable(value = "id")UUID id){
        try {
            lojaService.delete(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deu ruim");
        }
    }

    @GetMapping("/buscar")
    public List<LojaModel> buscar(
            @RequestParam String nomeBusca
    ){
        return lojaService.buscarPorNome(nomeBusca);
    }
}

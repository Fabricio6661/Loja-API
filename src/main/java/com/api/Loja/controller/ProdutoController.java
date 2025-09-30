package com.api.Loja.controller;

import com.api.Loja.dtos.ProdutoDto;
import com.api.Loja.models.LojaModel;
import com.api.Loja.models.ProdutoModel;
import com.api.Loja.repository.LojaRepository;
import com.api.Loja.services.LojaService;
import com.api.Loja.services.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final LojaRepository lojaRepository;
    private final LojaService lojaService;

    public ProdutoController(ProdutoService produtoService, LojaRepository lojaRepository, LojaService lojaService) {
        this.produtoService = produtoService;
        this.lojaRepository = lojaRepository;
        this.lojaService = lojaService;

    }


    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid ProdutoDto dto){

        Optional<LojaModel> loja = lojaRepository.findById(dto.getLojaId());
        if (!loja.isPresent()) {
            return ResponseEntity.badRequest().body("A loja não existe" + dto.getLojaId());
        }
        //Cria o modelo do produto e associa dto com model

        ProdutoModel produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(dto, produtoModel, "id", "lojaModel");
        produtoModel.setLojaModel(loja.get());//associa

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(produtoModel));

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
            Optional<LojaModel> loja = lojaRepository.findById(dto.getLojaId());
            if (!loja.isPresent()) {
                return ResponseEntity.badRequest().body("Loja não encontrada");
            }

            var produto = produtoService.findById(id);

            if (!produto.isPresent()) {
                return ResponseEntity.badRequest().body("Prduto não encontrado");
            }
            ProdutoModel produtoModel = new ProdutoModel();
            BeanUtils.copyProperties(dto, produtoModel, "lojaModel");
            produtoModel.setId(id);
            produtoModel.setLojaModel(loja.get());
            return ResponseEntity.ok(produtoService.salvar(produtoModel));

        }catch (Exception e){
            //RETORNA ERRO 500 COM AMENSAGEM DE ERRO PARA O FRONT
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro");
        }
    }

    @PostMapping("/deletar/{id}")
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

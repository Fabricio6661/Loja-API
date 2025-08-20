package com.api.Loja.services;

import com.api.Loja.dtos.LojaDto;
import com.api.Loja.models.LojaModel;
import com.api.Loja.repository.LojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LojaService {
    private final LojaRepository lojaRepository;

    public LojaService(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    public LojaModel create(LojaDto dto) {
        LojaModel loja = new LojaModel();
        loja.setNome(dto.getNome());
        loja.setCnpj(dto.getCnpj());
        loja.setEndereco(dto.getEndereco());
        return lojaRepository.save(loja);
    }

    public List<LojaModel> listar() {
        return lojaRepository.findAll();
    }

    public LojaModel atualizar(LojaDto dto, UUID id) {
        LojaModel existente = lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

        existente.setNome(dto.getNome());
        existente.setCnpj(dto.getCnpj());
        existente.setEndereco(dto.getEndereco());
        return lojaRepository.save(existente);
    }

    public void delete(UUID id) {
        LojaModel existente = lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        lojaRepository.delete(existente);
    }

    public List<LojaModel> buscarPorNome(String nomeBusca) {
        return lojaRepository.findByNomeContainsIgnoreCase(nomeBusca);
    }
}


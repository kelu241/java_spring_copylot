package com.example.springcopylot.controller;

import com.example.springcopylot.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private com.example.springcopylot.repository.IProjetoRepository projetoRepository;

    @GetMapping
    public CompletableFuture<Iterable<Projeto>> getAllProjetosAsync() {
        return projetoRepository.findAllAsync();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Projeto> getProjetoByIdAsync(@PathVariable Long id) {
        return projetoRepository.findByIdAsync(id);
    }

    @PostMapping
    public CompletableFuture<Projeto> addProjetoAsync(@RequestBody Projeto projeto) {
        return projetoRepository.saveAsync(projeto);
    }

    @PutMapping("/{id}")
    public CompletableFuture<Projeto> updateProjetoAsync(@PathVariable Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        return projetoRepository.saveAsync(projeto);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Projeto> deleteProjetoAsync(@PathVariable Long id) {
        return projetoRepository.deleteByIdAsync(id);
    }
}

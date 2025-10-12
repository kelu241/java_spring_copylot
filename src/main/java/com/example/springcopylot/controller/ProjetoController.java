package com.example.springcopylot.controller;

import com.example.springcopylot.model.Projeto;
import com.example.springcopylot.pagination.PagedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import com.example.springcopylot.unionofwork.IUnionofwork;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private IUnionofwork unionofwork;

    @GetMapping
    public CompletableFuture<Iterable<Projeto>> getAllProjetosAsync() {
        return unionofwork.GetProjetoRepository().findAllAsync();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Projeto> getProjetoByIdAsync(@PathVariable Long id) {
        return unionofwork.GetProjetoRepository().findByIdAsync(id);
    }

    @PostMapping
    public CompletableFuture<Projeto> addProjetoAsync(@RequestBody Projeto projeto) {
        return unionofwork.GetProjetoRepository().saveAsync(projeto);
    }

    @PutMapping("/{id}")
    public CompletableFuture<Projeto> updateProjetoAsync(@PathVariable Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        return unionofwork.GetProjetoRepository().saveAsync(projeto);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Projeto> deleteProjetoAsync(@PathVariable Long id) {
        return unionofwork.GetProjetoRepository().deleteByIdAsync(id);
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedList<Projeto>> paginateProjetosAsync(@RequestParam int pageNumber,
            @RequestParam int pageSize) {
        return unionofwork.GetProjetoRepository().paginateAsync(pageNumber, pageSize);
    }

 @GetMapping("/filter")
public CompletableFuture<Iterable<Projeto>> searchProjetosAsync(
        @RequestParam String campo,           // nome, descricao, etc.
        @RequestParam String valor) {         // texto a buscar
    
    // Criar predicate baseado nos parâmetros
    Predicate<Projeto> predicate = projeto -> {
        switch (campo.toLowerCase()) {
            case "nome":
                return projeto.getNome().toLowerCase().contains(valor.toLowerCase());
            case "idade":
                return String.valueOf(projeto.getIdade()).equals(valor);
            default:
                return projeto.getNome().toLowerCase().contains(valor.toLowerCase());
        }
    };
    
    return unionofwork.GetProjetoRepository().searchAsync(predicate);
}
    
}
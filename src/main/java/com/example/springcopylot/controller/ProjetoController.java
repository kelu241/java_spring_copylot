package com.example.springcopylot.controller;

import com.example.springcopylot.model.Projeto;
import com.example.springcopylot.pagination.PagedList;
import com.example.springcopylot.MetodoExtensao.ProjetoExtensao;
import com.example.springcopylot.logging.LogExecution;
import com.example.springcopylot.logging.CustomLogger;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.springcopylot.DTO.ProjetoDTO;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.example.springcopylot.unionofwork.IUnionofwork;

@RestController
@RequestMapping("/projetos")
@LogExecution(includeParameters = true, includeResult = false) // Log automático para toda a classe
@PreAuthorize("hasRole('USER')")
public class ProjetoController {
    @Autowired
    private IUnionofwork unionofwork;

    @GetMapping
    @LogExecution(includeParameters = false, includeResult = true) // Log específico para este método
    public CompletableFuture<ResponseEntity<Iterable<ProjetoDTO>>> getAllProjetosAsync() {
        CustomLogger.logInfo("Iniciando busca de todos os projetos");
        
        var projetosFuture = unionofwork.GetProjetoRepository().findAllAsync()
                .thenApply(projetos -> {
                    long count = StreamSupport.stream(projetos.spliterator(), false).count();
                    CustomLogger.logInfo("Convertendo %d projetos para DTO", count);
                    
                    return StreamSupport.stream(projetos.spliterator(), false)
                            .map(projeto -> ProjetoExtensao.ProjetoToDTO(projeto))
                            .collect(Collectors.toList());
                }).exceptionally(ex -> {
                    CustomLogger.logError("Erro ao buscar projetos: %s", ex.getMessage());
                    return null;
                }); 
        return projetosFuture.thenApply(ResponseEntity::ok);
    }

    // @GetMapping
    // public CompletableFuture<Iterable<Projeto>> getAllProjetosAsync() {
    // return unionofwork.GetProjetoRepository().findAllAsync();
    // }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProjetoDTO>> getProjetoByIdAsync(@PathVariable Long id) {
        return unionofwork.GetProjetoRepository().findByIdAsync(id)
                .thenApply(projeto -> ProjetoExtensao.ProjetoToDTO(projeto))
                .thenApply(ResponseEntity::ok);
    }

    // @GetMapping("/{id}")
    // public CompletableFuture<Projeto> getProjetoByIdAsync(@PathVariable Long id)
    // {
    // return unionofwork.GetProjetoRepository().findByIdAsync(id);
    // }

    @PostMapping
    public CompletableFuture<ResponseEntity<ProjetoDTO>> addProjetoAsync(@RequestBody ProjetoDTO projetoDTO) {
        Projeto projeto = ProjetoExtensao.DTOtoProjeto(projetoDTO);
        return unionofwork.GetProjetoRepository().saveAsync(projeto)
                .thenApply(projetoSalvo -> ProjetoExtensao.ProjetoToDTO(projetoSalvo))
                .thenApply(ResponseEntity::ok);
    }

    // @PostMapping
    // public CompletableFuture<Projeto> addProjetoAsync(@RequestBody Projeto
    // projeto) {
    // return unionofwork.GetProjetoRepository().saveAsync(projeto);
    // }


    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProjetoDTO>> updateProjetoAsync(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        Projeto projeto = ProjetoExtensao.DTOtoProjeto(projetoDTO);
        projeto.setId(id);
        return unionofwork.GetProjetoRepository().saveAsync(projeto)
                .thenApply(projetoAtualizado -> ProjetoExtensao.ProjetoToDTO(projetoAtualizado))
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteProjetoAsync(@PathVariable Long id) {
        return unionofwork.GetProjetoRepository().deleteByIdAsync(id)
                .thenApply(deleted -> ResponseEntity.noContent().build());
    }

    @GetMapping("/pagination")
    public CompletableFuture<ResponseEntity<PagedList<ProjetoDTO>>> paginateProjetosAsync(@RequestParam int pageNumber,
            @RequestParam int pageSize) {

         var pagedList = unionofwork.GetProjetoRepository().paginateAsync(pageNumber, pageSize);
         return pagedList.thenApply(list -> {
             var dtoList = list.stream()
                     .map(ProjetoExtensao::ProjetoToDTO)
                     .collect(Collectors.toList());
             return new PagedList<ProjetoDTO>(dtoList, list.getCurrentPage(), list.getPageSize(), pageSize);
         }).thenApply(ResponseEntity::ok);
    }

    @GetMapping("/filter")
    public CompletableFuture<ResponseEntity<Iterable<ProjetoDTO>>> searchProjetosAsync(
            @RequestParam String campo, // nome, descricao, etc.
            @RequestParam String valor) { // texto a buscar

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

        var projetos =  unionofwork.GetProjetoRepository().searchAsync(predicate);
        return projetos.thenApply(list -> StreamSupport.stream(list.spliterator(), false)
                .map(ProjetoExtensao::ProjetoToDTO)
                .collect(Collectors.toList())).thenApply(ResponseEntity::ok);
    }

}
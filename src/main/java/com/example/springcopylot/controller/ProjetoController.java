package com.example.springcopylot.controller;

import com.example.springcopylot.model.Projeto;
import com.example.springcopylot.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private com.example.springcopylot.service.IProjetoService projetoService;

    @GetMapping
    public List<Projeto> getAllProjetos() {
        return projetoService.getAllProjetos();
    }

    @GetMapping("/{id}")
    public Optional<Projeto> getProjetoById(@PathVariable Long id) {
        return projetoService.getProjetoById(id);
    }

    @PostMapping
    public Projeto addProjeto(@RequestBody Projeto projeto) {
        return projetoService.addProjeto(projeto);
    }

    @PutMapping("/{id}")
    public Projeto updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.updateProjeto(id, projeto);
    }

    @DeleteMapping("/{id}")
    public void deleteProjeto(@PathVariable Long id) {
        projetoService.deleteProjeto(id);
    }
}

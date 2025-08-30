package com.example.springcopylot.service;

import com.example.springcopylot.model.Projeto;
import java.util.List;
import java.util.Optional;

public interface IProjetoService {
    List<Projeto> getAllProjetos();
    Optional<Projeto> getProjetoById(Long id);
    Projeto addProjeto(Projeto projeto);
    Projeto updateProjeto(Long id, Projeto projeto);
    void deleteProjeto(Long id);
}

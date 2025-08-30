package com.example.springcopylot.service;

import com.example.springcopylot.model.Projeto;
import com.example.springcopylot.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService implements IProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> getProjetoById(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto addProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto updateProjeto(Long id, Projeto projeto) {
        projeto.setId(id);
        return projetoRepository.save(projeto);
    }

    public void deleteProjeto(Long id) {
        projetoRepository.deleteById(id);
    }
}

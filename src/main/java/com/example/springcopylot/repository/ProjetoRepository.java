package com.example.springcopylot.repository;

import com.example.springcopylot.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Métodos customizados podem ser adicionados aqui
}

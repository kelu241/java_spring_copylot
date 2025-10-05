package com.example.springcopylot.repository;

import com.example.springcopylot.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoJpaRepository extends JpaRepository<Projeto, Long> {
    // Spring Data cria automaticamente a implementação
}
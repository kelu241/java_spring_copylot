package com.example.springcopylot.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springcopylot.model.Projeto;



@Repository
public class ProjetoRepository extends GenericRepository<Projeto> implements IProjetoRepository {

    @Autowired
    private ProjetoJpaRepository projetoJpaRepository;

    @Override
    protected JpaRepository<Projeto, Long> getRepository() {
        return projetoJpaRepository;
    }

}


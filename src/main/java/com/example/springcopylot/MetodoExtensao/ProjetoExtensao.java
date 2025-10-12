package com.example.springcopylot.MetodoExtensao;

import com.example.springcopylot.DTO.*;

import com.example.springcopylot.model.Projeto;

public  class ProjetoExtensao {

    public static Projeto DTOtoProjeto(ProjetoDTO dto) {
        return new Projeto(dto.id(), dto.nome(), dto.idade());
    }


    public static ProjetoDTO ProjetoToDTO(Projeto projeto) {
        return new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getIdade());
    }


    }

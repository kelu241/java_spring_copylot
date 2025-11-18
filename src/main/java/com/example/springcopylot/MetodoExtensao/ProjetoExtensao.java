package com.example.springcopylot.MetodoExtensao;

import com.example.springcopylot.DTO.*;

import com.example.springcopylot.model.Projeto;

public  class ProjetoExtensao {

    public static Projeto DTOtoProjeto(ProjetoDTO dto) {
        return new Projeto(dto.id(), dto.nome(), dto.descricao(), new java.math.BigDecimal(dto.orcamento()), dto.status(),
                java.sql.Date.valueOf(dto.dataInicio()), java.sql.Date.valueOf(dto.dataFim()));
    }


    public static ProjetoDTO ProjetoToDTO(Projeto projeto) {
        return new ProjetoDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), projeto.getOrcamento().toString(), projeto.getStatus(),
                projeto.getDataInicio().toString(), projeto.getDataFim().toString());
    }


    }

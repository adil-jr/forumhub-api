package io.github.adil_jr.forumhub_api.model.dto;

import jakarta.validation.constraints.Size;

public record DadosAtualizacaoTopico(
        @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
        String titulo,

        @Size(min = 10, message = "Mensagem deve ter pelo menos 10 caracteres")
        String mensagem,

        String status
) {}
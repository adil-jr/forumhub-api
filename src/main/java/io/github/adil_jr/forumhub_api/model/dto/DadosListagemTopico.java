package io.github.adil_jr.forumhub_api.model.dto;

import io.github.adil_jr.forumhub_api.model.entity.Topico;
import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String status,
        LocalDateTime dataCriacao
) {

    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getStatus(), topico.getDataCriacao());
    }
}
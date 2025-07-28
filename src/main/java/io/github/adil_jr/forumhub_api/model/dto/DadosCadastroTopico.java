package io.github.adil_jr.forumhub_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(
        @NotBlank(message = "Título é obrigatório")
        @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        @Size(min = 10, message = "Mensagem deve ter pelo menos 10 caracteres")
        String mensagem,

        @NotNull(message = "ID do Autor é obrigatório")
        Long idAutor,

        @NotNull(message = "ID do Curso é obrigatório")
        Long idCurso
) {}
package io.github.adil_jr.forumhub_api.repository;

import io.github.adil_jr.forumhub_api.model.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}
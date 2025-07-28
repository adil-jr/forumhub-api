package io.github.adil_jr.forumhub_api.service;

import io.github.adil_jr.forumhub_api.exception.ValidacaoException;
import io.github.adil_jr.forumhub_api.model.dto.DadosAtualizacaoTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosCadastroTopico;
import io.github.adil_jr.forumhub_api.model.entity.Curso;
import io.github.adil_jr.forumhub_api.model.entity.Topico;
import io.github.adil_jr.forumhub_api.model.entity.Usuario;
import io.github.adil_jr.forumhub_api.repository.CursoRepository;
import io.github.adil_jr.forumhub_api.repository.TopicoRepository;
import io.github.adil_jr.forumhub_api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Topico criar(DadosCadastroTopico dados) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem.");
        }

        Usuario autor = usuarioRepository.findById(dados.idAutor())
                .orElseThrow(() -> new ValidacaoException("Autor não encontrado."));
        Curso curso = cursoRepository.findById(dados.idCurso())
                .orElseThrow(() -> new ValidacaoException("Curso não encontrado."));

        Topico topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    public Page<Topico> listar(Pageable pageable, String cursoNome) {
        if (cursoNome != null && !cursoNome.isEmpty()) {
            return topicoRepository.findByCursoNome(cursoNome, pageable);
        }
        return topicoRepository.findAll(pageable);
    }

    public Topico detalhar(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado."));
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados) {
        Topico topico = detalhar(id);

        if (dados.titulo() != null && !dados.titulo().isBlank()) {
            topico.setTitulo(dados.titulo());
        }
        if (dados.mensagem() != null && !dados.mensagem().isBlank()) {
            topico.setMensagem(dados.mensagem());
        }
        if (dados.status() != null && !dados.status().isBlank()) {
            topico.setStatus(dados.status());
        }

        return topico;
    }

    @Transactional
    public void deletar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico não encontrado.");
        }
        topicoRepository.deleteById(id);
    }
}
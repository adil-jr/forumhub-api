package io.github.adil_jr.forumhub_api.controller;

import io.github.adil_jr.forumhub_api.model.dto.DadosAtualizacaoTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosCadastroTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosDetalhamentoTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosListagemTopico;
import io.github.adil_jr.forumhub_api.model.entity.Topico;
import io.github.adil_jr.forumhub_api.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> criar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder
    ) {
        Topico topico = topicoService.criar(dados);
        DadosDetalhamentoTopico dadosDetalhamento = new DadosDetalhamentoTopico(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(dadosDetalhamento);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao,
            @RequestParam(required = false) String cursoNome
    ) {
        Page<Topico> paginaDeTopicos = topicoService.listar(paginacao, cursoNome);
        Page<DadosListagemTopico> paginaDeDtos = paginaDeTopicos.map(DadosListagemTopico::new);
        return ResponseEntity.ok(paginaDeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Topico topico = topicoService.detalhar(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados
    ) {
        Topico topicoAtualizado = topicoService.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
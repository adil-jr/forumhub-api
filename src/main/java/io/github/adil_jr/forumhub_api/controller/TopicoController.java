package io.github.adil_jr.forumhub_api.controller;

import io.github.adil_jr.forumhub_api.model.dto.DadosAtualizacaoTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosCadastroTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosDetalhamentoTopico;
import io.github.adil_jr.forumhub_api.model.dto.DadosListagemTopico;
import io.github.adil_jr.forumhub_api.model.entity.Topico;
import io.github.adil_jr.forumhub_api.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Operações relacionadas a tópicos do fórum")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Operation(summary = "Criar novo tópico", description = "Cria um novo tópico no fórum e requer autenticação.")
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
    @Operation(summary = "Listar todos os tópicos", description = "Retorna uma lista paginada de todos os tópicos.")
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao,
            @RequestParam(required = false) String cursoNome
    ) {
        Page<Topico> paginaDeTopicos = topicoService.listar(paginacao, cursoNome);
        Page<DadosListagemTopico> paginaDeDtos = paginaDeTopicos.map(DadosListagemTopico::new);
        return ResponseEntity.ok(paginaDeDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar tópico por ID", description = "Busca e retorna os detalhes de um tópico específico.")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Topico topico = topicoService.detalhar(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tópico por ID", description = "Atualiza os dados de um tópico existente com base no seu ID.")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados
    ) {
        Topico topicoAtualizado = topicoService.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tópico por ID", description = "Exclui um tópico do banco de dados com base no seu ID. Esta operação não pode ser desfeita.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
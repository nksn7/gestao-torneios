package br.com.gestao_torneios.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_torneios.api.model.Jogador;
import br.com.gestao_torneios.api.services.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {
    
    @Autowired
    JogadorService jogadorService;

    @Operation(summary = "Cadastra o jogador", method = "POST")
     @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jogador cadastrado com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    })
    @PostMapping
    public ResponseEntity<Jogador> cadastrarJogador(@RequestBody Jogador jogador) {
        try {
            Jogador jogadorSalvo = jogadorService.cadastrarJogador(jogador);
            return ResponseEntity.status(201).body(jogadorSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Lista todos os jogadores", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogadores listados com Sucesso"),
        @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    })
    @GetMapping
    public ResponseEntity<List<Jogador>> listarTodos() {
        List<Jogador> jogadores = jogadorService.listarTodos();
        return ResponseEntity.ok(jogadores);

    }

    @Operation(summary = "Lista o jogador por ID", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogador listado com Sucesso"),
        @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@PathVariable Integer id) {
        Jogador jogador = jogadorService.buscarPorId(id);
        return ResponseEntity.ok(jogador);

    }

    @Operation(summary = "Lista os jogadores de um time", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogadores do time listados com Sucesso"),
        @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    })
    @GetMapping("/time/{idTime}")
    public ResponseEntity<List<Jogador>> listarPorTime(@PathVariable Integer idTime) {
        List<Jogador> jogadores = jogadorService.listarPorTime(idTime);
        return ResponseEntity.ok(jogadores);

    }

    @Operation(summary = "Atualiza o jogador", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogador atualizado com Sucesso"),
        @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizarJogador(@PathVariable Integer id, @RequestBody Jogador jogador) {
        Jogador jogadorAtualizado = jogadorService.atualizarJogador(id, jogador);
        return ResponseEntity.ok(jogadorAtualizado);

    }

    @Operation(summary = "Deleta o jogador", method = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Jogador deletado com Sucesso"),
        @ApiResponse(responseCode = "404", description = "Jogador não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirJogador(@PathVariable Integer id) {
        try {
            jogadorService.excluirJogador(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }

    }
}

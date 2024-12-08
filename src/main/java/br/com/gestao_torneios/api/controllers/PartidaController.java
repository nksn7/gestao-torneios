package br.com.gestao_torneios.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_torneios.api.model.Partida;
import br.com.gestao_torneios.api.services.PartidaService;

@RestController
@RequestMapping("/partidas")
public class PartidaController {
    
    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<Partida> cadastrarPartida(@RequestBody Partida partida) {
        Partida novaPartida = partidaService.cadastrarPartida(partida);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPartida);

    }

    @GetMapping
    public ResponseEntity<List<Partida>> listarPartidas() {
        List<Partida> partidas = partidaService.listarPartidas();
        return ResponseEntity.ok(partidas);

    }

    @GetMapping("/torneio/{idTorneio}")
    public ResponseEntity<List<Partida>> listarPartidasPorTorneio(@PathVariable Integer idTorneio) {
        List<Partida> partidas = partidaService.listarPartidasPorTorneio(idTorneio);
        return ResponseEntity.ok(partidas);

    }

    @PatchMapping("/{idPartida}")
    public ResponseEntity<Partida> atualizarPartidaParcial(@PathVariable Integer idPartida, @RequestBody Partida novaPartida) {
        Partida partidaAtualizada = partidaService.atualizarPartidaParcial(idPartida, novaPartida);
        return ResponseEntity.ok(partidaAtualizada);

    }

    @DeleteMapping("/{idPartida}")
    public ResponseEntity<Void> deletarPartida(@PathVariable Integer idPartida) {
        partidaService.deletarPartida(idPartida);
        return ResponseEntity.noContent().build();
    }
}

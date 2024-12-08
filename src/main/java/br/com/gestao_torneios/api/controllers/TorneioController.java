package br.com.gestao_torneios.api.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_torneios.api.model.Torneio;
import br.com.gestao_torneios.api.services.TorneioService;

@RestController
@RequestMapping("/torneios")
public class TorneioController {
    
    @Autowired
    private TorneioService torneioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Torneio cadastraTorneio(@RequestBody Torneio torneio) {
        return torneioService.cadastrarTorneio(torneio);
    }

    @GetMapping
    public List<Torneio> listarTodosTorneios() {
        return torneioService.listarTodosTorneios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneio> buscarTorneioPorId(@PathVariable Integer id) {
        return torneioService.buscarTorneioPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Torneio> atualizarTorneio(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        try {
            String nome = updates.containsKey("nome") ? (String) updates.get("nome") : null;
            LocalDate dataInicio = updates.containsKey("dataInicio") ? LocalDate.parse((String) updates.get("dataInicio")) : null;
            LocalDate dataFim = updates.containsKey("dataFim") ? LocalDate.parse((String) updates.get("dataFim")) : null;

            Torneio torneioAtualizado = torneioService.atualizaTorneio(id, nome, dataInicio, dataFim);
            return ResponseEntity.ok(torneioAtualizado);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTorneio(@PathVariable Integer id) {
        try {
            torneioService.excluirTorneio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

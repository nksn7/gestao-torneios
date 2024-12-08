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

import br.com.gestao_torneios.api.model.Time;
import br.com.gestao_torneios.api.services.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {
    
    @Autowired
    private TimeService timeService;

    @GetMapping
    public List<Time> listarTimes() {
        return timeService.listarTodosTimes();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Time cadastrarTime(@RequestBody Time time) {
        return timeService.salvarTime(time);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Time> buscarTimePorId(@PathVariable Integer id) {
        return timeService.buscarTimePorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Time> atualizarTimeParcial(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        try {
            String nome = updates.containsKey("nome") ? (String) updates.get("nome") : null;
            LocalDate dataFundacao = updates.containsKey("dataFundacao") ? LocalDate.parse((String) updates.get("dataFundacao")) : null;

            Time timeAtualizado = timeService.atualizarTimeParcial(id, nome, dataFundacao);
            return ResponseEntity.ok(timeAtualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTime(@PathVariable Integer id) {
        try {
            timeService.excluirTime(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }

    }
}

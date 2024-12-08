package br.com.gestao_torneios.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_torneios.api.model.Resultado;
import br.com.gestao_torneios.api.services.ResultadoService;

@RestController
@RequestMapping("/resultados")
public class ResultadoController {
    
    @Autowired
    private ResultadoService resultadoService;
    
    @PostMapping
    public ResponseEntity<Resultado> cadastrarResultado(@RequestBody Resultado resultado) {
        Resultado novResultado = resultadoService.cadastrarResultado(resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(novResultado);

    }

    @GetMapping
    public ResponseEntity<List<Resultado>> listarTodosResultados() {
        List<Resultado> resultados = resultadoService.listarTodosResultados();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{idResultado}")
    public ResponseEntity<Resultado> buscarResultadoPorId(@PathVariable Integer idResultado) {
        Resultado resultado = resultadoService.buscarResultadoPorId(idResultado);
        return ResponseEntity.ok(resultado); 
    }

    @PutMapping("/{idResultado}")
    public ResponseEntity<Resultado> atualizarResultado(@PathVariable Integer idResultado, @RequestBody Resultado resultadoAtualizado) {
        Resultado resultado = resultadoService.atualizarResultado(idResultado, resultadoAtualizado);
        return ResponseEntity.ok(resultado); 

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResultado(@PathVariable Integer id) {
        resultadoService.deletarResultado(id);
        return ResponseEntity.noContent().build();
    }
}

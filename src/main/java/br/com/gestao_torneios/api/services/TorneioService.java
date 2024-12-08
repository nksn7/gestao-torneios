package br.com.gestao_torneios.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao_torneios.api.model.Torneio;
import br.com.gestao_torneios.api.repositories.TorneioRepository;

@Service
public class TorneioService {
    
    @Autowired
    private TorneioRepository torneioRepository;

    public Torneio cadastrarTorneio(Torneio torneio) {
        return torneioRepository.save(torneio);

    }

    public List<Torneio> listarTodosTorneios() {
        return torneioRepository.findAll();

    }

    public Optional<Torneio> buscarTorneioPorId(Integer id) {
        return torneioRepository.findById(id);
    }

    public Torneio atualizaTorneio(Integer id, String nome, LocalDate dataInicio, LocalDate dataFim) {
        Torneio torneioExistente = torneioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Torneio com ID " + " não encontrado!"));

        if (nome != null) {
            torneioExistente.setNome(nome);
        }
        if (dataInicio != null) {
            torneioExistente.setDataInicio(dataInicio);
        }
        if (dataFim != null) {
            torneioExistente.setDataFim(dataFim);
        }

        return torneioRepository.save(torneioExistente);
    }

    public void excluirTorneio(Integer id) {
        if (!torneioRepository.existsById(id)) {
            throw new RuntimeException("Torneio com ID " + id + " não encontrado");
        } else {
            torneioRepository.deleteById(id);
        }
    }
}

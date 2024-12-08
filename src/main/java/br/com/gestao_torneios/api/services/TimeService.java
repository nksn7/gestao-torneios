package br.com.gestao_torneios.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao_torneios.api.model.Time;
import br.com.gestao_torneios.api.repositories.TimeRepository;

@Service
public class TimeService {
    
    @Autowired
    private TimeRepository timeRepository;

    public List<Time> listarTodosTimes() {
        return timeRepository.findAll();

    }

    public Time salvarTime(Time time) {
        return timeRepository.save(time);
    }

    public Optional<Time> buscarTimePorId(Integer id) {
        return timeRepository.findById(id);
    }

    public Time atualizarTimeParcial(Integer id, String nome, LocalDate dataFundacao) {
        Time timeExistente = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time com ID " + id + " não encontrado"));

        if (nome != null) {
            timeExistente.setNome(nome);
        }
        if (dataFundacao != null) {
            timeExistente.setDataFundacao(dataFundacao);
        }

        return timeRepository.save(timeExistente);
    }

    public void excluirTime(Integer id) {
        if (!timeRepository.existsById(id)) {
            throw new RuntimeException("Time com ID " + id + " não encontrado");
        } else {
            timeRepository.deleteById(id);
        }
        
    }
}

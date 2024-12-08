package br.com.gestao_torneios.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao_torneios.api.model.Jogador;
import br.com.gestao_torneios.api.model.Time;
import br.com.gestao_torneios.api.repositories.JogadorRepository;
import br.com.gestao_torneios.api.repositories.TimeRepository;

@Service
public class JogadorService {
    
    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TimeRepository timeRepository;

    public Jogador cadastrarJogador(Jogador jogador) {
        if (jogador.getTime() != null && jogador.getTime().getIdTime() != null) {
            Time timeCompleto = timeRepository.findById(jogador.getTime().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time com ID " + jogador.getTime().getIdTime() + " não encontrado"));

            jogador.setTime(timeCompleto); 
        }

        return jogadorRepository.save(jogador);
        
    }

    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();

    }

    public Jogador buscarPorId(Integer id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador com ID " + id + " não encontrado."));

    }

    public List<Jogador> listarPorTime(Integer idTime) {
        return jogadorRepository.findByTimeIdTime(idTime);

    }

    public Jogador atualizarJogador(Integer id, Jogador jogadorAtualizado) {
        Jogador jogadorExistente = jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador com ID " + id + " não encontrado."));

        if (jogadorAtualizado.getNome() != null) {
            jogadorExistente.setNome(jogadorAtualizado.getNome());
        }
        if (jogadorAtualizado.getNickname() != null) {
            jogadorExistente.setNickname(jogadorAtualizado.getNickname());
        }
        if (jogadorAtualizado.getPosicao() != null) {
            jogadorExistente.setPosicao(jogadorAtualizado.getPosicao());
        }

        if (jogadorAtualizado.getTime() != null && jogadorAtualizado.getTime().getIdTime() != null) {
            Time time = timeRepository.findById(jogadorAtualizado.getTime().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time com ID " + jogadorAtualizado.getTime().getIdTime() + " não encontrado."));
            if (jogadorAtualizado.getTime() != null) {
                jogadorExistente.setTime(time);
            }
        }

        return jogadorRepository.save(jogadorExistente);
    }

    public void excluirJogador(Integer id) {
        if (!jogadorRepository.existsById(id)) {
            throw new RuntimeException("Jogador com ID " + id + " não encontrado");
        } else {
            jogadorRepository.deleteById(id);
        }
        
    }
}

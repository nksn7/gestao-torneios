package br.com.gestao_torneios.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao_torneios.api.model.Partida;
import br.com.gestao_torneios.api.model.Time;
import br.com.gestao_torneios.api.model.Torneio;
import br.com.gestao_torneios.api.repositories.PartidaRepository;
import br.com.gestao_torneios.api.repositories.TimeRepository;
import br.com.gestao_torneios.api.repositories.TorneioRepository;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private TorneioRepository torneioRepository;

    public Partida cadastrarPartida(Partida partida) {

        Torneio torneio = torneioRepository.findById(partida.getTorneio().getIdTorneio())
                .orElseThrow(() -> new RuntimeException("Torneio com ID " + partida.getTorneio().getIdTorneio() + " não encontrado."));

        if (partida.getDataPartida().isBefore(torneio.getDataInicio()) || 
            partida.getDataPartida().isAfter(torneio.getDataFim())) {
            throw new RuntimeException("A data da partida deve estar dentro do intervalo do torneio.");
        }

        partida.setTorneio(torneio);

        Time time1 = timeRepository.findById(partida.getTime1().getIdTime())
                .orElseThrow(() -> new RuntimeException("Time 1 com ID " + partida.getTime1().getIdTime() + " não encontrado."));      

        Time time2 = timeRepository.findById(partida.getTime2().getIdTime())
                .orElseThrow(() -> new RuntimeException("Time 2 com ID " + partida.getTime2().getIdTime() + " não encontrado."));
        
        if (partida.getTime1().getIdTime().equals(partida.getTime2().getIdTime())) {
            throw new RuntimeException("Os times da partida não podem ser os mesmos.");
        }

        partida.setTime1(time1);
        partida.setTime2(time2);

        return partidaRepository.save(partida);

    }

    public List<Partida> listarPartidas() {
        return partidaRepository.findAll();
        
    }

    public List<Partida> listarPartidasPorTorneio(Integer idTorneio) {
        return partidaRepository.findByTorneioIdTorneio(idTorneio);
    }

    public Partida atualizarPartidaParcial(Integer idPartida, Partida novaPartida) {

        Partida partidaExistente = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        if (novaPartida.getTorneio() != null) {
            Torneio torneio = torneioRepository.findById(novaPartida.getTorneio().getIdTorneio())
                    .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

            if (novaPartida.getDataPartida() == null) {
                if (partidaExistente.getDataPartida().isBefore(torneio.getDataInicio()) || 
                partidaExistente.getDataPartida().isAfter(torneio.getDataFim())) {
                
                    throw new RuntimeException("A data da partida deve estar dentro do intervalo do torneio.");

                }
            }

            partidaExistente.setTorneio(torneio);
        }

        if (novaPartida.getTime1() != null) {
            Time time1 = timeRepository.findById(novaPartida.getTime1().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time 1 não encontrado"));
            partidaExistente.setTime1(time1);
        }

        if (novaPartida.getTime2() != null) {
            Time time2 = timeRepository.findById(novaPartida.getTime2().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time 2 não encontrado"));
            partidaExistente.setTime2(time2);
        }

        if (novaPartida.getDataPartida() != null) {
            if (partidaExistente.getTorneio() != null) {
                Torneio torneio = partidaExistente.getTorneio();
                if (novaPartida.getDataPartida().isBefore(torneio.getDataInicio()) ||
                    novaPartida.getDataPartida().isAfter(torneio.getDataFim())) {
                    throw new RuntimeException("A data da partida deve estar dentro do intervalo do torneio");
                }
            }
            partidaExistente.setDataPartida(novaPartida.getDataPartida());
        }

        return partidaRepository.save(partidaExistente);
    }

    public void deletarPartida(Integer idPartida) {
        Partida partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        partidaRepository.delete(partida);
    }
}

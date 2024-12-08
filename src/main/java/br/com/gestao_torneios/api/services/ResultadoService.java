package br.com.gestao_torneios.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestao_torneios.api.model.Partida;
import br.com.gestao_torneios.api.model.Resultado;
import br.com.gestao_torneios.api.model.Time;
import br.com.gestao_torneios.api.repositories.PartidaRepository;
import br.com.gestao_torneios.api.repositories.ResultadoRepository;
import br.com.gestao_torneios.api.repositories.TimeRepository;

@Service
public class ResultadoService {
    
    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TimeRepository timeRepository;

    public Resultado cadastrarResultado(Resultado resultado) {

        Partida partida = partidaRepository.findById(resultado.getPartida().getIdPartida())
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        if (resultadoRepository.existsByPartida(partida)) {
            throw new RuntimeException("Resultado já cadastrado para esta partida");
        }

        Time vencedor = timeRepository.findById(resultado.getTimeVencedor().getIdTime())
                .orElseThrow(() -> new RuntimeException("Time vencedor não encontrado"));

        Time perdedor = timeRepository.findById(resultado.getTimePerdedor().getIdTime())
                .orElseThrow(() -> new RuntimeException("Time perdedor não encontrado"));

        if (!(partida.getTime1().equals(vencedor) || partida.getTime2().equals(vencedor)) ||
            !(partida.getTime1().equals(perdedor) || partida.getTime2().equals(perdedor))) {
            throw new RuntimeException("Os times não correspondem aos participantes da partida");
        }

        if (vencedor.equals(perdedor)) {
            throw new RuntimeException("O time vencedor e o perdedor não podem ser o mesmo");
        }

        vencedor.setVitorias(vencedor.getVitorias() + 1);
        perdedor.setDerrotas(perdedor.getDerrotas() + 1);

        timeRepository.save(vencedor);
        timeRepository.save(perdedor);

        resultado.setPartida(partida);
        resultado.setTimeVencedor(vencedor);
        resultado.setTimePerdedor(perdedor);

        return resultadoRepository.save(resultado);
    }

    public List<Resultado> listarTodosResultados() {
        return resultadoRepository.findAll();
    }

    public Resultado buscarResultadoPorId(Integer idResultado) {
        return resultadoRepository.findById(idResultado)
                .orElseThrow(() -> new RuntimeException("Resultado não encontrado com o ID: " + idResultado));
    }

    public Resultado atualizarResultado(Integer idResultado, Resultado resultadoAtualizado) {
        
        Resultado resultadoExistente = resultadoRepository.findById(idResultado)
                .orElseThrow(() -> new RuntimeException("Resultado não encontrado com o ID: " + idResultado));

        Partida partida = resultadoExistente.getPartida();
        if (partida == null) {
            throw new RuntimeException("A partida associada ao resultado não foi encontrada.");
        }

        if (resultadoAtualizado.getTimeVencedor() != null) {
            Time novoVencedor = timeRepository.findById(resultadoAtualizado.getTimeVencedor().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time vencedor não encontrado com o ID: " + resultadoAtualizado.getTimeVencedor().getIdTime()));

            if (!novoVencedor.equals(partida.getTime1()) && !novoVencedor.equals(partida.getTime2())) {
                throw new RuntimeException("O time vencedor deve ser um dos times da partida.");
            }

            resultadoExistente.setTimeVencedor(novoVencedor);
            novoVencedor.setVitorias(novoVencedor.getVitorias() + 1);
            novoVencedor.setDerrotas(novoVencedor.getDerrotas() - 1);

            if (novoVencedor.equals(partida.getTime1())) {
                resultadoExistente.setTimePerdedor(partida.getTime2());
                partida.getTime2().setDerrotas(partida.getTime2().getDerrotas() + 1);
                partida.getTime2().setVitorias(partida.getTime2().getVitorias() - 1);
            } else {
                resultadoExistente.setTimePerdedor(partida.getTime1());
                partida.getTime1().setDerrotas(partida.getTime1().getDerrotas() + 1);
                partida.getTime1().setVitorias(partida.getTime1().getVitorias() - 1);
            }
            
            timeRepository.save(novoVencedor);
            
        }

        if (resultadoAtualizado.getTimePerdedor() != null) {
            Time novoPerdedor = timeRepository.findById(resultadoAtualizado.getTimePerdedor().getIdTime())
                    .orElseThrow(() -> new RuntimeException("Time perdedor não encontrado com o ID: " + resultadoAtualizado.getTimePerdedor().getIdTime()));

            if (!novoPerdedor.equals(partida.getTime1()) && !novoPerdedor.equals(partida.getTime2())) {
                throw new RuntimeException("O time perdedor deve ser um dos times da partida.");
            }

            resultadoExistente.setTimePerdedor(novoPerdedor);
            novoPerdedor.setVitorias(novoPerdedor.getVitorias() + 1);
            novoPerdedor.setDerrotas(novoPerdedor.getDerrotas() - 1);

            if (novoPerdedor.equals(partida.getTime1())) {
                resultadoExistente.setTimeVencedor(partida.getTime2());
                partida.getTime2().setDerrotas(partida.getTime2().getDerrotas() + 1);
                partida.getTime2().setVitorias(partida.getTime2().getVitorias() - 1);
            } else {
                resultadoExistente.setTimeVencedor(partida.getTime1());
                partida.getTime1().setDerrotas(partida.getTime1().getDerrotas() + 1);
                partida.getTime1().setVitorias(partida.getTime1().getVitorias() - 1);
            }
        }

        if (resultadoAtualizado.getPartida() != null) {
           throw new RuntimeException("Não é possivel alterar a partida do resultado.");
        }

        return resultadoRepository.save(resultadoExistente);
    }

    public void deletarResultado(Integer idResultado) {
        
        Resultado resultadoExistente = resultadoRepository.findById(idResultado)
                .orElseThrow(() -> new RuntimeException("Resultado não encontrado com o ID: " + idResultado));

        Time timeVencedor = resultadoExistente.getTimeVencedor();
        Time timePerdedor = resultadoExistente.getTimePerdedor();

        if (timeVencedor != null) {
            timeVencedor.setVitorias(timeVencedor.getVitorias() - 1);
            timeRepository.save(timeVencedor);
        }

        if (timePerdedor != null) {
            timePerdedor.setDerrotas(timePerdedor.getDerrotas() - 1);
            timeRepository.save(timePerdedor);
        }

        resultadoRepository.deleteById(idResultado);
    }
}

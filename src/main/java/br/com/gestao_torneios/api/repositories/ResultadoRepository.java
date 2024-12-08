package br.com.gestao_torneios.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_torneios.api.model.Partida;
import br.com.gestao_torneios.api.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Integer>{
    boolean existsByPartida(Partida partida);
    
}   

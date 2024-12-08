package br.com.gestao_torneios.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_torneios.api.model.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer>{
    
    List<Partida> findByTorneioIdTorneio(Integer idTorneio);
}

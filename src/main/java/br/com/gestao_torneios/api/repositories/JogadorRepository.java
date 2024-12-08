package br.com.gestao_torneios.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestao_torneios.api.model.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer>{
    List<Jogador> findByTimeIdTime(Integer idTime);

}

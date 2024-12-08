package br.com.gestao_torneios.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_torneios.api.model.Torneio;

public interface TorneioRepository extends JpaRepository<Torneio, Integer>{
    
}

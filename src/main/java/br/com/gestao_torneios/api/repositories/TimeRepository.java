package br.com.gestao_torneios.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_torneios.api.model.Time;

public interface TimeRepository extends JpaRepository<Time, Integer>{
    
}

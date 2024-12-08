package br.com.gestao_torneios.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Torneio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTorneio;

    @Column(nullable = false, length = 150)
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Integer getIdTorneio() {
        return idTorneio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}

package br.com.gestao_torneios.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTime;

    @Column(nullable = false, length = 150)
    private String nome;
    private LocalDate dataFundacao;
    private Integer vitorias = 0;
    private Integer derrotas = 0;

    public Time() {}

    public Time(String nome, LocalDate dataFundacao) {
        this.nome = nome;
        this.dataFundacao = dataFundacao;
    }

    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime(Integer idTime) {
        this.idTime = idTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public Integer getVitorias() {
        return vitorias;
    }

    public void setVitorias(Integer vitorias) {
        this.vitorias = vitorias;
    }
    
    public Integer getDerrotas() {
        return derrotas;
    }
    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }
}

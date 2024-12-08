package br.com.gestao_torneios.api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Partida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPartida;

    @ManyToOne
    @JoinColumn(name = "id_torneio", nullable = false)
    private Torneio torneio;

    @ManyToOne
    @JoinColumn(name = "id_time_1", nullable = false)
    private Time time1;

    @ManyToOne
    @JoinColumn(name = "id_time_2", nullable = false)
    private Time time2;

    private LocalDate dataPartida;

    public Integer getIdPartida() {
        return idPartida;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }

    public Time getTime1() {
        return time1;
    }

    public void setTime1(Time time1) {
        this.time1 = time1;
    }

    public Time getTime2() {
        return time2;
    }

    public void setTime2(Time time2) {
        this.time2 = time2;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        this.dataPartida = dataPartida;
    }
}

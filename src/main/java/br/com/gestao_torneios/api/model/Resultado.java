package br.com.gestao_torneios.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Resultado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResultado;

    @OneToOne
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "id_time_vencedor", nullable = false)
    private Time timeVencedor;

    @ManyToOne
    @JoinColumn(name = "id_time_perdedor", nullable = false)
    private Time timePerdedor;

    public Integer getIdResultado() {
        return idResultado;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Time getTimeVencedor() {
        return timeVencedor;
    }

    public void setTimeVencedor(Time timeVencedor) {
        this.timeVencedor = timeVencedor;
    }

    public Time getTimePerdedor() {
        return timePerdedor;
    }

    public void setTimePerdedor(Time timePerdedor) {
        this.timePerdedor = timePerdedor;
    }
}

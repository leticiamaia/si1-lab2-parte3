package models;

import javax.persistence.*;

/**
 * Created by Leticia on 2/13/2015.
 */
@Entity(name="IndicadorDeProximoEp")
public abstract class IndicadorDeProximoEp {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
    protected Serie serie;

    protected abstract Episodio proximoEpisodio(int temporada, Serie serie);

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Long getId(){
        return id;

    }

    public void setId(Long id){
        this.id = id;
    }
}

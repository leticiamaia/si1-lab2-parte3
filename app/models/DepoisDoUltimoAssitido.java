package models;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Leticia on 2/13/2015.
 */
@Entity(name="DepoisDoUltimoAssitido")
public class DepoisDoUltimoAssitido extends IndicadorDeProximoEp{
    public DepoisDoUltimoAssitido(Serie seria){super.serie = serie;}
    public DepoisDoUltimoAssitido(){}

    @Override
    protected Episodio proximoEpisodio(int temporada, Serie serie) {
        List<Episodio> episodios = serie.getEpisodios(temporada);
        for (int i = episodios.size()-2; i >= 0; i--) {
            if (episodios.get(i).isAssistido()) {
                return episodios.get(i+1);
            }
        }
        return null;
    }
}

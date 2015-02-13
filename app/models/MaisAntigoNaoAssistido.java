package models;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Leticia on 2/13/2015.
 */
@Entity(name="MaisAntigoNaoAssistido")
public class MaisAntigoNaoAssistido extends IndicadorDeProximoEp{
    public MaisAntigoNaoAssistido(Serie serie) {super.serie = serie;}
    public MaisAntigoNaoAssistido() {}


    @Override
    protected Episodio proximoEpisodio(int temporada, Serie serie) {
        List<Episodio> episodios = serie.getEpisodios(temporada);
        for (int i = 0; i < episodios.size(); i++) {
            if (!episodios.get(i).isAssistido()) {
                return episodios.get(i);
            }
        }
        return null;
    }
}

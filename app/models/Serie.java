package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="serie")
public class Serie implements Comparable<Serie> {
	
	@Id
    @GeneratedValue
    private Long idSerie;

	private String nome;
	private boolean status;

	@OneToOne
	private IndicadorDeProximoEp indicador;

	@OneToMany(mappedBy = "serie")
	private List<Episodio> episodios=null;

	public Serie(String nome) {
		this.nome = nome;
		this.status = false;
		this.episodios = new ArrayList<Episodio>();
	}

	public Serie() {
	}

	public IndicadorDeProximoEp getIndicador() {
		return indicador;
	}

	public void setIndicador(IndicadorDeProximoEp indicador) {
		this.indicador = indicador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isAssistindo() {
		return status;
	}

	public void mudaStatus() {
		if(this.status) {this.status = false;}
		else {this.status = true;}
	}
	
	public boolean hasTemporadaAssistidaCompleta() {
		for (int i = 1; i <= getTotalDeTemporadas(); i++) {
			if(isTemporadaAssistidaCompleta(i)) {return true;}
		}
		return false;
	}
	
	public boolean hasTemporadaAssistidaIncompleta() {
		for (int i = 1; i <= getTotalDeTemporadas(); i++) {
			if(isTemporadaAssistidaIncompleta(i)) {return true;}
		}
		return false;
	}
	
	public boolean hasTemporadaNaoAssistida() {
		for (int i = 1; i <= getTotalDeTemporadas(); i++) {
			if(isTemporadaNaoAssistida(i)) {return true;}
		}
		return false;
	}
	public boolean isTemporadaAssistidaCompleta(int temporada) {
		List<Episodio> temp = getEpisodios(temporada);
		for (int i = 0; i < temp.size(); i++) {
			if(!temp.get(i).isAssistido()) {return false;}
		}
		return true;
	}
	
	public boolean isTemporadaAssistidaIncompleta(int temporada) {
		if(isTemporadaAssistidaCompleta(temporada)) {return false;}
		if(isTemporadaNaoAssistida(temporada)) {return false;}
		return true;
	}
	
	public boolean isTemporadaNaoAssistida(int temporada) {
		List<Episodio> temp = getEpisodios(temporada);
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).isAssistido()) {return false;}
		}
		return true;
	}
	
	public Episodio getProximoEpisodio(int temporada) {
		return indicador.proximoEpisodio(temporada, this);
	}
	
	public List<Integer> getTemporadas() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < this.episodios.size(); i++) {
			if(!result.contains(episodios.get(i).getTemporada())) {result.add(episodios.get(i).getTemporada());}
		}
		return result;
	}
	
	public int getTotalDeTemporadas() {
		return getTemporadas().size();
	}

	public void setEpisodios(List<Episodio> episodios) {
		this.episodios = episodios;
	}

	public List<Episodio> getEpisodios() {
		return this.episodios;
	}
	
	public List<Episodio> getEpisodios(int temporada) {
		List<Episodio> temp = new ArrayList<Episodio>();
		for (int i = 0; i < this.episodios.size(); i++) {
			if(episodios.get(i).getTemporada() == temporada) {temp.add(episodios.get(i));}
		}
		return temp;
	}

	public int getTotalDeEpisodios() {
		return this.episodios.size();
	}
	
	public int getTotalDeEpisodiosDaTemporada(int temporada) {
		return getEpisodios(temporada).size();
	}
		
	public int getTotalDeEpisodiosAssistidos() {
		int cont = 0;
		for (int i = 0; i < this.episodios.size(); i++) {
			if(episodios.get(i).isAssistido()) {cont++;}
		}
		return cont;
	}
	
	public int getTotalDeEpisodiosAssistir() {
		return this.episodios.size() - getTotalDeEpisodiosAssistidos();
	}
	
	public Episodio addEpisodio(String nome, int numero, int temporada) {
		Episodio temp = new Episodio(nome, numero, temporada, this);
		this.episodios.add(temp);
		return temp;
	}
	
	public void removeEpisodio(Episodio episodio) {
		this.episodios.remove(episodio);
	}
	
	public Long getId() {
		return idSerie;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Serie)) {
			return false;
		}
		Serie temp = (Serie) obj;
		return this.nome.equals(temp.getNome());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	
	@Override
	public int compareTo(Serie serie) {
		return this.nome.compareTo(serie.getNome());
	}
}

package it.polito.tdp.meteo.bean;

import it.polito.tdp.meteo.db.MeteoDAO;

public class SimpleCity {

	private String nome;
	private int costo;
	private int contaGG;
	
	public SimpleCity(String nome) {
		this.nome = nome;
	}
	
	public SimpleCity(String nome, int costo) {
		this.nome = nome;
		this.costo = costo;
	}

	public SimpleCity() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleCity other = (SimpleCity) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	public double getCosto(int mese, int i,String localita) {
		// TODO Auto-generated method stub
		MeteoDAO dao = new MeteoDAO();
		
		return dao.getUmidita(mese, i, localita);
	}

	public int getContaGG() {
		return contaGG;
	}

	public void setContaGG(int contaGG) {
		this.contaGG = contaGG;
	}
	
}

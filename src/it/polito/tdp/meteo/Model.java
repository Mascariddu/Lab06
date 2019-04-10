package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {
	
	SimpleCity exCitta= new SimpleCity();
	private double spesaMigliore;
	private List<SimpleCity> migliore;
	private List<Citta> citta = new ArrayList<Citta>();
	private List<String> nomicitta = new ArrayList<String>();
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	public Model() {

		MeteoDAO dao = new MeteoDAO();
		
		citta.addAll(dao.getCities());
		
	}

	public String getUmiditaMedia(int mese) {
		
		MeteoDAO dao = new MeteoDAO();
		String risultato="";
		
		List<String> localita = new ArrayList<String>();
		
		for(Rilevamento r: dao.getAllRilevamenti()) {
			
			if(!localita.contains(r.getLocalita()))
				localita.add(r.getLocalita());
		}
		
		for(String l : localita)
			risultato += l+": "+dao.getAvgRilevamentiLocalitaMese(mese, l)+"\n";
		
		return risultato;
	}

	public String trovaSequenza(int mese) {

		String result = "totti";
		spesaMigliore = 10000000000000000.0;
		migliore = new ArrayList<SimpleCity>();
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		
		cerca(parziale,0,mese);
		
		for(SimpleCity sc : migliore)
			result += sc.toString();
		
		return result;
	}
	
	public void cerca(List<SimpleCity> parziale, int L,int mese) {
		
		if(L==15) {
			
			if(this.controllaParziale(parziale)) {
				
				if(this.punteggioSoluzione(parziale, mese)< this.spesaMigliore)
					this.migliore= parziale;
					//System.out.println("§Piorcodododododod");
					this.spesaMigliore = this.punteggioSoluzione(parziale, mese);
			}
			//System.out.println("§Piorcodododododod");
			return;
		}
		
		//if(!this.controlloIntermedio(parziale, exCitta))
			//return;  
		
		for(int i = 0; i<3; i++) {
			
			System.out.println("§Piorcodododododod");
			exCitta = new SimpleCity(citta.get(i).getNome());
			parziale.add(new SimpleCity(citta.get(i).getNome()));
			if(this.controlloIntermedio(parziale, exCitta)) {
				cerca(parziale,L+1,mese);
				parziale.remove(new SimpleCity(citta.get(i).getNome()));
			}
				else return;
			
		}
		
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata, int mese) {

		MeteoDAO dao = new MeteoDAO();
		SimpleCity cittaVecchia = new SimpleCity();
		int i=1;
		
		double score = 0.0;
		
		for(SimpleCity sc : soluzioneCandidata) {
			
			if(!cittaVecchia.equals(sc))
				score+=100;
			score += dao.getUmidita(mese,i,sc.getNome());
			cittaVecchia = sc;
			i++;
			
		}
		
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		for(Citta c : citta) {
			
			for(SimpleCity sc : parziale) {
				
				if(sc.getNome().equals(c.getNome()))
					c.increaseCounter();
				
			}
			
			if(c.getCounter()==0 || c.getCounter()>6)
				return false;
		}
		
		return true;
	}
	
	private boolean controlloIntermedio(List<SimpleCity> parziale, SimpleCity exC) {

		int pernottamento = 0;
		
		for(SimpleCity sc: parziale) {
		
			if(exC.equals(sc))
				pernottamento++;
			else pernottamento =0;
			
			exC = sc;
			
		}
		
		return true;
	}
	
}

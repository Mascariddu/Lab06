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

		String result = "";
		for(Citta c: citta)
			System.out.println(c.getNome());
		spesaMigliore = 100000000.0;
		migliore = new ArrayList<SimpleCity>();
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		
		cerca(parziale,0,mese);
		
		System.out.println(migliore.toString());
		for(SimpleCity sc : migliore) {
			result += sc.toString()+"\n";
		}
		
		return result+ "Spesa: "+spesaMigliore;
	}
	
	public void cerca(List<SimpleCity> parziale, int L,int mese) {
		
		
		System.out.println("Livello: "+L);
		if(L== Model.NUMERO_GIORNI_TOTALI) {
			
			System.out.println("condizione terminale");
				
				if(this.punteggioSoluzione(parziale, mese)< this.spesaMigliore) {
					migliore.clear();
					migliore.addAll(parziale);
					System.out.println("aggiorna migliore");
					this.spesaMigliore = this.punteggioSoluzione(parziale, mese);
				}
				
			return;
		}
			
		for(Citta c: citta) {
			
					SimpleCity prova = new SimpleCity(c.getNome());
					
					if(this.controllaAggiunta(parziale,prova)) {
				
						parziale.add(prova);
						System.out.println("parziale corretto");
						for(SimpleCity sc: parziale)
							System.out.println(sc.getNome());
						cerca(parziale,L+1,mese);
						parziale.remove(parziale.size()-1);
						
					} else {
						System.out.println("parziale errato");
						for(SimpleCity sc: parziale)
							System.out.println(sc.getNome());
					}
			//for(SimpleCity sc: parziale)
				//System.out.println(sc.getNome());
			
				}
			}

	private boolean controllaAggiunta(List<SimpleCity> parziale, SimpleCity prova) {

		int conta = 0;

		for (SimpleCity precedente : parziale)

			if (precedente.equals(prova))

				conta++;

		if (conta >= NUMERO_GIORNI_CITTA_MAX)

			return false;



		// verifica giorni minimi

		if (parziale.size() == 0) // primo giorno

			return true;

		if (parziale.size() == 1 || parziale.size() == 2) { // secondo o terzo giorno: non posso cambiare

			return parziale.get(parziale.size() - 1).equals(prova);

		}

		if (parziale.get(parziale.size() - 1).equals(prova)) // giorni successivi, posso SEMPRE rimanere

			return true;

		// sto cambiando citta

		if (parziale.get(parziale.size() - 1).equals(parziale.get(parziale.size() - 2))

				&& parziale.get(parziale.size() - 2).equals(parziale.get(parziale.size() - 3)))

			return true;



		return false;
	
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata, int mese) {

		SimpleCity cittaVecchia = new SimpleCity();
		int i=1;
		
		double score = 0.0;
		
		for(SimpleCity sc : soluzioneCandidata) {
			
			if(!cittaVecchia.equals(sc))
				score+=100;
			score += sc.getCosto(mese,i,sc.getNome());
			cittaVecchia = sc;
			i++;
			
		}
		
		return score;
	}

}

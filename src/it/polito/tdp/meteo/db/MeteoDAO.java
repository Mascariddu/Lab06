package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE data LIKE ? AND localita=?";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try { 
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			if(mese <=9)
				st.setString(1, "2013-0"+mese+"%");
			else
				st.setString(1, "2013-"+mese+"%");
			
			st.setString(2,localita);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		
		 int somma=0;
		
		for(Rilevamento r: this.getAllRilevamentiLocalitaMese(mese, localita))
			somma+=r.getUmidita();
		
		return (double)somma/this.getAllRilevamentiLocalitaMese(mese, localita).size();
	}

	public double getUmidita(int mese, int gg, String localita) {
		// TODO Auto-generated method stub
		final String sql = "SELECT * FROM situazione WHERE data LIKE ? AND localita=?";

		int u=0;

		try { 
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			if(mese <=9 && gg <= 9)
				st.setString(1, "2013-0"+mese+"-0"+gg);
			else if(mese > 9 && gg<= 9)
					st.setString(1, "2013-"+mese+"-0"+gg);
				else if(mese <= 9 && gg>9)
					st.setString(1, "2013-0"+mese+"-"+gg);
					else
						st.setString(1, "2013-"+mese+"-"+gg);
		
			st.setString(2, localita);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				u=r.getUmidita();
			}

			conn.close();
			return u;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Citta> getCities() {
		// TODO Auto-generated method stub
		
		final String sql = "SELECT DISTINCT localita FROM situazione";
		List<Citta> cities = new ArrayList<Citta>();
		
		try{
			
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				cities.add(new Citta(rs.getString("localita"),this.getAllRilevamentiLocalita(rs.getString("localita"))));
				
			}
				
			return cities;
			
		} catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
	}
	
	public List<Rilevamento> getAllRilevamentiLocalita(String localita) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE localita=?";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try { 
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1,localita);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}

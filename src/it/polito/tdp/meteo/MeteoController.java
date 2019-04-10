package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {

	private Model modello = new Model();
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		
		txtResult.clear();
		txtResult.appendText(modello.trovaSequenza(boxMese.getValue()));

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {

		txtResult.clear();
		
		if(boxMese.getValue()!=null) {
			if(modello.getUmiditaMedia(boxMese.getValue())!= "")
				txtResult.appendText(modello.getUmiditaMedia(boxMese.getValue()));
			else txtResult.appendText("Nessun rilevamento!");
		}
		else txtResult.appendText("Scegli un mese!");
	}
	
	public void SetModel(Model model) {
		
		this.modello = model;

		for(int i=1;i<=12;i++) {
			
			boxMese.getItems().add(i);
			
		}
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
	}

}

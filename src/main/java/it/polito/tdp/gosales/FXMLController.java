package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<String> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	
    	String retailers = cmbRivenditore.getSelectionModel().getSelectedItem();
    	txtResult.appendText(this.model.calcolaConnessa(retailers));

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtArchi.clear();
    	txtVertici.clear();
    	txtResult.clear();
    	
    	String input = txtNProdotti.getText();
    	int m = 0;
    	if (input == "") {
    	 txtResult.setText("Scrivere un numero!");
    	 return;
    	 }
    	try {
    	 m = Integer.parseInt(input);

    	 } catch (NumberFormatException e) {
    	 e.printStackTrace();
    	 return;
    	}
    	
    	int anno = cmbAnno.getSelectionModel().getSelectedItem();
    	String country = cmbNazione.getSelectionModel().getSelectedItem();
    	
    	this.model.creaGrafo(country, anno, m);
    	
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI: " + this.model.numeroVertici() + "\n");
    	txtResult.appendText("#ARCHI: " + this.model.numeroArchi() + "\n");
    	txtVertici.appendText(this.model.riempiVertici());
    	txtArchi.appendText(this.model.riempiArchi());
    	cmbRivenditore.setDisable(false);
    	cmbRivenditore.getItems().addAll(this.model.listaRetailers());
    	btnAnalizzaComponente.setDisable(false);

    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";
        model = new Model();
        cmbAnno.getItems().addAll(this.model.listaAnni());
        cmbNazione.getItems().addAll(this.model.listaPaesi());
        
        
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}

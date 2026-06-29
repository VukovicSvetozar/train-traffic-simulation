package org.unibl.etf.zs.controller;

import java.io.File;
import java.util.ArrayList;

import org.unibl.etf.zs.istorija_kretanja.IstorijaKretanja;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.Serialization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrikazKretanja implements PathFile {

	ArrayList<IstorijaKretanja> listaIstorijaKretanja = new ArrayList<IstorijaKretanja>();

	@FXML
	private VBox vbIstorijaKretanjaKontejner;

	@FXML
	private TextArea taIstorijaKretanja;

	@FXML
	private ListView<IstorijaKretanja> lvListaImenaKompozicija;

	@FXML
	private Button btnIzlaz;

	@FXML
	void izadji(ActionEvent event) {
		Stage stage = (Stage) btnIzlaz.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void initialize() {
		ucitajIstorijeKretanja();
		popunjavanjeImenaZavrsenihKompozicija();
		prikaziIstorijuKretanja();
	}

	private void ucitajIstorijeKretanja() {
		File[] nizDatoteka = new File(PUTANJA_DO_DIREKTORIJUMA_ZA_SERIJALIZACJU).listFiles();
		if (nizDatoteka.length > 0)
			for (File datoteka : nizDatoteka)
				listaIstorijaKretanja.add(Serialization.ucitajIstorijuKretanja(datoteka.toString()));
	}

	private void popunjavanjeImenaZavrsenihKompozicija() {
		for (IstorijaKretanja istorijaKretanja : listaIstorijaKretanja)
			lvListaImenaKompozicija.getItems().add(istorijaKretanja);
	}

	private void prikaziIstorijuKretanja() {
		prikaziDetalje(null);
		lvListaImenaKompozicija.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> prikaziDetalje(newValue));
	}

	private void prikaziDetalje(IstorijaKretanja istorijaKretanja) {
		if (istorijaKretanja != null)
			taIstorijaKretanja.setText(istorijaKretanja.getPoruka().toString());
		else
			taIstorijaKretanja.setText("");
	}

}

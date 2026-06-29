package org.unibl.etf.zs.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.simulation.KontrolerDrumskogSaobracaja;
import org.unibl.etf.zs.utility.FileLogger;
import org.unibl.etf.zs.utility.FileWatcher;
import org.unibl.etf.zs.utility.FxmlLoader;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.PropertiesUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MapaSaobracaja implements Initializable, PathFile {

	@FXML
	private Button btnOmoguciKretanje;

	@FXML
	private Button btnZavrsetakSimulacije;

	@FXML
	private Button btnPrikazKretanja;

	@FXML
	private HBox kontejnerTeritorije;

	private static Pane matricaTeritorije;;
	private static int velicinaMatrice;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		postaviMatricuMape();
		Teritorija.kreirajTeritoriju();
	}

	@FXML
	void omoguciKretanje(ActionEvent event) {
		btnOmoguciKretanje.setDisable(true);
		posmatrajNoveLinijeKompozicija();
		KontrolerDrumskogSaobracaja.pokreniSimulacijuZaVozila();
	}

	@FXML
	void zavrsiSimulaciju(ActionEvent event) {
		obrisiSerijalizovanePodatke();
		Platform.exit();
		System.exit(0);
	}

	@FXML
	void prikaziKretanje(ActionEvent event) {
		FxmlLoader.load(getClass(), "/org/unibl/etf/zs/view/PrikazKretanja.fxml", "Prikazi kretanje");
	}

	private void postaviMatricuMape() {
		velicinaMatrice = PropertiesUtil.vratiSvojstvo("VELICINA_MATRICE", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
		kontejnerTeritorije.setAlignment(Pos.CENTER);
		matricaTeritorije = new Pane();
		matricaTeritorije.setPrefHeight(velicinaMatrice);
		matricaTeritorije.setMinHeight(velicinaMatrice);
		matricaTeritorije.setMaxHeight(velicinaMatrice);
		matricaTeritorije.setPrefWidth(velicinaMatrice);
		matricaTeritorije.setMinWidth(velicinaMatrice);
		matricaTeritorije.setMaxWidth(velicinaMatrice);
		kontejnerTeritorije.getChildren().add(matricaTeritorije);
	}

	private void posmatrajNoveLinijeKompozicija() {
		FileWatcher direktorijumZaPosmatranje;
		try {
			direktorijumZaPosmatranje = new FileWatcher(Paths.get(PUTANJA_DO_DIREKTORIJUMA_ZA_LINIJE), true);
			Thread radnaNit = new Thread(direktorijumZaPosmatranje::procesiranjeDogadjaja);
			radnaNit.setDaemon(true);
			radnaNit.start();
		} catch (Exception ex) {
			FileLogger.log(Level.SEVERE, "Navedena putanja nije odgovarajuca.", ex);
		}
	}

	private static void obrisiSerijalizovanePodatke() {
		File[] nizDatoteka = new File(PUTANJA_DO_DIREKTORIJUMA_ZA_SERIJALIZACJU).listFiles();
		for (File datoteka : nizDatoteka) {
			datoteka.delete();
		}
	}

	public static Pane getMatricaTeritorije() {
		return matricaTeritorije;
	}

	public static void setMatricaTeritorije(Pane matricaTeritorije) {
		MapaSaobracaja.matricaTeritorije = matricaTeritorije;
	}

}

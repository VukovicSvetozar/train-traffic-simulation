package org.unibl.etf.zs.controller;

import org.unibl.etf.zs.utility.FxmlLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class NaslovnaStrana {

	@FXML
	public Button naslovnoDugme;

	@FXML
	public ImageView naslovnaSlikaDugme;

	@FXML
	public synchronized void pokreniAplikaciju(ActionEvent event) {
		FxmlLoader.load(getClass(), "/org/unibl/etf/zs/view/MapaSaobracaja.fxml", "Mapa saobracaja");
		Stage stage = (Stage) naslovnoDugme.getScene().getWindow();
		stage.close();
	}

}

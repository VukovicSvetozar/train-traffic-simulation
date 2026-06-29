package org.unibl.etf.zs.application;

import org.unibl.etf.zs.utility.FxmlLoader;

import javafx.application.Application;
import javafx.stage.Stage;

public class PokretanjeAplikacije extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FxmlLoader.load(getClass(), "/org/unibl/etf/zs/view/NaslovnaStrana.fxml", "Naslovna strana");
	}

	public static void main(String[] args) {
		launch(args);
	}

}

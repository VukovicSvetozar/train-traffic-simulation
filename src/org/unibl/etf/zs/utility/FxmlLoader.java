package org.unibl.etf.zs.utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FxmlLoader implements PathFile {

	public static void load(Class<?> c, String fxml, String naslov) {
		try {
			Stage stage = new Stage();
			Pane root = (Pane) FXMLLoader.load(c.getResource(fxml));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(naslov);
			stage.setResizable(false);
			stage.getIcons().add(new Image(new File(PUTANJA_DO_IKONICE).toURI().toString()));
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();

		} catch (IOException ex) {
			FileLogger.log(Level.SEVERE, "Fx", ex);
		} 
	}

}

package ru.bmstu.www.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class EqualizerApp extends Application {

	@Override
	public void start(Stage stage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/UserInterface.fxml"));

		Scene scene = new Scene(root);

		String url = getClass().getClassLoader().getResource("css/Chart.css").toExternalForm();
		scene.getStylesheets().add(url);

		stage.setScene(scene);
		stage.setWidth(900);
		stage.setHeight(580);
		stage.setResizable(false);

		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});

		stage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

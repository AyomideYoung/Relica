package com.zapsoftco.relica;
	
import java.io.IOException;
import java.net.URL;

import com.zapsoftco.relica.util.ResourceManager;
import com.zapsoftco.relica.util.PaneNavigator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class StartUp extends Application {
	public static Stage stage;
	
	@Override
	public void start(Stage stage) {
		StartUp.stage = stage;
		Pane pane = loadLogin();
		Scene scene = new Scene(pane, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Login to Relica");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	private Pane loadLogin() {
		
		try {
			URL loginFxmlLocation = ResourceManager.getLocalResource("fxml/Login.fxml");
			Pane pane = FXMLLoader.load(loginFxmlLocation);
			return pane;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StackPane();
	}
}

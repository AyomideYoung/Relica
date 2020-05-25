package com.zapsoftco.relica.controllers;

import java.io.IOException;
import com.zapsoftco.relica.util.ResourceManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeScreenController implements Controller {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Parent showUI() {
		try {
			FXMLLoader loader = new FXMLLoader(ResourceManager.getLocalResource("fxml/HomeScreen.fxml"));
			loader.setController(this);
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new Pane();
	}

	public void runAction() {
		logger.info("Home Controller run action");
	}
	
}

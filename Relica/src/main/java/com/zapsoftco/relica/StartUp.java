package com.zapsoftco.relica;
	
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.zapsoftco.relica.controllers.*;
import com.zapsoftco.relica.models.SellerInfoModel;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.PaneNavigator.ComplementaryActions;
import com.zapsoftco.relica.util.ResourceManager;
import com.zapsoftco.relica.util.TtfNameFilter;

import com.zapsoftco.relica.viewloaders.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StartUp extends Application {
	public static Stage stage;
	private static PaneNavigator defaultPaneNavigator;

	private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
	
	@Override
	public void init() {
		loadFonts();
		logger.trace("Completed init execution");
	}
	
	@Override
	public void start(Stage stage) {
		StartUp.stage = stage;
		
		Parent pane = new Pane();
		Scene scene = new Scene(pane, 800, 600);
		scene.getStylesheets().add("/application.css");
		defaultPaneNavigator = new PaneNavigator(scene);
		loadSellerPhotoUpload();

		stage.setScene(scene);
		stage.show();
		logger.trace("Primary Stage shown");
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	private void loadLogin(){
		ViewLoader loader = new LoginViewLoader();
		loader.loadView(defaultPaneNavigator, true);
	}
	
	private void loadSignup() {
		SignupViewLoader viewLoader = new SignupViewLoader();
		viewLoader.loadView(defaultPaneNavigator, true);
		setStageTitle("Signup on Relica");
	}

	private void loadSellerRegistration(){
		ViewLoader viewLoader = new SellerRegistrationViewLoader();
		viewLoader.loadView(defaultPaneNavigator, true);
	}
	
	private void loadHomeScreen() {
		HomeScreenViewLoader viewLoader = new HomeScreenViewLoader();
		viewLoader.loadView(defaultPaneNavigator, true);

	}

	private void loadSellerPhotoUpload(){
		ViewLoader<SellerInfoModel> viewLoader = new SellerPhotoUploadViewLoader();

		SellerInfoModel model = new SellerInfoModel();
		model.setBio("a");
		model.setUsername("a");
		model.setSellerPhoneNumber(100000L);

		viewLoader.setDataModel(model);
		viewLoader.loadView(defaultPaneNavigator, true);
	}
	
	private void loadItemsDisplayScreen() {
		ItemsDisplayViewLoader viewLoader = new ItemsDisplayViewLoader();
		viewLoader.loadView(defaultPaneNavigator, true);

	}
	
	public void setStageTitle(String title) {
		stage.setTitle(title);
	}

	public static PaneNavigator getDefaultPaneNavigator() {
		return defaultPaneNavigator;
	}

	private void loadFonts() {
		loadKeepCalmFonts();
		loadRobotoFonts();
	}
	
	private void loadRobotoFonts() {
		try {
			URL url = ResourceManager.getLocalResource("fonts/roboto");
			loadAllFontsFromDirectory(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void loadKeepCalmFonts() {
		try {
			URL url = ResourceManager.getLocalResource("fonts/keep_calm");
			loadAllFontsFromDirectory(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void loadAllFontsFromDirectory(URI path) {
		File dirObj = new File(path);
		File[] fontFiles = dirObj.listFiles(new TtfNameFilter());
		
		for(File fontFile : fontFiles) {
			loadFontFromFile(fontFile);
		}
		
	}

	public void loadFontFromFile(File fontFile) {
		try {
			URL fontFileUrl = fontFile.toURI().toURL();
			loadFontFromUrl(fontFileUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFontFromUrl(URL url) {
		try(InputStream in = url.openStream()){
			Font font = Font.loadFont(in, 12);
			logger.info("Loaded Font" + " " + font.getName());
		} catch (IOException e) {}
	}
		
}

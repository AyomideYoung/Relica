package com.zapsoftco.relica.controllers;

import java.io.IOException;
import java.net.URL;

import com.zapsoftco.relica.util.ResourceManager;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginController implements Controller{

	@FXML
	Button loginButton;
	
	@FXML
	TextField usernameInput;
	
	@FXML
	PasswordField passwordInput;
	
	@FXML
	Hyperlink signupButton;
	
	@FXML
	VBox controlLayout;
	
	@FXML
	StackPane rootPane;
	
	
	
	public void showSignupPane() {
		Transition transition = createTransition(rootPane);
		transition.play();
		
	}
	
	public void initiateUserLogin(ActionEvent e) {
		
	}
	
	private Transition createTransition(Node node) {
		TranslateTransition translate = new TranslateTransition();
		FadeTransition fade = new FadeTransition();
		
		Bounds localBounds = node.getBoundsInLocal();
		translate.setByX(-localBounds.getWidth());
		
		fade.setFromValue(1.0);
		fade.setToValue(0.4);
		
		ParallelTransition transition = new ParallelTransition(node, fade, translate);
		transition.setRate(1.2);
		transition.setAutoReverse(true);
		transition.setInterpolator(Interpolator.EASE_IN);
		transition.setCycleCount(2);
		
		return transition;
	}

	@Override
	public Parent showUI() {
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

package com.zapsoftco.relica.controllers;

import java.io.IOException;
import java.net.URL;

import com.zapsoftco.relica.StartUp;
import com.zapsoftco.relica.util.Execution;
import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.PaneNavigator.ComplementaryActions;
import com.zapsoftco.relica.util.ResourceManager;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SignupController implements Controller {
	
	@FXML
	private VBox usernameForm;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private Label invalidUsernameText;
	
	@FXML
	private Button usernameFormNextBtn;
	
	@FXML 
	private VBox passwordForm;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField cPasswordField;
	
	@FXML
	private VBox emailForm;
	
	@FXML
	private StackPane emailFormContainer;
	
	@FXML
	private TextField emailAddressField;
	
	@FXML
	private Button emailSubmitBtn;

	@FXML
	private VBox overlay;
	
	@FXML
	private Label progressLabel;
	
	private Transition usernameFormTransition;
	private Transition passwordFormTransition;
	private Transition emailFormTransition;
	
	@Override
	public Parent showUI() {
		try {
			URL fxmlUrl = ResourceManager.getLocalResource("fxml/Signup.fxml");
			FXMLLoader loader = new FXMLLoader(fxmlUrl);
			loader.setController(this);
			loader.load();
			
			instantiateTransitions();
			return this.usernameForm;
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return new Pane();
	}
	
	public void checkForUsername() {}
	
	public void slideToPasswordForm() {
		PaneNavigator paneNavigator = StartUp.getDefaultPaneNavigator();
		
		try {
			paneNavigator.addPaneAsSubPaneAndShow(passwordForm, getPasswordFormComplementaryActions());
		} catch (NoParentPaneException e) {
			e.printStackTrace();
		}
		
	}
	
	public void slideToEmailForm() {
		PaneNavigator paneNavigator = StartUp.getDefaultPaneNavigator();
		
		try {
			paneNavigator.addPaneAsSubPaneAndShow(emailFormContainer, getEmailFormComplementaryActions());
		} catch (NoParentPaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void signup() {
		overlay.setVisible(true);
	}
	
	private Transition createSlideAnimationForNode(Node node) {
		TranslateTransition translate = new TranslateTransition();
		FadeTransition fade = new FadeTransition();
		
		translate.setFromX(0);
		translate.setToX(-300);
		translate.setDuration(Duration.millis(700));
		
		fade.setFromValue(1.0);
		fade.setToValue(0.08);
		fade.setDuration(Duration.millis(700));
		
		ParallelTransition transition = new ParallelTransition(node, fade, translate);
		
		transition.setInterpolator(Interpolator.EASE_IN);
		
		return transition;
	}
	
	private void instantiateTransitions() {
		usernameFormTransition = createSlideAnimationForNode(usernameForm);
		passwordFormTransition = createSlideAnimationForNode(passwordForm);
		emailFormTransition = createSlideAnimationForNode(emailForm);
		
	}
	
	public ComplementaryActions getUsernameFormComplementaryActions() {
		return new ComplementaryActions() {
			
			@Override
			public void executeOnInitialDisplay(Execution exec) {
				usernameFormTransition.setOnFinished(null);
				usernameFormTransition.setRate(-1.0);
				usernameFormTransition.jumpTo(usernameFormTransition.getTotalDuration());
				usernameFormTransition.play();
				
				exec.execute();
			}
			
			@Override
			public void executeOnSubsequentDisplays(Execution exec) {
				executeOnInitialDisplay(exec);
			}
			
			@Override
			public void executeOnSubPaneAdded(Execution exec) {
				usernameFormTransition.setRate(1.0);
				usernameFormTransition.jumpTo("start");
				usernameFormTransition.play();
				
				usernameFormTransition.setOnFinished(e -> exec.execute());
			}
			
			@Override
			public void executeOnLeave(Execution exec) {
				executeOnSubPaneAdded(exec);
			}
		};
	}
	
	public ComplementaryActions getPasswordFormComplementaryActions() {
		return new ComplementaryActions() {
			
			@Override
			public void executeOnInitialDisplay(Execution exec) {
				passwordFormTransition.setOnFinished(null);
				passwordFormTransition.setRate(-1.0);
				passwordFormTransition.jumpTo(passwordFormTransition.getTotalDuration());
				passwordFormTransition.play();
				
				passwordFormTransition.setOnFinished(e -> exec.execute());
				passwordField.requestFocus();
				
			}
			
			@Override
			public void executeOnSubsequentDisplays(Execution exec) {
				executeOnInitialDisplay(exec);
			}
			
			@Override
			public void executeOnSubPaneAdded(Execution exec) {
				executeOnLeave(exec);
			}
			
			@Override
			public void executeOnLeave(Execution exec) {
				passwordFormTransition.setRate(1.0);
				passwordFormTransition.jumpTo("start");
				passwordFormTransition.play();
				
				passwordFormTransition.setOnFinished(e -> exec.execute());
				
			}
		};
	}

	public ComplementaryActions getEmailFormComplementaryActions() {
		
		return new ComplementaryActions() {
			
			@Override
			public void executeOnInitialDisplay(Execution exec) {
				emailFormTransition.setRate(-1.0);
				emailFormTransition.jumpTo("end");
				emailFormTransition.play();
				
				emailFormTransition.setOnFinished(e -> exec.execute());
			}
			
			@Override
			public void executeOnLeave(Execution exec) {
				emailFormTransition.setRate(1.0);
				emailFormTransition.jumpTo("start");
				emailFormTransition.play();
				
				emailFormTransition.setOnFinished(e -> exec.execute());
			}
		};
	}
}

package com.zapsoftco.relica.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.zapsoftco.relica.StartUp;
import com.zapsoftco.relica.util.*;
import com.zapsoftco.relica.util.PaneNavigator.ComplementaryActions;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import static com.zapsoftco.relica.util.AnimationsCreator.createSlideAnimationForNode;

public class SignupController implements  Initializable {
	
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
	
	private Animation usernameFormAnimation;
	private Animation passwordFormAnimation;
	private Animation emailFormAnimation;
	
	public void checkForUsername() {
		//TODO: Write the logic for this method
	}
	
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
	

	
	private void instantiateTransitions() {
		usernameFormAnimation = createSlideAnimationForNode(usernameForm);
		passwordFormAnimation = createSlideAnimationForNode(passwordForm);
		emailFormAnimation = createSlideAnimationForNode(emailForm);
		
	}
	
	public ComplementaryActions getUsernameFormComplementaryActions() {
		return new ComplementaryActions() {
			
			@Override
			public void executeOnInitialDisplay(Execution exec) {
				usernameFormAnimation.setOnFinished(null);
				usernameFormAnimation.setRate(-1.0);
				usernameFormAnimation.jumpTo(usernameFormAnimation.getTotalDuration());
				usernameFormAnimation.play();
				
				exec.execute();
			}
			
			@Override
			public void executeOnSubsequentDisplays(Execution exec) {
				executeOnInitialDisplay(exec);
			}
			
			@Override
			public void executeOnSubPaneAdded(Execution exec) {
				usernameFormAnimation.setRate(1.0);
				usernameFormAnimation.jumpTo("start");
				usernameFormAnimation.play();
				
				usernameFormAnimation.setOnFinished(e -> exec.execute());
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
				passwordFormAnimation.setOnFinished(null);
				passwordFormAnimation.setRate(-1.0);
				passwordFormAnimation.jumpTo(passwordFormAnimation.getTotalDuration());
				passwordFormAnimation.play();
				
				passwordFormAnimation.setOnFinished(e -> exec.execute());
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
				passwordFormAnimation.setRate(1.0);
				passwordFormAnimation.jumpTo("start");
				passwordFormAnimation.play();
				
				passwordFormAnimation.setOnFinished(e -> exec.execute());
				
			}
		};
	}

	public ComplementaryActions getEmailFormComplementaryActions() {
		
		return new ComplementaryActions() {
			
			@Override
			public void executeOnInitialDisplay(Execution exec) {
				emailFormAnimation.setRate(-1.0);
				emailFormAnimation.jumpTo("end");
				emailFormAnimation.play();
				
				emailFormAnimation.setOnFinished(e -> exec.execute());
			}
			
			@Override
			public void executeOnLeave(Execution exec) {
				emailFormAnimation.setRate(1.0);
				emailFormAnimation.jumpTo("start");
				emailFormAnimation.play();
				
				emailFormAnimation.setOnFinished(e -> exec.execute());
			}
		};
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instantiateTransitions();
	}
}

package com.zapsoftco.relica.util.reusable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.zapsoftco.relica.util.ResourceManager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.event.*;

public class ActionCard extends VBox implements Initializable {

	@FXML
	private ImageView iconView;

	@FXML
	private Label cardTitle;

	@FXML
	private Button helpButton;

	@FXML
	private Label helpText;

	@FXML 
	private BorderPane lowerRibbon;

	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
	private StringProperty titleProperty = new SimpleStringProperty();
	private StringProperty helpTextProperty = new SimpleStringProperty();
	private ObjectProperty<EventHandler<? super ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<? super ActionEvent>>() {

		@Override
		public Object getBean() {
			return ActionCard.this;
		}

		@Override
		public String getName() {
			return "onAction";
		}
	
		@Override
		public void invalidated() {
			setEventHandler(ActionEvent.ACTION, onAction.get());
		}
	
	};
	
	public ActionCard() {
		try {
			FXMLLoader loader = 
					new FXMLLoader(ResourceManager.getLocalResource("fxml/ActionCard.fxml"));

			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public final ObjectProperty<Image> imageProperty() {
		return imageProperty;
	}

	public Image getImage() {
		return imageProperty.getValue();
	}

	public void setImage(Image img) {
		imageProperty.setValue(img);
	}

	public final StringProperty titleProperty() {
		return titleProperty;
	}

	public String getTitle() {
		return titleProperty.getValue();
	}

	public void setTitle(String title) {
		titleProperty.setValue(title);
	}

	public final StringProperty helpTextProperty() {
		return helpTextProperty;
	}

	public void setHelpText(String helpText) {
		helpTextProperty.setValue(helpText);
	}

	public String getHelpText() {
		return helpTextProperty.getValue();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.getStyleClass().add("action-card");
		bindPropertiesToNodes();
		setStyleClassesForAllNodes();
		setBorderPaneAlignments();
		styleAllNodesInCode();
		setEventHandlersToFireAction();
		helpButton.setOnAction(e -> {
			showHelpText();
			e.consume();});
	}

	private void styleAllNodesInCode() {
		styleLowerRibbon();
		styleHelpButton();
		styleCardTitle();
		styleHelpText();
	}

	private void styleHelpText() {
		helpText.setFont(Font.font(14));
		helpText.setTextFill(Color.WHITE);
		
		BackgroundFill fill = new BackgroundFill(Color.web("#52057a"), CornerRadii.EMPTY, Insets.EMPTY);
		helpText.setBackground(new Background(fill));
		helpText.setPadding(new Insets(3, 3, 3, 30));
	}

	private void styleCardTitle() {
		cardTitle.setFont(Font.font(20));
		cardTitle.setTextFill(Color.WHITE);
	}

	private void styleHelpButton() {
		Font font = Font.font(null, FontWeight.findByWeight(300), FontPosture.REGULAR, 17);
		Insets padding = new Insets(3);
		BackgroundFill fill = new BackgroundFill(Color.web("#1586ff"), new CornerRadii(50, true), Insets.EMPTY);
		Background background = new Background(fill);
		
		helpButton.setPadding(padding);
		helpButton.setFont(font);
		helpButton.setBackground(background);
		helpButton.setTextFill(Color.WHITE);
		
		helpButton.setOnMouseEntered(e -> {
			BackgroundFill hoverFill = new BackgroundFill(Color.web("#1586ff").darker(), new CornerRadii(50, true), Insets.EMPTY);
			helpButton.setBackground(new Background(hoverFill));
		});
		
		helpButton.setOnMouseExited(e -> {
			helpButton.setBackground(background);
		});
		
		
	}

	private void styleLowerRibbon() {
		BackgroundFill fill = new BackgroundFill(Color.web("#52057a"), CornerRadii.EMPTY, Insets.EMPTY);
		lowerRibbon.setBackground(new Background(fill));
		lowerRibbon.setPadding(new Insets(5));
	}

	private void setBorderPaneAlignments() {
		BorderPane.setAlignment(helpButton, Pos.CENTER_RIGHT);
		BorderPane.setAlignment(helpText, Pos.CENTER);
		BorderPane.setAlignment(helpButton, Pos.BOTTOM_CENTER);
		
	}
	
	private void setStyleClassesForAllNodes() {
		cardTitle.getStyleClass().add("card-title");
		helpText.getStyleClass().add("help-text");
		lowerRibbon.getStyleClass().add("lower-ribbon");
		helpButton.getStyleClass().add("help-button");
		
	}

	private void bindPropertiesToNodes() {
		iconView.imageProperty().bind(imageProperty);
		cardTitle.textProperty().bind(titleProperty);
		helpText.textProperty().bind(helpTextProperty);				
	}
	
	private void setEventHandlersToFireAction() {
		this.setOnMouseClicked(e -> fireAction());
	}

	private void fireAction() {
		ActionEvent event = new ActionEvent();
		Event.fireEvent(this, event);
	}

	public ObjectProperty<EventHandler<? super ActionEvent>> onActionProperty() {
		return onAction;
	}

	public EventHandler<? super ActionEvent> getOnAction() {
		return onAction.get();
	}
	
	public void setOnAction(EventHandler<? super ActionEvent> value) {
		this.onAction.set(value);
	}
	
	private void showHelpText() {
		if(!helpTextProperty.getValue().isEmpty()) {
			helpText.setPrefWidth(this.getWidth());
			toggleHelpText();
		}
	}
	
	private void toggleHelpText() {
		if(helpText.isVisible()) {
			helpText.setManaged(false);
			helpText.setVisible(false);
		} else {
			helpText.setManaged(true);
			helpText.setVisible(true);
		}
	}

}
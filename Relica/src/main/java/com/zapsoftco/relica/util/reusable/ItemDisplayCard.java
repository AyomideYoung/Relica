package com.zapsoftco.relica.util.reusable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.zapsoftco.relica.util.ResourceManager;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ItemDisplayCard extends VBox implements Initializable{
	private ObjectProperty<Image> itemImage = new SimpleObjectProperty<>();
	private StringProperty title = new SimpleStringProperty();
	private StringProperty sellerName = new SimpleStringProperty();
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private ImageView itemImageView;
	
	@FXML
	private VBox otherInformationContainer;
	
	@FXML
	private Label sellerNameLabel;
	
	@FXML
	private Rectangle overlay;
	
	public ItemDisplayCard() {
		try {
			FXMLLoader loader = 
					new FXMLLoader(ResourceManager.getLocalResource("fxml/ItemDisplayCard.fxml"));

			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Image getItemImage() {
		return itemImage.get();
	}
	
	public void setItemImage(Image value) {
		itemImage.set(value);
		bindOverlaySizeToItemImageView();
	}
	
	public ObjectProperty<Image> itemImageProperty() {
		return itemImage;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}
	
	public String getSellerName() {
		return sellerName.get();
	}

	public void setSellerName(String value) {
		sellerName.set(value);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindPropertiesToNodes();
		addStyleClassesToNodes();
		styleSomeNodesInCode();
	}

	private void styleSomeNodesInCode() {
		styleTitleLabel();
		styleSellerNameLabel();
		styleOverlay();
		styleThisItemDisplayCardInstance();
	}

	private void styleThisItemDisplayCardInstance() {
		BackgroundFill fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(fill);
		this.setBackground(background);
		
		this.setPadding(new Insets(10));
	}

	private void styleOverlay() {
		Stop[] stops = {new Stop(0, Color.web("#ffffff", 0)),
						new Stop(0.82, Color.web("#101010", 0.7)),
						new Stop(1, Color.web("#000000", 0.91))};
		
		LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		overlay.setFill(gradient);
	}

	private void styleSellerNameLabel() {
		//add and configure "By" Text
		Text byText = new Text("By ");
		byText.fontProperty().bind(sellerNameLabel.fontProperty());
		
		sellerNameLabel.setGraphic(byText);
		sellerNameLabel.setContentDisplay(ContentDisplay.LEFT);
		sellerNameLabel.setGraphicTextGap(3);
	}

	private void styleTitleLabel() {
		Font font = Font.font(20);
		titleLabel.setFont(font);
	
		
	}

	private void addStyleClassesToNodes() {
		sellerNameLabel.getStyleClass().add("seller-name");
		itemImageView.getStyleClass().add("item-image");
		titleLabel.getStyleClass().add("title");
		otherInformationContainer.getStyleClass().add("other-info-container");
		overlay.getStyleClass().add("image-overlay");
		
		this.getStyleClass().add("item-display-card");
	}

	private void bindPropertiesToNodes() {
		itemImageView.imageProperty().bind(itemImage);
		titleLabel.textProperty().bind(title);
		sellerNameLabel.textProperty().bind(sellerName);
		
		bindOverlaySizeToItemImageView();
		
	}

	private void bindOverlaySizeToItemImageView() {
		if(itemImageView.getImage() == null && itemImage.get() == null)
			return;
		
		DoubleProperty width = new SimpleDoubleProperty(itemImageView.getImage().getWidth());
		DoubleProperty height = new SimpleDoubleProperty(itemImageView.getImage().getHeight());
	
		overlay.widthProperty().bind(width);
		overlay.heightProperty().bind(height);
	}

	public ObservableList<Node> getOtherInfo() {
		return otherInformationContainer.getChildren();
	}

	public void setOtherInfo(ObservableList<Node> nodes) {
		otherInformationContainer.getChildren().setAll(nodes);
	}
	
}

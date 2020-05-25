package com.zapsoftco.relica.util.reusable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.zapsoftco.relica.util.ResourceManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LocationLabel extends Label {
	private ImageView pinImage = new ImageView();
	private StringProperty stringProperty = new SimpleStringProperty();
	
	public LocationLabel() {
		URL url = ResourceManager.getLocalResource("images/map-pin.png");
		
		try (InputStream in = url.openStream()){
			Image img = createIcon(in);
			pinImage.setImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		configureGraphicProperties();
	}

	private Image createIcon(InputStream in) {
		Image image = new Image(in, 32, 32, true, true);
		return image;
	}

	public LocationLabel(String location) {
		URL url = ResourceManager.getLocalResource("images/map-pin.png");
		try (InputStream in = url.openStream()){
			Image img = new Image(in);
			pinImage.setImage(img);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		stringProperty.setValue(location);
		configureGraphicProperties();
	}
	
	private void configureGraphicProperties() {
		super.setGraphic(pinImage);
		super.setGraphicTextGap(10);
		super.setContentDisplay(ContentDisplay.LEFT);
	}
	
	
}

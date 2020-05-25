package com.zapsoftco.relica.util.reusable;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceLabel extends Label {
	public static final Text DOLLAR_ICON = new Text("$");
	public static final Text EURO_ICON = new Text(new Character('\u20ac').toString());

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private void setTextFontSize() {
		DOLLAR_ICON.setFont(Font.font("Keep Calm Med", 30));
		EURO_ICON.setFont(Font.font("Keep Calm Med", 30));

		logger.info("Text font sizes have been set");
	}
	
	public PriceLabel(String text, Node node) {
		super(text, node);
		setTextFontSize();
		setDefaultGraphicTextGap();
	}
	
	public PriceLabel() {
		super();
		setTextFontSize();
		setDefaultGraphicTextGap();
	}
	
	public PriceLabel(String text) {
		super(text);
		setTextFontSize();
		setDefaultGraphicTextGap();
	}
	
	private void setDefaultGraphicTextGap() {
		this.setGraphicTextGap(10);
	}
}

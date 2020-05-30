package com.zapsoftco.relica.util.reusable;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceLabel extends Label {
	public static final char DOLLAR_CHARACTER = '$';
	public static final char EURO_CHARACTER = '\u20ac';
	public static final char NAIRA_CHARACTER = '\u20a6';
	public static final char YEN_CHARACTER = '\u00a5';

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PriceLabel(String text, Node node) {
		super(text, node);
		setDefaultGraphicTextGap();
	}
	
	public PriceLabel() {
		super();

		setDefaultGraphicTextGap();
	}
	
	public PriceLabel(String text) {
		super(text);
		setDefaultGraphicTextGap();
	}
	
	private void setDefaultGraphicTextGap() {
		this.setGraphicTextGap(10);
	}
}

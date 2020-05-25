package com.zapsoftco.relica.util.reusable;

import javafx.scene.image.Image;

public class LabelComboBoxData {
    private String text;
    private Image image;

    public LabelComboBoxData(){}

    public LabelComboBoxData(String text, Image image) {
        this.text = text;
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Image getImage(){
        return this.image;
    }

    public void setImage(Image image){
        this.image = image;
    }
    }


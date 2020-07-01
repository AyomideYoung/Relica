package com.zapsoftco.relica.controllers;

import com.zapsoftco.relica.util.AnimationsCreator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;

public class SellerRegistrationController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(SellerRegistrationController.class);

    @FXML
    private HBox phoneNumberInputContainer;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button submitBtn;


    private void flashPhoneNumberInputContainer() {
        Animation animation = AnimationsCreator.createLinedBackgroundErrorFlash(phoneNumberInputContainer);
        animation.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneNumberField.textProperty().addListener(new PhoneNumberListener());
    }

    private class PhoneNumberListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            try{
                Long.parseLong(newValue);

                //this section does not get called if the parseLong throws an Exception
                if(newValue.length() < 6)
                    disableSubmitButton();
                else
                    enableSubmitButton();
            } catch (NumberFormatException e){
                logger.info("Text in field is invalid");
                flashPhoneNumberInputContainer();
                disableSubmitButton();
            }
        }
    }

    private void enableSubmitButton() {
        submitBtn.setDisable(false);
    }

    private void disableSubmitButton(){
        submitBtn.setDisable(true);
    }
}

package com.zapsoftco.relica.controllers;

import com.zapsoftco.relica.util.AnimationsCreator;
import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerRegistrationController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(SellerRegistrationController.class);

    @FXML
    private Button nextBtn;

    @FXML
    private HBox phoneNumberInputContainer;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button backBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private VBox bioBox;

    @FXML
    private VBox phoneNumberBox;

    private void flashPhoneNumberInputContainer() {
        Animation animation = AnimationsCreator.createLinedBackgroundErrorFlash(phoneNumberInputContainer);
        animation.play();
    }

    public void showPhoneNumberBox(){
        bioBox.setVisible(false);
        phoneNumberBox.setVisible(true);
        phoneNumberBox.requestFocus();
    }

    public void showBioBox(){
        phoneNumberBox.setVisible(false);
        bioBox.setVisible(true);
        bioBox.requestFocus();
    }

    public void submitNewSellerData(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneNumberField.textProperty().addListener(new PhoneNumberListener());
    }

    private class PhoneNumberListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            try {
                Long.parseLong(newValue);

                //this section does not get called if the parseLong throws an Exception
                if (newValue.length() < 6)
                    disableNextBtn();
                else
                    enableNextBtn();
            } catch (NumberFormatException e) {
                logger.info("Text in field is invalid");
                flashPhoneNumberInputContainer();
                disableNextBtn();
            }
        }
    }

    private void enableNextBtn() {
        nextBtn.setDisable(false);
    }

    private void disableNextBtn() {
        nextBtn.setDisable(true);
    }
}

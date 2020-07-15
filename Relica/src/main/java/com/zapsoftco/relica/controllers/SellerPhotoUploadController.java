package com.zapsoftco.relica.controllers;

import com.zapsoftco.relica.StartUp;
import com.zapsoftco.relica.models.SellerInfoModel;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SellerPhotoUploadController implements DataModelConsumer<SellerInfoModel> {

    private SellerInfoModel model;
    private File photoFile;

    @FXML
    private AnchorPane avatarRegion;

    @Override
    public void setDataModel(SellerInfoModel sellerInfoModel) {
        this.model = sellerInfoModel;
    }

    public void initialize() {
        verifyDataModelExistenceAndValidity();
    }

    private void verifyDataModelExistenceAndValidity() {
        if (model == null) {
            throw new NullPointerException("No data model has been passed to this controller");
        } else if (!isDataModelValid()) {
            throwInvalidDataModelExceptionWithRelevantMessages();
        }
    }

    private void throwInvalidDataModelExceptionWithRelevantMessages() {
        String msg = "The data model passed is invalid. ";

        if (model.getUsername().isEmpty())
            throw new InvalidDataModelException(msg + "The username field is empty");
        if (model.getBio().isEmpty())
            throw new InvalidDataModelException(msg + "The username field is empty");
        if (model.getSellerPhoneNumber() == null && model.getSellerPhoneNumber() < 99999)
            throw new InvalidDataModelException(msg + "The sellerPhoneNumber field is invalid");

    }

    private boolean isDataModelValid() {
        return !(model.getUsername().isEmpty() &&
                model.getBio().isEmpty() &&
                (model.getSellerPhoneNumber() == null && model.getSellerPhoneNumber() < 99999)
        );
    }


    @Override
    public Class<SellerInfoModel> getDataModelClass() {
        return SellerInfoModel.class;
    }

    public void chooseAndDisplayImageFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files",
                        "*.jpg","*.gif","*.png","*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(StartUp.stage);
        displaySelectedImageInAvatarRegion(selectedFile);
    }

    private void displaySelectedImageInAvatarRegion(File selectedFile) {

        try {
            if (selectedFile != null) {
                URL fileUrl = selectedFile.toURI().toURL();
                Image img = new Image(fileUrl.openStream());
                BackgroundImage backgroundImg = createBackgroundImage(img);
                Background bg = createBackgroundWithBackgroundImage(backgroundImg);

                avatarRegion.setBackground(bg);
                photoFile = selectedFile;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitSellerInfoAndPhoto(){}

    private Background createBackgroundWithBackgroundImage(BackgroundImage backgroundImg) {
        return new Background(backgroundImg);
    }

    private BackgroundImage createBackgroundImage(Image img) {
        BackgroundSize containPosition =
                new BackgroundSize(100, 100, true,
                        true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,containPosition);

        return backgroundImage;
    }
}

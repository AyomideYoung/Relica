package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.controllers.SellerPhotoUploadController;
import com.zapsoftco.relica.models.SellerInfoModel;
import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SellerPhotoUploadViewLoader implements ViewLoader<SellerInfoModel> {



    private SellerInfoModel model;

    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryView) {
        try {
            ensureThatModelIsNotNull();
            FXMLLoader loader = new FXMLLoader(ResourceManager.getLocalResource("fxml/SellerPhotoUpload.fxml"));
            loader.setController(createAndSetupController());
            Pane pane = loader.load();

            if(isPrimaryView || !navigator.hasPrimaryPane()){
                navigator.setPrimaryPane(pane);
            } else {
                navigator.addPaneAsSubPaneAndShow(pane);
            }
        } catch (IOException | NoParentPaneException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataModel(SellerInfoModel sellerInfoModel) {
        this.model = sellerInfoModel;
    }

    private Object createAndSetupController() {
        SellerPhotoUploadController controller = new SellerPhotoUploadController();
        controller.setDataModel(model);

        return controller;
    }

    private void ensureThatModelIsNotNull() {
        if(model == null){
        throw new NullPointerException("No model has been passed to the view loader");
    }
    }
}

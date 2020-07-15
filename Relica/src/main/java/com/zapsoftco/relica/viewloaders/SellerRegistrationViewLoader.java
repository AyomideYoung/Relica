package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.controllers.SellerRegistrationController;
import com.zapsoftco.relica.models.SellerInfoModel;
import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SellerRegistrationViewLoader implements ViewLoader<SellerInfoModel>{

    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryView) {
        try {
            FXMLLoader loader = new FXMLLoader(ResourceManager.getLocalResource("fxml/SellerRegistration.fxml"));
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

}

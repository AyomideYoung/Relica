package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class LoginViewLoader implements ViewLoader{


    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryView) {
        try {
            URL loginFxmlLocation = ResourceManager.getLocalResource("fxml/Login.fxml");
            Pane pane = FXMLLoader.load(loginFxmlLocation);

            if(isPrimaryView || !navigator.hasPrimaryPane())
                navigator.setPrimaryPane(pane);
            else
                navigator.addPaneAsSubPaneAndShow(pane);

        } catch (IOException | NoParentPaneException e) {
            e.printStackTrace();
        }
    }
}

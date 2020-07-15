package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.controllers.SignupController;
import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.PaneNavigator.ComplementaryActions;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class SignupViewLoader implements ViewLoader<Void> {

    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryPane) {
        try {
            URL fxmlUrl = ResourceManager.getLocalResource("fxml/Signup.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Pane pane = loader.load();

            SignupController controller = loader.getController();
            ComplementaryActions usernameFormComplementaryActions = controller.getUsernameFormComplementaryActions();

            if(isPrimaryPane || navigator.hasPrimaryPane() == false)
                navigator.setPrimaryPane(pane, usernameFormComplementaryActions);
            else
                navigator.addPaneAsSubPaneAndShow(pane, usernameFormComplementaryActions);

        } catch (IOException | NoParentPaneException e) {
            e.printStackTrace();
        }

    }
}

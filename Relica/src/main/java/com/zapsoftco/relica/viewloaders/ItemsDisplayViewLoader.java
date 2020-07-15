package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class ItemsDisplayViewLoader implements ViewLoader<Void> {

    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryView) {
        try {
            URL fxmlUrl = ResourceManager.getLocalResource("fxml/ItemsDisplayPage.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Pane pane = loader.load();

            if(isPrimaryView || !navigator.hasPrimaryPane())
                navigator.setPrimaryPane(pane);
            else
                navigator.addPaneAsSubPaneAndShow(pane);

        } catch (IOException | NoParentPaneException e) {
            e.printStackTrace();
        }
    }
}

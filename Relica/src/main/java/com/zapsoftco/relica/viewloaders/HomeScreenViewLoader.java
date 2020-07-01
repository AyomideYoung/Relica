package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.util.NoParentPaneException;
import com.zapsoftco.relica.util.PaneNavigator;
import com.zapsoftco.relica.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeScreenViewLoader implements ViewLoader {

    @Override
    public void loadView(PaneNavigator navigator, boolean isPrimaryView) {
        try {
            FXMLLoader loader = new FXMLLoader(ResourceManager.getLocalResource("fxml/HomeScreen.fxml"));
            Pane pane = loader.load();

            if(isPrimaryView)
                navigator.setPrimaryPane(pane);
            else
                navigator.addPaneAsSubPaneAndShow(pane);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoParentPaneException e){
            this.loadView(navigator, true);
        }

    }
}

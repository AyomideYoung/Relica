package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.util.PaneNavigator;
import javafx.scene.Parent;
import javafx.scene.Scene;

public interface ViewLoader {

    public void loadView(PaneNavigator navigator, boolean isPrimaryView);

}

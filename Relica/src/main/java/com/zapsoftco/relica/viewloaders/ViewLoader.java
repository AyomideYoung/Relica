package com.zapsoftco.relica.viewloaders;

import com.zapsoftco.relica.util.PaneNavigator;
import javafx.scene.Parent;
import javafx.scene.Scene;

public interface ViewLoader <T> {

    public void loadView(PaneNavigator navigator, boolean isPrimaryView);

    public default void setDataModel(T t){
        throw new UnsupportedOperationException("This view loader does not support the passing"
            + " of data models ");
    }
}

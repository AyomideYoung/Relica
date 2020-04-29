package com.zapsoftco.relica.util;

import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


/**
 * This class's main use is to navigate through the panes of a scene.
 * It sets up a parent-child relationship between different panes and provides
 * a user interface for navigating from child to parent.
 * 
 * <p>
 * <b>NOTE:</b> the {@code PaneNavigator} does not allow forward 
 * navigation
 *
 */
public class PaneNavigator{

	private Scene scene;
	private Deque<Pane> paneQueue = new LinkedList<>();
	private Pane paneNavigatorUI;

	@FXML
	private FlowPane navigatorButtonContainer;

	/**
	 * Creates a new instance of {@code PaneNavigator} and passes the scene 
	 * it operates on
	 * @param scene
	 * the scene whose pane to navigate
	 */
	public PaneNavigator(Scene scene) {
		this.scene = scene;
		paneNavigatorUI = loadPaneNavigatorUI();
	}

	/**
	 * Replaces the primary pane for the navigator.
	 * Calling this method will also cause the navigator to
	 * remove any data (including all subpanes) associated with the
	 * previous parent pane
	 * @param newPane
	 * the new parent pane
	 * 
	 */
	public void replacePrimaryPane(Pane newPane) {
		scene.setRoot(newPane);
		paneQueue.clear();
		paneQueue.add(newPane);
	}

	/**
	 * Adds the new pane as the subpane of the currently displayed pane
	 * 
	 * @param newPane
	 * the pane to be added as subpane
	 * 
	 */
	public void addPaneAsSubPane(Pane newPane) {
		scene.setRoot(paneNavigatorUI);
		refreshNavigatorUI();
		paneNavigatorUI.getChildren().add(newPane);
		paneQueue.add(newPane);
	}

	/**
	 * Navigates to the immediate parent pane of the currently displayed pane 
	 */
	public void navigateBackwards() {
		boolean isCurrentPaneLastSubPane = paneQueue.size() == 2;

		if(isCurrentPaneLastSubPane) {
			showFirstPaneAsRootAndUpdatePaneQueue();
		} else {
			removeCurrentPaneAndShowImmediateParent();
		}
	}

	
	private void showFirstPaneAsRootAndUpdatePaneQueue() {
		paneQueue.removeLast();
		scene.setRoot(paneQueue.getFirst());
	}

	private void removeCurrentPaneAndShowImmediateParent() {
		paneQueue.removeLast();
		Pane immediateParentPane = paneQueue.getLast();
		refreshNavigatorUI();
		paneNavigatorUI.getChildren().add(immediateParentPane);
	}
	
	private void refreshNavigatorUI() {
		paneNavigatorUI.getChildren()
		.removeIf(e -> !e.equals(navigatorButtonContainer));

	}

	private Pane loadPaneNavigatorUI() {
		try {
			URL paneNavigatorUrl = ResourceManager.getLocalResource("fxml/PaneNavigator.fxml");
			FXMLLoader loader = new FXMLLoader(paneNavigatorUrl);
			loader.setController(this);
			
			Pane pane = loader.load();
			
			return pane;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

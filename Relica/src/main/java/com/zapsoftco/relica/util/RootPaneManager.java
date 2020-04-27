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

public class RootPaneManager {

	private Scene scene;
	private Deque<Pane> paneQueue = new LinkedList<>();
	private Pane paneNavigatorUI;

	@FXML
	private FlowPane navigatorButtonContainer;


	public RootPaneManager(Scene scene) {
		this.scene = scene;
		paneNavigatorUI = loadPaneNavigatorUI();
	}

	public void replaceParentPane(Pane pane) {
		scene.setRoot(pane);
		paneQueue.clear();
		paneQueue.add(pane);
	}

	public void addPaneAsSubPane(Pane pane) {
		scene.setRoot(paneNavigatorUI);
		refreshNavigatorUI();
		paneNavigatorUI.getChildren().add(pane);
		paneQueue.add(pane);
	}

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
			Pane pane = FXMLLoader.load(paneNavigatorUrl);
			return pane;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

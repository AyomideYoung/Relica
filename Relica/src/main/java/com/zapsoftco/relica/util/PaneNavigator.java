package com.zapsoftco.relica.util;

import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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

	private final Map<Pane, ComplementaryActions> complementaryActionsMap = new HashMap<>();
	private Scene scene;
	private final Deque<Pane> paneQueue = new LinkedList<>();
	private Pane paneNavigatorUI;

	@FXML
	private FlowPane navigatorButtonContainer;

	@FXML
	private VBox navigatorRoot;

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
	 * {@code ComplimentaryActions} gives a pane the ability to control the
	 * way the navigator uses it
	 * 
	 * All methods must call the {@link Execution#execute()} method or the
	 * navigator will never complete its execution
	 */
	public static class ComplementaryActions {

		private Pane pane;

		public void setPane(Pane pane) {
			this.pane = pane;
		}

		public Pane getPane() {
			return pane;
		}

		/**
		 * Executes when {@code pane} is first displayed
		 * 
		 * @param exec
		 * the remaining instructions left for the navigator to run
		 */
		public void executeOnInitialDisplay(Execution exec) {
			exec.execute();
		}

		/**
		 * Executes when {@code pane} has been displayed one or more times
		 * before
		 * 
		 * @param exec
		 * the remaining instructions left for the navigator to run
		 */
		public void executeOnSubsequentDisplays(Execution exec) {
			exec.execute();
		}

		/**
		 * Executes when the navigator is about to leave the pane
		 * 
		 * @param exec
		 * the remaining instructions left for the navigator to run
		 */
		public void executeOnLeave(Execution exec) {
			exec.execute();
		}

		/**
		 * Executes when {@code pane} receives a subpane
		 * 
		 * @param exec
		 * the remaining instructions left for the navigator to run
		 */
		public void executeOnSubPaneAdded(Execution exec) {
			exec.execute();
		}
	}

	/**
	 * Replaces the primary pane for the navigator.
	 * Calling this method will also cause the navigator to
	 * remove any data (including all subpanes) associated with the
	 * previous parent pane
	 * 
	 * @param newPane
	 * the new parent pane
	 * @param complementaryActions
	 * the {@link ComplementaryActions} to call when backwards navigation
	 * leads to {@code newPane}
	 * 
	 */
	public void setPrimaryPane(Pane newPane, ComplementaryActions complementaryActions) {
		scene.setRoot(newPane);
		paneQueue.clear();
		paneQueue.add(newPane);

		complementaryActions.executeOnInitialDisplay(() -> 
			complementaryActionsMap.put(newPane, complementaryActions));
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
	public void setPrimaryPane(Pane newPane) {
		setPrimaryPane(newPane, new ComplementaryActions());
	}


	/**
	 * Adds the new pane as the sub pane of the currently displayed pane
	 * 
	 * @param newPane
	 * the pane to be added as subpane
	 * @throws NoParentPaneException
	 * if there is no set parent pane
	 * 
	 */
	public void addPaneAsSubPaneAndShow(Pane newPane) throws NoParentPaneException {
		addPaneAsSubPaneAndShow(newPane, new ComplementaryActions());
	}

	/**
	 * Adds the new pane as the subpane of the currently displayed pane
	 * 
	 * @param newPane
	 * the pane to be added as subpane
	 * @param actionsForNewPane
	 * the {@code ComplementaryActions} to call when backwards navigation leads to newPane
	 * @throws NoParentPaneException
	 * if there is no set parent pane
	 */
	public void addPaneAsSubPaneAndShow(Pane newPane, ComplementaryActions actionsForNewPane) throws NoParentPaneException {
		if(!hasPrimaryPane())
			throw new NoParentPaneException("No Parent Pane has been set");
		
		Pane parentPane = paneQueue.getLast();
		ComplementaryActions actionsForParentPane = complementaryActionsMap.get(parentPane);
		InitializeAndDisplaySubPaneExecution execution = new InitializeAndDisplaySubPaneExecution();
		
		execution.scene = scene;
		execution.actionsForNewPane = actionsForNewPane;
		execution.newPane = newPane;
		execution.complementaryActionsMap = complementaryActionsMap;
		execution.paneQueue = paneQueue;
		
		actionsForParentPane.executeOnSubPaneAdded(execution);
	}
	

	/**
	 * Navigates to the immediate parent pane of the currently displayed pane 
	 */
	public void navigateBackwards() {
		boolean isCurrentPaneLastSubPane = paneQueue.size() == 2;

		if(isCurrentPaneLastSubPane) {
			removeCurrentPaneAndShowFirstPaneAsRoot();
		} else {
			removeCurrentPaneAndShowImmediateParent();
		}
	}


	private void removeCurrentPaneAndShowFirstPaneAsRoot() {
		ComplementaryActions actionsForFirstPane;
		ComplementaryActions actionsForCurrentPane;
		Pane firstPane;
		ShowPrimaryPaneExecution execution = new ShowPrimaryPaneExecution();

		actionsForCurrentPane = removeCurrentPaneFromPaneQueueAndGetItsActions();
		
		//Get the parent pane and its ComplementaryActions
		firstPane = paneQueue.getFirst();
		actionsForFirstPane = complementaryActionsMap.get(firstPane);
		
		//set the execution variables
		execution.scene = scene;
		execution.firstPane = firstPane;
		execution.actionsForFirstPane = actionsForFirstPane;
		
		actionsForCurrentPane.executeOnLeave(execution);
	}


	private ComplementaryActions removeCurrentPaneFromPaneQueueAndGetItsActions() {
		Pane currentPane = paneQueue.getLast();
		ComplementaryActions actionsForCurrentPane = complementaryActionsMap.get(currentPane);
		paneQueue.remove(currentPane);
		
		return actionsForCurrentPane;
}

	private void removeCurrentPaneAndShowImmediateParent() {
		Pane immediateParentPane;
		ComplementaryActions actionsForCurrentPane = removeCurrentPaneFromPaneQueueAndGetItsActions();
		ComplementaryActions actionsForImmediateParentPane;
		ShowImmediateParentPaneExecution execution = new ShowImmediateParentPaneExecution();
		
		immediateParentPane = paneQueue.getLast();
		actionsForImmediateParentPane = complementaryActionsMap.get(immediateParentPane);
		
		execution.immediateParentPane = immediateParentPane;
		execution.actionsForImmediateParentPane = actionsForImmediateParentPane;
		
		actionsForCurrentPane.executeOnLeave(execution);

	}

	private void refreshNavigatorUI() {
		paneNavigatorUI.getChildren()
		.removeIf(e -> !e.equals(navigatorButtonContainer));

	}

	private void bindPaneBoundsToNavigatorRootDimensions(Pane pane) {
		pane.prefWidthProperty().bind(navigatorRoot.widthProperty());
		pane.prefHeightProperty().bind(navigatorRoot.heightProperty());
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
	
	public boolean hasPrimaryPane() {
		 return paneQueue.size() > 0;
	}
	
	private class InitializeAndDisplaySubPaneExecution implements Execution {
		public Scene scene;
		public Pane newPane;
		public Deque<Pane> paneQueue;
		public ComplementaryActions actionsForNewPane;
		public Map<Pane, ComplementaryActions> complementaryActionsMap;
		
		public void execute() {
			//show newPane
			scene.setRoot(paneNavigatorUI);
			refreshNavigatorUI();
			paneNavigatorUI.getChildren().add(newPane);
			bindPaneBoundsToNavigatorRootDimensions(newPane);

			//add newPane and actions to their respective collections
			paneQueue.add(newPane);
			complementaryActionsMap.put(newPane, actionsForNewPane);

			//no more executions to add
			actionsForNewPane.executeOnInitialDisplay(()->{});
		}
	}
	
	private static class ShowPrimaryPaneExecution implements Execution {

		public Scene scene;
		public Pane firstPane;
		public ComplementaryActions actionsForFirstPane;
		
		@Override
		public void execute() {
			scene.setRoot(firstPane);
			
			//no executions to pass
			actionsForFirstPane.executeOnSubsequentDisplays(()->{});
		}
		
	}

	private class ShowImmediateParentPaneExecution implements Execution {

		public ComplementaryActions actionsForImmediateParentPane;
		public Pane immediateParentPane;
		
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			refreshNavigatorUI();
			paneNavigatorUI.getChildren().add(immediateParentPane);

			//no executions to add
			actionsForImmediateParentPane.executeOnSubsequentDisplays(()->{});
		}
		
	}
}

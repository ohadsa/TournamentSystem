package Championship.view;

import java.util.Vector;
import Championship.listeners.ChampionshipUIEventsListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChampionshipView  {	
	private Vector<ChampionshipUIEventsListener> listeners; 
	private StartChampionshipPage startChampionshipPage;

	public ChampionshipView(Stage primaryStage) {			
		listeners = new Vector<ChampionshipUIEventsListener>();
		
		StackPane spRoot = new StackPane();
		spRoot.setPadding(new Insets(30));
		
		startChampionshipPage = new StartChampionshipPage(listeners, primaryStage, spRoot);
		
		spRoot.getChildren().add(startChampionshipPage);
				
		primaryStage.setScene(new Scene(spRoot, 1300, 800));
		primaryStage.show();
	}

	public void registerListener(ChampionshipUIEventsListener newListener) {
		listeners.add(newListener);
	}

	public StartChampionshipPage getStartChampionshipPage() {
		return startChampionshipPage;
	}

}
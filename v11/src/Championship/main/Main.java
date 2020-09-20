package Championship.main;

import Championship.controller.ChampionshipController;
import Championship.model.ChampionshipManager;
import Championship.view.ChampionshipView;
import javafx.application.Application;
import javafx.stage.Stage;

// Submitted by Ohad Saada 204624209

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		ChampionshipManager theModel = new ChampionshipManager();
		ChampionshipView theView = new ChampionshipView(primaryStage);
		ChampionshipController controller = new ChampionshipController(theModel, theView);		
	}
		
}


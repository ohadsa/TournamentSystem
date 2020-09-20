package Championship.view;

import java.util.Vector;
import Championship.listeners.ChampionshipUIEventsListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class ChampionshipBorderPane extends BorderPane {	
	protected Vector<ChampionshipUIEventsListener> listeners; 
	protected Stage primaryStage;
	protected ParticipantLabel[] labelParticipantNameArray;
	
	public ChampionshipBorderPane(Vector<ChampionshipUIEventsListener> theListeners, Stage theStage) {	
		listeners = theListeners;
		primaryStage = theStage;
		
		HBox topHbox = new HBox();
		topHbox.setAlignment(Pos.CENTER);
		LabelTitle lblTopTitle = new LabelTitle("Championship");	
		topHbox.getChildren().add(lblTopTitle);
		this.setTop(topHbox);
	}
	
	protected void showErrorMessage(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();	
	}	
	
}

package Championship.view;

import java.util.Vector;
import Championship.listeners.ChampionshipUIEventsListener;
import Championship.model.ChampionshipManager;
import Championship.model.ChampionshipManager.SportType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartChampionshipPage extends ChampionshipBorderPane {
	private StackPane spRoot;
	private GameBoardPage gameBoardPage;
	private Button buttonAddParticipant;
	private Button buttonStartChampionship;
	private TextField textFieldParticipantName;
	private int currentNumberOfParticipants;
	private ToggleGroup tglRadioSportType;
	private RadioButton[] radioButtonSportTypeArray;
	
	public StartChampionshipPage(Vector<ChampionshipUIEventsListener> theListeners, Stage theStage, StackPane root) {
		super(theListeners, theStage);
		spRoot = root;
		 
		setLeftBorder();
		setRightBorder();
		setMiddleBorder();
		
		addParticipant();
		startChampionship();
	}

	private void setLeftBorder() {
		VBox leftVbox = new VBox();
		leftVbox.setAlignment(Pos.CENTER);
		leftVbox.setSpacing(40);

		labelParticipantNameArray = new ParticipantLabel[ChampionshipManager.MAX_NUMBER_OF_PARTICIPANTS];

		for (int i = 0; i < labelParticipantNameArray.length; i++) {
			labelParticipantNameArray[i] = new ParticipantLabel(Color.DARKBLUE);
			leftVbox.getChildren().add(labelParticipantNameArray[i]);
		}

		this.setLeft(leftVbox);
	}

	private void setMiddleBorder() {
		ChampionshipGridPane middleGridPane = new ChampionshipGridPane();

		Label labelParticipantName = new Label("Participant name:");
		textFieldParticipantName = new TextField();
		textFieldParticipantName.setMaxSize(122, 20);
		buttonAddParticipant = new Button("Add participant");
		buttonStartChampionship = new Button("Start Championship");

		middleGridPane.add(labelParticipantName, 0, 0);
		middleGridPane.add(textFieldParticipantName, 1, 0);
		middleGridPane.add(buttonAddParticipant, 0, 1);
		middleGridPane.add(buttonStartChampionship, 1, 1);

		this.setCenter(middleGridPane);
	}

	private void setRightBorder() {
		VBox rightVbox = new VBox();
		rightVbox.setSpacing(10);
		rightVbox.setAlignment(Pos.CENTER_LEFT);
		rightVbox.setPadding(new Insets(0, 95, 0, 0));
		tglRadioSportType = new ToggleGroup();

		SportType[] allSportTypes = SportType.values();
		radioButtonSportTypeArray = new RadioButton[allSportTypes.length];

		for (int i = 0; i < radioButtonSportTypeArray.length; i++) {
			radioButtonSportTypeArray[i] = new RadioButton(allSportTypes[i].name());
			radioButtonSportTypeArray[i].setToggleGroup(tglRadioSportType);
			rightVbox.getChildren().add(radioButtonSportTypeArray[i]);
		}
		radioButtonSportTypeArray[0].setSelected(true);
		this.setRight(rightVbox);
	}

	private void addParticipant() {
		buttonAddParticipant.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					listeners.get(0).addParticipantToUI(textFieldParticipantName.getText());
				} catch (Exception e) {
					showErrorMessage(e.getMessage());
				}
				textFieldParticipantName.clear();
			}
		});
	}

	private void startChampionship() {
		buttonStartChampionship.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				RadioButton selectedSportTypeRadio = (RadioButton) tglRadioSportType.getSelectedToggle(); 
				SportType sportType = SportType.valueOf(selectedSportTypeRadio.getText());
				try {
					listeners.get(0).startChampionshipFromUI(sportType);
					switchToGameBoardPage();
				} catch (Exception e) {
					showErrorMessage(e.getMessage());
				}
			}
		});
	}

	private void switchToGameBoardPage() {
		gameBoardPage = new GameBoardPage(listeners, labelParticipantNameArray, primaryStage);
		spRoot.getChildren().add(gameBoardPage);
		this.setVisible(false);
	}

	public void addParticipantToUI(String addedParticipant) {
		labelParticipantNameArray[currentNumberOfParticipants++].setText(addedParticipant);
	}

	protected void showErrorMessage(String msg) {
		super.showErrorMessage(msg);
	}

	public GameBoardPage getGameBoardPage() {
		return gameBoardPage;
	}

}

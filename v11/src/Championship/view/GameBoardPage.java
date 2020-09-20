package Championship.view;

import java.util.Vector;
import Championship.listeners.ChampionshipUIEventsListener;
import Championship.model.ChampionshipManager.SportType;
import Championship.model.Soccer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameBoardPage extends ChampionshipBorderPane {
	private static final int NUMBER_OF_STAGES = 3; // quarter finals ,semi finals or finals
	private static final int NUMBER_OF_GAMES = 7; // total games in the championship
	private static final int QUARTER_FINALS_GAMES = 4;
	private static final int SEMI_FINALS_GAMES = 2;
	private static final int ONE_FINAL_GAME = 1;

	private GameView[][] allGames;
	private ParticipantLabel labelChampionshipWinner;
	private Boolean[] wasGameInitiatedArray;
	private boolean isCurrentGameFinished;
	private int currentGameNumberOfRounds;
	private TextField[] tfParticipant1ScoresArray;
	private TextField[] tfParticipant2ScoresArray;
	private CheckBox[] chbParticipant1ScoresArray;
	private CheckBox[] chbParticipant2ScoresArray;
	private String currentGamePhaseName;
	private String currentGameWinner;
	private Stage currentGameWindow;
	private Label labelParticipantName1;
	private Label labelParticipantName2;
	private Label labelGamePhaseName;
	private LabelTitle labelGameSportTitle;
	private Button finishGamePhaseButton;
	private VBox playGameVBoxRoot;

	public GameBoardPage(Vector<ChampionshipUIEventsListener> theListeners, ParticipantLabel[] lblParticipantArray,
			Stage theStage) {
		super(theListeners, theStage);

		labelParticipantNameArray = lblParticipantArray;

		wasGameInitiatedArray = new Boolean[NUMBER_OF_GAMES];
		for (int i = 0; i < wasGameInitiatedArray.length; i++) {
			wasGameInitiatedArray[i] = false;
		}

		allGames = new GameView[NUMBER_OF_STAGES][];

		// quarter finals:
		allGames[0] = new GameView[QUARTER_FINALS_GAMES];
		int heightFixer = 0;
		int k = 0;
		for (int i = 0; i < allGames[0].length; i++) {
			allGames[0][i] = new GameView(0, 135 + heightFixer, 80);
			allGames[0][i].setParticipantsToLabels(labelParticipantNameArray[k++].getText(),
					labelParticipantNameArray[k++].getText());

			heightFixer += 130;
			this.getChildren().add(allGames[0][i].getMasterPane());
		}

		// semi finals:
		allGames[1] = new GameView[SEMI_FINALS_GAMES];
		heightFixer = 0;
		for (int i = 0; i < allGames[1].length; i++) {
			allGames[1][i] = new GameView(353, 170 + heightFixer, 138);
			heightFixer += 258;
			this.getChildren().add(allGames[1][i].getMasterPane());
		}

		// finals:
		allGames[2] = new GameView[ONE_FINAL_GAME];
		heightFixer = 0;
		for (int i = 0; i < allGames[2].length; i++) {
			allGames[2][i] = new GameView(709, 232 + heightFixer, 270);
			heightFixer += 386;
			this.getChildren().add(allGames[2][i].getMasterPane());
		}
		// championship winner:
		labelChampionshipWinner = new ParticipantLabel(Color.RED);

		HBox hBoxChampionshipWinner = new HBox();
		hBoxChampionshipWinner.getChildren().add(labelChampionshipWinner);
		hBoxChampionshipWinner.setLayoutX(1062);
		hBoxChampionshipWinner.setLayoutY(359);
		this.getChildren().add(hBoxChampionshipWinner);

		int gameNumber = 0;
		for (int i = 0; i < allGames.length; i++) {
			for (int j = 0; j < allGames[i].length; j++) {
				playGame(i, j, gameNumber++);
			}
		}
	}

	private void playGame(int stageIndex, int gameIndexInStage, int gameNumber) {
		allGames[stageIndex][gameIndexInStage].getPlayGameButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				initiateGame(allGames[stageIndex][gameIndexInStage].getParticipant(0),
						allGames[stageIndex][gameIndexInStage].getParticipant(1),
						getWinnerLabel(stageIndex, gameIndexInStage), gameNumber,
						allGames[stageIndex][gameIndexInStage].getPlayGameButton());
			}
		});
	}

	private void initiateGame(String participant1, String participant2, ParticipantLabel labelWinner, int gameNumber,
			Button playGameButton) {

		if (!wasGameInitiatedArray[gameNumber]) {
			try {
				listeners.get(0).initiateGameFromUI(participant1, participant2);
			} catch (Exception e) {
				showErrorMessage(e.getMessage());
				return;
			}
		}

		wasGameInitiatedArray[gameNumber] = true;

		playGameVBoxRoot = new VBox();
		playGameVBoxRoot.setSpacing(40);
		playGameVBoxRoot.setPadding(new Insets(15));
		playGameVBoxRoot.setAlignment(Pos.TOP_CENTER);

		listeners.get(0).getSportTypeToUI();

		labelGamePhaseName = new Label();
		labelGamePhaseName.setFont(new Font("Arial", 20));
		labelGamePhaseName.setTextFill(Color.DARKBLUE);
		labelGamePhaseName.setWrapText(true);

		finishGamePhaseButton = new Button("Done");
		ChampionshipGridPane scoreGridPane = createScoreGridPane(participant1, participant2);

		playGameVBoxRoot.getChildren().addAll(labelGameSportTitle, labelGamePhaseName, scoreGridPane,
				finishGamePhaseButton);

		Scene secondScene = new Scene(playGameVBoxRoot, 430, 350);
		currentGameWindow = new Stage();
		currentGameWindow.setScene(secondScene);
		currentGameWindow.initModality(Modality.WINDOW_MODAL);
		currentGameWindow.initOwner(primaryStage);
		currentGameWindow.show();

		try {
			listeners.get(0).getCurrentGamePhaseNameToUI(participant1, participant2);
			listeners.get(0).getCurrentNumberOfRoundsToUI(participant1, participant2);
		} catch (Exception e) {
			currentGameWindow.close();
			showErrorMessage(e.getMessage());
		}

		if (isPenaltyPhase(currentGamePhaseName)) {
			initiateCheckBoxScoreArray(currentGameNumberOfRounds, scoreGridPane);
		} else {
			initiateTextFieldScoreArray(currentGameNumberOfRounds, scoreGridPane);
		}

		playPhase(participant1, participant2, labelWinner, playGameButton);
	}

	private void playPhase(String participant1, String participant2, ParticipantLabel labelWinner,
			Button playGameButton) {

		finishGamePhaseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int[] score1 = new int[currentGameNumberOfRounds];
				int[] score2 = new int[currentGameNumberOfRounds];

				try {
					if (isPenaltyPhase(currentGamePhaseName)) {
						prepareScoreArrayForPendels(score1, score2);
					} else {
						prepareScoreArrayGeneral(score1, score2);
					}

					listeners.get(0).playGameFromUI(score1, score2, participant1, participant2);

					if (isCurrentGameFinished) {
						listeners.get(0).getGameWinnerToUI(participant1, participant2);
						labelWinner.setText(currentGameWinner);
						playGameButton.setVisible(false);
						currentGameWindow.close();
					} else { // game is not finished, play next phase
						ChampionshipGridPane scoreGridPane = createScoreGridPane(participant1, participant2);
						playGameVBoxRoot.getChildren().set(2, scoreGridPane);

						listeners.get(0).getCurrentGamePhaseNameToUI(participant1, participant2);
						listeners.get(0).getCurrentNumberOfRoundsToUI(participant1, participant2);

						if (isPenaltyPhase(currentGamePhaseName)) {
							initiateCheckBoxScoreArray(currentGameNumberOfRounds, scoreGridPane);
						} else {
							initiateTextFieldScoreArray(currentGameNumberOfRounds, scoreGridPane);
						}
					}
				} catch (Exception e) {
					showErrorMessage(e.getMessage());
				}
			}
		});
	}

	private ParticipantLabel getWinnerLabel(int stageIndex, int gameIndexInStage) {
		switch (stageIndex) {
		case 0: {
			if (gameIndexInStage == 0 || gameIndexInStage == 1)
				return allGames[1][0].getParticipantLabel(gameIndexInStage);
			if (gameIndexInStage == 2 || gameIndexInStage == 3)
				return allGames[1][1].getParticipantLabel(gameIndexInStage - 2);
			break;
		}
		case 1: {
			return allGames[2][0].getParticipantLabel(gameIndexInStage);
		}
		case 2: {
			return labelChampionshipWinner;
		}
		}
		return null;
	}

	private void prepareScoreArrayGeneral(int[] score1, int[] score2) throws Exception {
		for (int i = 0; i < score1.length; i++) {
			score1[i] = Integer.parseInt(tfParticipant1ScoresArray[i].getText());
			score2[i] = Integer.parseInt(tfParticipant2ScoresArray[i].getText());
		}
	}

	private void prepareScoreArrayForPendels(int[] score1, int[] score2) {
		for (int i = 0; i < score1.length; i++) {
			if (chbParticipant1ScoresArray[i].isSelected()) {
				score1[i]++;
			}

			if (chbParticipant2ScoresArray[i].isSelected()) {
				score2[i]++;
			}
		}
	}

	private void initiateCheckBoxScoreArray(int numberOfRounds, ChampionshipGridPane scoreGridPane) {
		chbParticipant1ScoresArray = new CheckBox[numberOfRounds];
		chbParticipant2ScoresArray = new CheckBox[numberOfRounds];
		for (int i = 0; i < chbParticipant1ScoresArray.length; i++) {
			chbParticipant1ScoresArray[i] = new CheckBox();
			chbParticipant2ScoresArray[i] = new CheckBox();
			scoreGridPane.add(chbParticipant1ScoresArray[i], i + 1, 0);
			scoreGridPane.add(chbParticipant2ScoresArray[i], i + 1, 1);
		}
	}

	private void initiateTextFieldScoreArray(int numberOfRounds, ChampionshipGridPane scoreGridPane) {
		tfParticipant1ScoresArray = new TextField[numberOfRounds];
		tfParticipant2ScoresArray = new TextField[numberOfRounds];

		for (int i = 0; i < tfParticipant1ScoresArray.length; i++) {
			tfParticipant1ScoresArray[i] = new TextField();
			tfParticipant1ScoresArray[i].setMaxSize(40, 20);
			tfParticipant2ScoresArray[i] = new TextField();
			tfParticipant2ScoresArray[i].setMaxSize(40, 20);
			scoreGridPane.add(tfParticipant1ScoresArray[i], i + 1, 0);
			scoreGridPane.add(tfParticipant2ScoresArray[i], i + 1, 1);
		}
	}

	private ChampionshipGridPane createScoreGridPane(String participant1, String participant2) {
		ChampionshipGridPane scoreGridPane = new ChampionshipGridPane();
		labelParticipantName1 = new Label(participant1);
		labelParticipantName2 = new Label(participant2);
		scoreGridPane.add(labelParticipantName1, 0, 0);
		scoreGridPane.add(labelParticipantName2, 0, 1);

		return scoreGridPane;
	}

	private boolean isPenaltyPhase(String currentGamePhaseName) {
		return currentGamePhaseName.equals(Soccer.FINAL_EVEN_BREAK_NAME)
				|| currentGamePhaseName.equals(Soccer.FINAL_PHASE_NAME);
	}

	public void getSportTypeToUI(SportType sportType) {
		labelGameSportTitle = new LabelTitle(sportType.name() + " Game");
	}

	public void getCurrentGamePhaseNameToUI(String currentPhaseName) {
		currentGamePhaseName = currentPhaseName;
		labelGamePhaseName.setText(currentGamePhaseName);
	}

	public void getCurrentNumberOfRoundsToUI(int currentNumberOfRounds) {
		currentGameNumberOfRounds = currentNumberOfRounds;
	}

	public void playGameFromUI(boolean isFinished) {
		isCurrentGameFinished = isFinished;
	}

	public void getGameWinnerToUI(String winner) {
		currentGameWinner = winner;
	}

	protected void showErrorMessage(String msg) {
		super.showErrorMessage(msg);
	}

}
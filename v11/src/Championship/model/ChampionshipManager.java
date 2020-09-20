package Championship.model;

import java.util.Vector;
import Championship.listeners.ChampionshipEventsListener;
import Championship.util.StringUtil;

public class ChampionshipManager {
	public static final int MAX_NUMBER_OF_PARTICIPANTS = 8;
	private static final int MAX_PARTICIPANT_NAME_LENGTH = 24;

	public enum SportType { Tennis, Basketball, Soccer };
		
	private Vector<ChampionshipEventsListener> listeners;
	private Vector<String> participants;
	private Vector<Game> games;
	private SportType sportType;
	private boolean isStarted;

	public ChampionshipManager() {
		listeners = new Vector<ChampionshipEventsListener>();
		participants = new Vector<String>();
		games = new Vector<Game>();	
	}

	public void registerListener(ChampionshipEventsListener newListener) {
		listeners.add(newListener);
	}

	public String addParticipant(String particitpantToAdd) throws Exception {
		if (MAX_NUMBER_OF_PARTICIPANTS == participants.size()) {
			throw new Exception("Cannot add more than " + MAX_NUMBER_OF_PARTICIPANTS + " participants");
		}
		if (MAX_PARTICIPANT_NAME_LENGTH < particitpantToAdd.length()) {
			throw new Exception("Name cannot be more than " + MAX_PARTICIPANT_NAME_LENGTH + " characters");
		}
		if (!StringUtil.isOnlyLettersAndSpaces(particitpantToAdd)) {
			throw new Exception("Name must consist only letters and spaces");
		}
		if (isSameName(particitpantToAdd)) {
			throw new Exception("Name already exists");
		}

		particitpantToAdd = StringUtil.toUpperCaseFirstChar(particitpantToAdd);
		participants.add(particitpantToAdd);

		fireAddParticipantEvent(particitpantToAdd);

		return particitpantToAdd;
	}

	private boolean isSameName(String particitpantToAdd) {
		String name = StringUtil.toUpperCaseFirstChar(particitpantToAdd);
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void startChampionship(SportType sportType) throws Exception {
		if (isStarted) {
			throw new Exception("Championship already started");
		}

		if (participants.size() != MAX_NUMBER_OF_PARTICIPANTS) {
			throw new Exception(
					"Cannot start championship before all " + MAX_NUMBER_OF_PARTICIPANTS + " participants are added");
		}

		if (StringUtil.isEmptyOrNull(sportType.name())) {
			throw new Exception("Choose sport type first");
		}

		this.sportType = sportType;
		isStarted = true;
	}

	public void initiateGame(String participant1, String participant2) throws Exception {
		Game gameToAdd;

		if (StringUtil.isEmptyOrNull(participant1) || StringUtil.isEmptyOrNull(participant2)) {
			throw new Exception("Must have two participants to play a game");
		}

		if (getGame(participant1, participant2) != null) {
			throw new Exception("Game was already initiated");
		}

		if (sportType == SportType.Tennis) {
			gameToAdd = new Tennis(participant1, participant2);
		} else if (sportType == SportType.Basketball) {
			gameToAdd = new Basketball(participant1, participant2);
		} else {
			gameToAdd = new Soccer(participant1, participant2);
		}

		games.add(gameToAdd);
	}

	public boolean playGame(int[] scoreArray1, int[] scoreArray2, String participant1, String participant2)
			throws Exception {
		Game theGame = getGame(participant1, participant2);
		doesGameExists(theGame);

		theGame.playGame(scoreArray1, scoreArray2);

		fireIsGameFinishedEvent(theGame.isFinished());

		return theGame.isFinished();
	}

	private void doesGameExists(Game theGame) throws Exception {
		if (theGame == null) {
			throw new Exception("Game does not exist");
		}
	}

	private Game getGame(String participant1, String participant2) {
		for (int i = 0; i < games.size(); i++) {
			if (games.get(i).isSameParticipants(participant1, participant2)) {
				return games.get(i);
			}
		}
		return null;
	}

	public String getCurrentGamePhaseName(String participant1, String participant2) throws Exception {
		String currentGamePhaseName;
		Game theGame = getGame(participant1, participant2);
		doesGameExists(theGame);

		currentGamePhaseName = theGame.getCurrentPhaseName();
		fireReturnCurrentPhaseNameEvent(currentGamePhaseName);
		return currentGamePhaseName;
	}

	public int getCurrentGameNumberOfRounds(String participant1, String participant2) throws Exception {
		int currentGameNumberOfRounds;
		Game theGame = getGame(participant1, participant2);
		doesGameExists(theGame);

		currentGameNumberOfRounds = theGame.currentNumberOfRounds;
		fireReturnCurrentNumberOfRoundsEvent(currentGameNumberOfRounds);
		return currentGameNumberOfRounds;
	}

	public String getGameWinner(String participant1, String participant2) throws Exception {
		String gameWinner;
		Game theGame = getGame(participant1, participant2);
		doesGameExists(theGame);

		gameWinner = theGame.getWinner();
		fireReturnGameWinnerEvent(gameWinner);
		return gameWinner;
	}

	public SportType getSportType() {
		fireReturnSportTypeEvent(sportType);
		return sportType;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public String getParticipant(int index) {
		return participants.get(index);
	}

	private void fireAddParticipantEvent(String participant) {
		for (ChampionshipEventsListener l : listeners) {
			l.addedParticipantToModelEvent(participant);
		}
	}

	private void fireReturnSportTypeEvent(SportType sportType) {
		for (ChampionshipEventsListener l : listeners) {
			l.returnedSportTypeFromModelEvent(sportType);
		}
	}

	private void fireReturnCurrentPhaseNameEvent(String currentGamePhaseName) {
		for (ChampionshipEventsListener l : listeners) {
			l.returnedCurrentGamePhaseNameFromModelEvent(currentGamePhaseName);
		}
	}

	private void fireReturnCurrentNumberOfRoundsEvent(int currentGameNumberOfRounds) {
		for (ChampionshipEventsListener l : listeners) {
			l.returnedCurrentNumberOfRoundsFromModelEvent(currentGameNumberOfRounds);
		}

	}

	private void fireReturnGameWinnerEvent(String gameWinner) {
		for (ChampionshipEventsListener l : listeners) {
			l.returnedGameWinnerFromModelEvent(gameWinner);
		}
	}

	private void fireIsGameFinishedEvent(boolean isGameFinished) {
		for (ChampionshipEventsListener l : listeners) {
			l.returnedGameResultFromModelEvent(isGameFinished);
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Championship: " 
				+ (sportType != null ? sportType : "") + "\nStatus: "
				+ (isStarted == true ? "started" : "not started") + "\n");			
		stringBuilder.append("The participants: \n");
		for (int i = 0; i < participants.size(); i++) {
			stringBuilder.append(participants.get(i) + "\n");
		}
		stringBuilder.append("The games: \n");
		for (int i = 0; i < games.size(); i++) {
			stringBuilder.append(games.get(i) + "\n");
		}

		return stringBuilder.toString();
	}

}

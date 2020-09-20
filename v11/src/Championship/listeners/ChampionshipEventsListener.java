package Championship.listeners;

import Championship.model.ChampionshipManager.SportType;

public interface ChampionshipEventsListener {
	void addedParticipantToModelEvent(String addedParticipant);
	void returnedSportTypeFromModelEvent(SportType sportType);
	void returnedCurrentGamePhaseNameFromModelEvent(String currentGamePhaseName);
	void returnedCurrentNumberOfRoundsFromModelEvent(int currentNumberOfRounds);
	void returnedGameWinnerFromModelEvent(String gameWinner);
	void returnedGameResultFromModelEvent(boolean isGameFinished);
}

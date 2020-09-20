package Championship.listeners;

import Championship.model.ChampionshipManager.SportType;

public interface ChampionshipUIEventsListener {
	void addParticipantToUI(String participantToAdd) throws Exception;
	void startChampionshipFromUI(SportType sportType) throws Exception;
	void initiateGameFromUI(String participant1, String participant2) throws Exception;
	void getSportTypeToUI();
	void getCurrentGamePhaseNameToUI(String participant1, String participant2) throws Exception;
	void getCurrentNumberOfRoundsToUI(String participant1, String participant2) throws Exception;
	void getGameWinnerToUI(String participant1, String participant2) throws Exception;
	void playGameFromUI(int[] score1, int[] score2, String participant1, String participant2) throws Exception;
}

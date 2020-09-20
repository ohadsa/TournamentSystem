package Championship.controller;

import Championship.listeners.ChampionshipEventsListener;
import Championship.listeners.ChampionshipUIEventsListener;
import Championship.model.ChampionshipManager;
import Championship.model.ChampionshipManager.SportType;
import Championship.view.ChampionshipView;

public class ChampionshipController implements ChampionshipEventsListener, ChampionshipUIEventsListener {
	private ChampionshipManager championshipModel;
	private ChampionshipView championshipView;
	
	public ChampionshipController(ChampionshipManager model, ChampionshipView view) {
		championshipModel = model;
		championshipView = view;
		
		championshipModel.registerListener(this);
		championshipView.registerListener(this);	
	}
	
	@Override
	public void addParticipantToUI(String participantToAdd) throws Exception {
		championshipModel.addParticipant(participantToAdd);	
	}
	
	@Override
	public void addedParticipantToModelEvent(String addedParticipant) {
		championshipView.getStartChampionshipPage().addParticipantToUI(addedParticipant);	
	}

	@Override
	public void startChampionshipFromUI(SportType sportType) throws Exception {		
		championshipModel.startChampionship(sportType);		
	}
	
	@Override
	public void initiateGameFromUI(String participant1, String participant2) throws Exception {
		championshipModel.initiateGame(participant1, participant2);	
	}

	@Override
	public void getSportTypeToUI() {
		championshipModel.getSportType();		
	}

	@Override
	public void returnedSportTypeFromModelEvent(SportType sportType) {
		championshipView.getStartChampionshipPage().getGameBoardPage().getSportTypeToUI(sportType);		
	}

	
	@Override
	public void getCurrentGamePhaseNameToUI(String participant1, String participant2) throws Exception {
		championshipModel.getCurrentGamePhaseName(participant1, participant2);		
	}

	@Override
	public void returnedCurrentGamePhaseNameFromModelEvent(String currentGamePhaseName) {
		championshipView.getStartChampionshipPage().getGameBoardPage().getCurrentGamePhaseNameToUI(currentGamePhaseName);	
	}

	@Override
	public void getCurrentNumberOfRoundsToUI(String participant1, String participant2) throws Exception {		
		championshipModel.getCurrentGameNumberOfRounds(participant1, participant2);		
	}

	@Override
	public void returnedCurrentNumberOfRoundsFromModelEvent(int currentNumberOfRounds) {
		championshipView.getStartChampionshipPage().getGameBoardPage().getCurrentNumberOfRoundsToUI(currentNumberOfRounds);	
	}

	@Override
	public void getGameWinnerToUI(String participant1, String participant2) throws Exception {		
		championshipModel.getGameWinner(participant1, participant2);				
	}

	@Override
	public void returnedGameWinnerFromModelEvent(String gameWinner) {
		championshipView.getStartChampionshipPage().getGameBoardPage().getGameWinnerToUI(gameWinner);	
	}

	@Override
	public void playGameFromUI(int[] score1, int[] score2, String participant1, String participant2) throws Exception {
		championshipModel.playGame(score1, score2, participant1, participant2);			
	}

	@Override
	public void returnedGameResultFromModelEvent(boolean isGameFinished) {
		championshipView.getStartChampionshipPage().getGameBoardPage().playGameFromUI(isGameFinished);	
	}

}



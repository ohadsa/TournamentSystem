package Championship.model;

public class Tennis extends Game {
	private static final int FIRST_NUMBER_OF_ROUNDS = 3;
	private static final int FINAL_NUMBER_OF_ROUNDS = 2;
	private static final int DIFFERENCE_TO_WIN = 3;
	private static final String FIRST_PHASE_NAME = "Round 1 - three sets";
	private static final String FINAL_PHASE_NAME = "Final round - two sets";

	public Tennis(String participant1, String participant2) {
		super(participant1, participant2);
		currentNumberOfRounds = FIRST_NUMBER_OF_ROUNDS;
		currentPhaseName = FIRST_PHASE_NAME;
	}

	@Override
	public void playGame(int[] scoreArray1, int[] scoreArray2) throws Exception {	
		super.playGame(scoreArray1, scoreArray2);
		isValidScore(scoreArray1, scoreArray2);
		calculatePhaseScore(scoreArray1, scoreArray2);
			
		if (currentPhaseName.equals(FIRST_PHASE_NAME)) {
			if (score1 - score2 == DIFFERENCE_TO_WIN) {
				isFinished = true;
				winner = participant1;

			} else if (score2 - score1 == DIFFERENCE_TO_WIN) {
				isFinished = true;
				winner = participant2;

			} else { // no winner yet, change to the final phase
				currentNumberOfRounds = FINAL_NUMBER_OF_ROUNDS;
				currentPhaseName = FINAL_PHASE_NAME;
			}

		} else if (currentPhaseName.equals(FINAL_PHASE_NAME)) {
			if (score1 > score2) {
				winner = participant1;
				isFinished = true;
			}
			if (score1 < score2) {
				winner = participant2;
				isFinished = true;
			}		
			// if it is a tie no one gets a score
		}		
	}

	@Override
	protected void calculatePhaseScore(int[] scoreArray1, int[] scoreArray2) {		
		for (int i = 0; i < scoreArray1.length; i++) {
			if (scoreArray1[i] > scoreArray2[i])
				score1++;
			if (scoreArray1[i] < scoreArray2[i])
				score2++;
		}	
	}
	
	protected void isValidScore(int[] scoreArray1, int[] scoreArray2) throws Exception {
		super.isValidScore(scoreArray1, scoreArray2);
		
		for (int i = 0; i < scoreArray1.length; i++) { // arrays are the same length
			if (scoreArray1[i] == scoreArray2[i]) {
				throw new Exception("Cannot have a tie in around");
			}
		}	
	}
		
}

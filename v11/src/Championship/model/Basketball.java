package Championship.model;

public class Basketball extends Game {
	private static final int QUARTERS = 4;
	private static final int EXTRA_QUARTER = 1;
	private static final String FIRST_PHASE_NAME = "First four quarters";
	private static final String FINAL_PHASE_NAME = "Extra quarter";

	public Basketball(String participant1, String participant2) {
		super(participant1, participant2);
		currentNumberOfRounds = QUARTERS;
		currentPhaseName = FIRST_PHASE_NAME;
	}

	@Override
	public void playGame(int[] scoreArray1, int[] scoreArray2) throws Exception {
		super.playGame(scoreArray1, scoreArray2);
		super.isValidScore(scoreArray1, scoreArray2);
		calculatePhaseScore(scoreArray1, scoreArray2);

		if (score1 > score2) {
			isFinished = true;
			winner = participant1;
		} else if (score2 > score1) {
			isFinished = true;
			winner = participant2;
		} else { // its a tie
			currentPhaseName = FINAL_PHASE_NAME;
			currentNumberOfRounds = EXTRA_QUARTER;
		}

	}

	@Override
	protected void calculatePhaseScore(int[] scoreArray1, int[] scoreArray2) {
		for (int i = 0; i < scoreArray1.length; i++) {
			score1 += scoreArray1[i];
			score2 += scoreArray2[i];
		}
	}

}

package Championship.model;

public class Soccer extends Game {
	private static final int FIRST_HALVES = 2;
	private static final int OVERTIME_HALVE = 1;
	private static final int PENALTIES = 5;
	private static final int EXTRA_PENALTIES = 1;
	private static final String FIRST_PHASE_NAME = "First two halves";
	private static final String SECOND_PHASE_NAME = "Overtime halve";
	public static final String FINAL_PHASE_NAME = "" + PENALTIES + " Penalties";
	public static final String FINAL_EVEN_BREAK_NAME = "" + EXTRA_PENALTIES + " penalty";

	public Soccer(String participant1, String participant2) {
		super(participant1, participant2);
		currentNumberOfRounds = FIRST_HALVES;
		currentPhaseName = FIRST_PHASE_NAME;
	}

	@Override
	public void playGame(int[] scoreArray1, int[] scoreArray2) throws Exception {
		super.playGame(scoreArray1, scoreArray2);

		if (currentPhaseName.equals(FINAL_PHASE_NAME) || currentPhaseName.equals(FINAL_EVEN_BREAK_NAME)) {
			isValidPenaltyScore(scoreArray1, scoreArray2);
		} else {
			super.isValidScore(scoreArray1, scoreArray2);
		}

		calculatePhaseScore(scoreArray1, scoreArray2);

		if (score1 > score2) {
			isFinished = true;
			winner = participant1;

		} else if (score2 > score1) {
			isFinished = true;
			winner = participant2;

		} else { // its a tie
			if (currentPhaseName.equals(FIRST_PHASE_NAME)) {
				currentPhaseName = SECOND_PHASE_NAME;
				currentNumberOfRounds = OVERTIME_HALVE;
			} else if (currentPhaseName.equals(SECOND_PHASE_NAME)) {
				score1 = 0;
				score2 = 0;
				currentPhaseName = FINAL_PHASE_NAME;
				currentNumberOfRounds = PENALTIES;
			} else if (currentPhaseName.equals(FINAL_PHASE_NAME)) {
				currentPhaseName = FINAL_EVEN_BREAK_NAME;
				currentNumberOfRounds = EXTRA_PENALTIES;
			}

		}

	}

	private void isValidPenaltyScore(int[] scoreArray1, int[] scoreArray2) throws Exception {
		for (int i = 0; i < scoreArray1.length; i++) {
			if (!(scoreArray1[i] == 1 || scoreArray1[i] == 0)) {
				throw new Exception("Penalties score is not valid");
			}
			if (!(scoreArray2[i] == 1 || scoreArray2[i] == 0)) {
				throw new Exception("Penalties score is not valid");
			}
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

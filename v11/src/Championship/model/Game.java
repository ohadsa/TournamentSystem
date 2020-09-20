package Championship.model;

public abstract class Game {
	protected boolean isFinished;
	protected String participant1;
	protected String participant2;
	protected String winner;
	protected String currentPhaseName;
	protected int currentNumberOfRounds;
	protected int score1;
	protected int score2;

	public Game(String p1, String p2) {
		participant1 = p1;
		participant2 = p2;
		winner = "";
		currentPhaseName = "";
	}
	
	public void playGame(int[] scoreArray1, int[] scoreArray2) throws Exception {
		if (isFinished) {
			throw new Exception("Game is already finished, cannot play");
		}
	}

	protected abstract void calculatePhaseScore(int[] scoreArray1, int[] scoreArray2);

	protected void isValidScore(int[] scoreArray1, int[] scoreArray2) throws Exception {
		isSameNumberOfScores(scoreArray1, scoreArray2);

		for (int i = 0; i < scoreArray1.length; i++) { // arrays are the same length
			if (scoreArray1[i] < 0 || scoreArray2[i] < 0) {
				throw new Exception("A score cannot be negative");
			}
		}
	}

	private void isSameNumberOfScores(int[] scoreArray1, int[] scoreArray2) throws Exception {
		if (scoreArray1.length != scoreArray2.length) {
			throw new Exception("Score arrays should be equal in length");
		}
	}
	
	protected boolean isSameParticipants(String p1, String p2) {
		if (p1.equals(participant1) && p2.equals(participant2)) {
			return true;
		}
		if (p2.equals(participant1) && p1.equals(participant2)) {
			return true;
		}
		return false;
	}

	public String getWinner() {
		return winner;
	}

	public int getCurrentNumberOfRounds() {
		return currentNumberOfRounds;
	}

	public String getCurrentPhaseName() {
		return currentPhaseName;
	}

	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName() + " " + participant1 + " VS "
				+ participant2 + (isFinished == true ? ", game finished" : ", game is not finish") + ", Winner: "
				+ (winner != "" ? winner : "Undecided yet"));

		return stringBuilder.toString();
	}

}

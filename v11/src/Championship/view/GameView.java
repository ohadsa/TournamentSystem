package Championship.view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameView {
	private ParticipantLabel labelParticipant1Name;
	private ParticipantLabel labelParticipant2Name;
	private BranchWithButton branchButtonWithLines;
	private HBox hBoxRoot;

	public GameView(int centerX, int centerY, int verticalDistance) {
		branchButtonWithLines = new BranchWithButton(centerX, centerY, verticalDistance);
		labelParticipant1Name = new ParticipantLabel(Color.DARKBLUE);
		labelParticipant2Name = new ParticipantLabel(Color.DARKBLUE);
		hBoxRoot = new HBox();
		VBox vBoxParticipants = new VBox();
		vBoxParticipants.setSpacing(verticalDistance - 29);
		vBoxParticipants.getChildren().addAll(labelParticipant1Name, labelParticipant2Name);
		hBoxRoot.getChildren().addAll(vBoxParticipants, branchButtonWithLines.getControls());
		hBoxRoot.setSpacing(20);
		hBoxRoot.setLayoutX(centerX);
		hBoxRoot.setLayoutY(centerY);
	}

	public HBox getMasterPane() {
		return hBoxRoot;
	}

	public void setParticipantsToLabels(String participant1, String participant2) {
		labelParticipant1Name.setText(participant1);
		labelParticipant2Name.setText(participant2);
	}

	public Button getPlayGameButton() {
		return branchButtonWithLines.getPlayGameButton();
	}
	
	public String getParticipant(int index) {
		if (index == 0) {
			return labelParticipant1Name.getText();
		} else
			return labelParticipant2Name.getText();
	}
	
	public ParticipantLabel getParticipantLabel(int topOrBottom) {
		if (topOrBottom == 0)
			return labelParticipant1Name;
		else
			return labelParticipant2Name;
	}
	
}

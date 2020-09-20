package Championship.view;

import javafx.scene.Group;

import javafx.scene.control.Button;

import javafx.scene.shape.Line;

public class BranchWithButton {
	private Group theControls;
	
	public BranchWithButton(int centerPointX, int centerPointY, int verticalDistance) {
		theControls = new Group();
		Line topHorizontalLine = new Line(centerPointX, centerPointY - (verticalDistance / 2), centerPointX - 70,
				centerPointY - (verticalDistance / 2));
		Line bottomHorizontalLine = new Line(centerPointX, centerPointY + (verticalDistance / 2), centerPointX - 70,
				centerPointY + (verticalDistance / 2));
		Line centerHorizontalLine = new Line(centerPointX, centerPointY, centerPointX + 100, centerPointY);
		Line verticalLine = new Line(centerPointX, centerPointY - (verticalDistance / 2), centerPointX,
				centerPointY + (verticalDistance / 2));
		
		Button playGameButton = new Button();
		playGameButton.setText("Play a game");
		playGameButton.setLayoutX(centerPointX - 30);
		playGameButton.setLayoutY(centerPointY - 14);
		
		theControls.getChildren().addAll(topHorizontalLine, bottomHorizontalLine, centerHorizontalLine, verticalLine,
				playGameButton);
	}

	public Group getControls() {
		return theControls;
	}

	public Button getPlayGameButton() {
		return (Button)theControls.getChildren().get(4);
	}

}

package Championship.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ParticipantLabel extends Label {
	
	public ParticipantLabel(Color c) {
		DropShadow dropShadow = new DropShadow(10, c);
		dropShadow.setOffsetX(3);
		dropShadow.setOffsetY(3);
		this.setEffect(dropShadow);	
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5.0), new Insets(-5.0))));
		this.setMinWidth(150);	
	}

}

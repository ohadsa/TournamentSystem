package Championship.view;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LabelTitle extends Label {
	
	public LabelTitle(String titleName) {
		this.setText(titleName);
		this.setFont(new Font("Arial", 38));
		this.setEffect(new DropShadow(15, Color.DARKBLUE));	
		this.setWrapText(true);
	}

}

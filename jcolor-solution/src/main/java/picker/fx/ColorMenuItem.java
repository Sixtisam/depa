package picker.fx;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.paint.Color;

public class ColorMenuItem extends CheckMenuItem {
	
	public ColorMenuItem(ColorModel model, String label, Color color) {
		super(label);
		setOnAction(e -> model.setColor(color));
		model.colorProperty().addListener((obs, oldValue, newValue) -> {
			setSelected(newValue.equals(color));
		});
	}

}

package jcolor.fx;

import javafx.scene.control.TextField;
import jcolor.ColorChannel;
import jcolor.ColorModel;

public class ColorTextHexField extends TextField {
	ColorTextHexField(ColorModel model, ColorChannel channel) {
		super("0");
		this.textProperty().addListener((obs, oldValue, newValue) -> {
			try {
				model.setColor(channel.modifiedColor(model.getColor(),
						Integer.parseInt(newValue, 16)));
			}
			catch (NumberFormatException e) {}
		});
		model.addColorListener(
				c -> this.setText("" + Integer.toHexString(channel.getValue(c))));
	}
}

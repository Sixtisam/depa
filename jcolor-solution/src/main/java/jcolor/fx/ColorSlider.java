package jcolor.fx;

import javafx.scene.control.Slider;
import jcolor.ColorChannel;
import jcolor.ColorModel;

public class ColorSlider extends Slider {

	public ColorSlider(ColorModel model, ColorChannel channel) {
		super(0.0, 255, 0);
		this.valueProperty().addListener((obs, oldValue, newValue) -> {
			model.setColor(channel.modifiedColor(model.getColor(), newValue.intValue()));
		});
		model.addColorListener(c -> setValue(channel.getValue(c)));
	}

}

package jcolor.fx;

import java.awt.Color;

import javafx.scene.control.RadioButton;
import jcolor.ColorModel;

public class ColorRadioButton extends RadioButton {

	public ColorRadioButton(ColorModel model, String label, Color color){
		super(label);
		setOnAction(e -> model.setColor(color));
		model.addColorListener(c -> setSelected(c.equals(color)));
	}

}

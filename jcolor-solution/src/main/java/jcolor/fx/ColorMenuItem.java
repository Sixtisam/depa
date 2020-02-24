package jcolor.fx;

import java.awt.Color;

import javafx.scene.control.CheckMenuItem;
import jcolor.ColorModel;

public class ColorMenuItem extends CheckMenuItem {
	
	public ColorMenuItem(ColorModel model, String label, Color color) {
		super(label);
		setOnAction(e -> model.setColor(color));
		model.addColorListener(c -> setSelected(c.equals(color)));
	}

}

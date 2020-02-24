package jcolor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorModel {
	private Color color;
	private final List<ColorListener> listeners = new ArrayList<>();

	public void addColorListener(ColorListener l) {
		listeners.add(l);
	}

	public void removeColorListener(ColorListener l) {
		listeners.remove(l);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if (!color.equals(this.color)) {
			this.color = color;

			for (ColorListener l : listeners) {
				l.colorValueChanged(color);
			}
		}
	}
}

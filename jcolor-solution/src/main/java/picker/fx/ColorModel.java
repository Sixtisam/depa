package picker.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class ColorModel {
	private final IntegerProperty red = new SimpleIntegerProperty(0);
	private final IntegerProperty green = new SimpleIntegerProperty(0);
	private final IntegerProperty blue = new SimpleIntegerProperty(0);

	private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);

	public ColorModel() {
		color.addListener((observable, oldValue, newValue) -> {
			Color c = newValue;
			red.set((int) Math.round(c.getRed() * 255));
			green.set((int) Math.round(c.getGreen() * 255));
			blue.set((int) Math.round(c.getBlue() * 255));
		});
		red.addListener((observable, oldValue, newValue) -> {
			int val = Math.max(0, Math.min(255, newValue.intValue()));
			color.set(Color.rgb(val, getGreen(), getBlue()));
		});
		green.addListener((observable, oldValue, newValue) -> {
			int val = Math.max(0, Math.min(255, newValue.intValue()));
			color.set(Color.rgb(getRed(), val, getBlue()));
		});
		blue.addListener((observable, oldValue, newValue) -> {
			int val = Math.max(0, Math.min(255, newValue.intValue()));
			color.set(Color.rgb(getRed(), getGreen(), val));
		});
	}

	public IntegerProperty redProperty() {
		return red;
	}

	public int getRed() {
		return red.get();
	}

	public void setRed(int red) {
		this.red.set(red);
	}

	public IntegerProperty greenProperty() {
		return green;
	}

	public int getGreen() {
		return green.get();
	}

	public void setGreen(int green) {
		this.green.set(green);
	}

	public IntegerProperty blueProperty() {
		return blue;
	}

	public int getBlue() {
		return blue.get();
	}

	public void setBlue(int blue) {
		this.blue.set(blue);
	}

	public ObjectProperty<Color> colorProperty() {
		return color;
	}

	public Color getColor() {
		return color.get();
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

}

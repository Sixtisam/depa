package picker.fx;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

public class ColorRadioButton extends RadioButton {

	public ColorRadioButton(ColorModel model, String label, Color color){
		super(label);
		setOnAction(e -> model.setColor(color));
		model.colorProperty().addListener((obs, oldValue, newValue) -> {
			setSelected(newValue.equals(color));
		});
	}

}

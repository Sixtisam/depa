package picker.fx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.NumberStringConverter;

public class ColorPickerUI extends VBox {

	public ColorPickerUI(final ColorModel model) {

		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setMnemonicParsing(true);
		exitItem.setOnAction(e -> Platform.exit());
		
		Menu menuFile = new Menu("File");
		menuFile.getItems().add(exitItem);

		MenuItem redItem = new ColorMenuItem(model, "Red", Color.RED);
		MenuItem greenItem = new ColorMenuItem(model, "Lime", Color.LIME);
		MenuItem blueItem = new ColorMenuItem(model, "Blue", Color.BLUE);
		MenuItem yellowItem = new ColorMenuItem(model, "Yellow", Color.YELLOW);
		MenuItem cyanItem = new ColorMenuItem(model, "Cyan", Color.CYAN);
		MenuItem orangeItem = new ColorMenuItem(model, "Orange", Color.ORANGE);
		MenuItem whiteItem = new ColorMenuItem(model, "White", Color.WHITE);
		MenuItem blackItem = new ColorMenuItem(model, "Black", Color.BLACK);

		Menu menuAttributes = new Menu("Attributes");
		menuAttributes.getItems().addAll(redItem, greenItem, blueItem, yellowItem, cyanItem, orangeItem, whiteItem, blackItem);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menuFile, menuAttributes);

		GridPane colorPicker = getGridPane(model);

		getChildren().addAll(menuBar, colorPicker);
	}
	
	private GridPane getGridPane(ColorModel model) {
		Slider redSlider = new Slider(0.0, 255, 0);
		redSlider.valueProperty().bindBidirectional(model.redProperty());

		Slider greenSlider = new Slider(0.0, 255, 0);
		greenSlider.valueProperty().bindBidirectional(model.greenProperty());

		Slider blueSlider = new Slider(0.0, 255, 0);
		blueSlider.valueProperty().bindBidirectional(model.blueProperty());

		TextField redValue = new TextField("0");
		redValue.textProperty().bindBidirectional(model.redProperty(), new NumberStringConverter());

		TextField greenValue = new TextField("0");
		greenValue.textProperty().bindBidirectional(model.greenProperty(), new NumberStringConverter());

		TextField blueValue = new TextField("0");
		blueValue.textProperty().bindBidirectional(model.blueProperty(), new NumberStringConverter());

		TextField redValueHex = new TextField("not yet");
		TextField greenValueHex = new TextField("not yet");
		TextField blueValueHex = new TextField("not yet");

		Rectangle display = new Rectangle();
		display.fillProperty().bind(model.colorProperty());
		
		ColorRadioButton redRadio = new ColorRadioButton(model, "Red", Color.RED);
		ColorRadioButton limeRadio = new ColorRadioButton(model, "Lime", Color.LIME);
		ColorRadioButton blueRadio = new ColorRadioButton(model, "Blue", Color.BLUE);
		ColorRadioButton yellowRadio = new ColorRadioButton(model, "Yellow", Color.YELLOW);
		ColorRadioButton cyanRadio = new ColorRadioButton(model, "Cyan", Color.CYAN);
		ColorRadioButton orangeRadio = new ColorRadioButton(model, "Orange", Color.ORANGE);
		ColorRadioButton whiteRadio = new ColorRadioButton(model, "White", Color.WHITE);
		ColorRadioButton blackRadio = new ColorRadioButton(model, "Black", Color.BLACK);

		Button darkerButton = new Button("Darker");
		darkerButton.setOnAction(e -> model.setColor(model.getColor().darker()));
		ObjectBinding<Color> d = Bindings.createObjectBinding(() -> model.getColor().darker(), model.colorProperty());
		darkerButton.disableProperty().bind(model.colorProperty().isEqualTo(d));
		
		Button brighterButton = new Button("Brighter");
		brighterButton.setOnAction(e -> model.setColor(model.getColor().brighter()));
		ObjectBinding<Color> b = Bindings.createObjectBinding(() -> model.getColor().brighter(), model.colorProperty());
		brighterButton.disableProperty().bind(model.colorProperty().isEqualTo(b));

		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);

		darkerButton.setPrefWidth(100);
		brighterButton.setPrefWidth(100);

		display.setHeight(100);
		display.setStroke(Color.BLACK);
		display.widthProperty().bind(grid.widthProperty().divide(3));

		ColumnConstraints sliderColumn = new ColumnConstraints(200);
		sliderColumn.setHalignment(HPos.CENTER);
		ColumnConstraints valueColumn = new ColumnConstraints(100);
		valueColumn.setHalignment(HPos.CENTER);
		ColumnConstraints valueHexColumn = new ColumnConstraints(100);
		valueHexColumn.setHalignment(HPos.CENTER);
		grid.getColumnConstraints().addAll(sliderColumn, valueColumn, valueHexColumn);

		Label spacer = new Label("");
		VBox radioBox = new VBox(redRadio, limeRadio, blueRadio, yellowRadio, cyanRadio, orangeRadio, whiteRadio, blackRadio);
		VBox buttonBox = new VBox(darkerButton, spacer, brighterButton);
		grid.addRow(1, redSlider, redValue, redValueHex);
		grid.addRow(2, greenSlider, greenValue, greenValueHex);
		grid.addRow(3, blueSlider, blueValue, blueValueHex);
		grid.addRow(4, display, radioBox, buttonBox);

		grid.setPrefHeight(grid.getPrefHeight() + 200);

		
		return grid;
	}


}

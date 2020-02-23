package net.skeeks.fhnw.lektion1;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.function.Function;

/**
 * Das GUI ist folgendermassen organisiert:
 * - VBox - RootComponent
 * -- VBox - Enthält 3x eine HBox mit den Slidern und Eingabefeldern.
 * -- HBox - Enthält Rechteck sowie die Buttons je als Plane/Rectangle
 * <p>
 * Für R,G,B gibt es ein Integer-Property.
 * Dies wird an alle Felder gebunden (via bindBidirectional)
 * Für das Hex-Eingabefeld wird ein eigener Konverter benötigt.
 * <p>
 * Für das Rechteck mit der dargestellten Farbe gibt es 3 Listener (pro R,G,B) welcher jeweils die Farbe auf dem Rechteck aktualisiert.
 *
 * @author Samuel Keusch
 */
public class ColorChooser extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent appPane = new RootComponent();
        Scene scene = new Scene(appPane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setHeight(400);
        primaryStage.setWidth(400);
        primaryStage.setTitle("[DEPA] ColorChooser");
        primaryStage.show();
    }

    public static class RootComponent extends VBox {
        public SimpleIntegerProperty red = new SimpleIntegerProperty();
        public SimpleIntegerProperty blue = new SimpleIntegerProperty();
        public SimpleIntegerProperty green = new SimpleIntegerProperty();


        public HBox createHorizontalColorBox(SimpleIntegerProperty propertyToBindTo) {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.setPadding(new Insets(4));
            box.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(box, Priority.ALWAYS);

            Slider slider = new Slider(0, 255, 100);
            slider.valueProperty().bindBidirectional(propertyToBindTo);
            TextField tf = new TextField();
            tf.textProperty().bindBidirectional(propertyToBindTo, new NumberStringConverter());
            TextField hexField = new TextField();
            hexField.textProperty().bindBidirectional(propertyToBindTo, new StringHexConverter());

            box.getChildren().addAll(slider, tf, hexField);

            return box;
        }

        public HBox createLowerBox() {
            HBox box = new HBox();

            box.getChildren().add(createRectangle());

            box.getChildren().add(createColorButtonsBox());
            box.getChildren().add(createColorModifierBox());
            box.setPrefWidth(120);
            box.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(box, Priority.ALWAYS);
            return box;
        }

        public Node createRectangle() {
            Rectangle rect = new Rectangle();
            rect.setWidth(160);
            rect.setHeight(120);
            VBox wrapper = new VBox();
            wrapper.setPadding(new Insets(10));
            wrapper.getChildren().add(rect);

            // add listener for update
            InvalidationListener colorListener = observable -> {
                rect.setFill(Color.rgb(red.intValue(), green.intValue(), blue.intValue()));
            };

            red.addListener(colorListener);
            green.addListener(colorListener);
            blue.addListener(colorListener);
            return wrapper;
        }

        public VBox createColorButtonsBox() {
            VBox box = new VBox();
            box.setSpacing(4);
            String[] colors = new String[]{"red", "green", "blue", "yellow", "cyan", "orange", "black"};

            for (String color : colors) {
                Button btn = new Button(color);
                btn.setPrefWidth(100);
                btn.setOnAction(event -> {
                    Color c = Color.web(btn.getText());
                    red.setValue(c.getRed() * 255);
                    green.setValue(c.getGreen() * 255);
                    blue.setValue(c.getBlue() * 255);
                });
                box.getChildren().add(btn);
            }
            box.setFillWidth(true);
            HBox.setHgrow(box, Priority.ALWAYS);
            box.setAlignment(Pos.CENTER);
            return box;
        }

        public Node createColorModifierBox() {
            VBox colorModifierButtonsBox = new VBox();
            colorModifierButtonsBox.setSpacing(4);
            colorModifierButtonsBox.setAlignment(Pos.CENTER);
            colorModifierButtonsBox.getChildren().addAll(
                    createColorModifierButton("Darker", c -> c.darker()),
                    createColorModifierButton("Brighter", c -> c.brighter()));
            colorModifierButtonsBox.setFillWidth(true);
            colorModifierButtonsBox.setPrefWidth(120);
            colorModifierButtonsBox.setAlignment(Pos.TOP_CENTER);
            HBox.setHgrow(colorModifierButtonsBox, Priority.ALWAYS);
            return colorModifierButtonsBox;
        }

        public Button createColorModifierButton(String text, Function<Color, Color> modifier) {
            Button brighterBtn = new Button(text);
            brighterBtn.setPrefWidth(100);
            brighterBtn.setOnAction(event -> {
                Color newColor = modifier.apply(Color.rgb(red.get(), green.get(), blue.get()));
                red.setValue(newColor.getRed() * 255);
                green.setValue(newColor.getGreen() * 255);
                blue.setValue(newColor.getBlue() * 255);
            });
            return brighterBtn;
        }

        public MenuBar createMenuBar() {
            MenuBar mb = new MenuBar();
            Menu fileMenu = new Menu("File");

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.setOnAction(event -> {
                System.exit(0);
            });
            fileMenu.getItems().add(exitItem);
            mb.getMenus().add(fileMenu);
            return mb;
        }

        public RootComponent() {
            getChildren().add(createMenuBar());
            getChildren().add(createHorizontalColorBox(red));
            getChildren().add(createHorizontalColorBox(green));
            getChildren().add(createHorizontalColorBox(blue));

            getChildren().add(createLowerBox());
        }
    }

    public static class StringHexConverter extends StringConverter<Number> {

        @Override
        public String toString(Number object) {
            if (object == null) return null;
            return Integer.toHexString(object.intValue()).toUpperCase();
        }

        @Override
        public Number fromString(String string) {
            if (string == null) return null;
            return Integer.parseInt(string, 16);
        }
    }
}

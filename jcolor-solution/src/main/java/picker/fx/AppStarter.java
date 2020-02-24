package picker.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ColorModel model = new ColorModel();

		Scene scene = new Scene(new ColorPickerUI(model));
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Color Picker");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

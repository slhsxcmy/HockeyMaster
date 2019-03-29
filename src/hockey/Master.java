package hockey;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Master extends Application {
	@Override
	public void start(Stage stage) {
		try {

			// FXML
			Parent root = FXMLLoader.load(getClass().getResource("/hockey/Menu.fxml"));

			/*
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			
			int screenWidth = (int) Math.round(bounds.getWidth());
			int screenHeight = (int) Math.round(bounds.getHeight());
			int gameWidth = screenHeight / 2;
			int gameHeight = screenHeight;
			
			primaryStage.setWidth(screenWidth);
			primaryStage.setHeight(screenHeight);
			*/
			Scene scene = new Scene(root);
			// CSS
			scene.getStylesheets().add(getClass().getResource("Menu.css").toExternalForm());
						
			
			stage.setScene(scene);
			
			//primaryStage.setFullScreen(true);
			//primaryStage.setMaximized(true);

//			System.out.println(scene.getWidth());
//			System.out.println(scene.getHeight());
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

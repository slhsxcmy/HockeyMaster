package hockey.java;


import hockey.java.controller.GameController;
import hockey.java.controller.LoggedController;
import hockey.java.controller.LoginController;
import hockey.java.controller.MenuController;
import hockey.java.controller.SignupController;
import hockey.java.controller.StatsController;
import hockey.java.front.Network;
import hockey.java.front.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// ClientProgram
public class Hockey extends Application {
	
	private static Network network = new Network();
	private static User self = null; // instantiate when playAsGuest or Register or Login
	
	private static Stage primaryStage;
	private static Scene menuScene, loginScene, signupScene, loggedScene, statsScene, gameScene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
// https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
		
		
			
		// FXML
		//Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
		
		/*** All Scenes Start ***/
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Parent menuParent = (Parent)menuLoader.load();	
		menuScene = new Scene(menuParent);
		
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Login.fxml"));
		Parent loginParent = (Parent)loginLoader.load();	
		loginScene = new Scene(loginParent);
		
		FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Signup.fxml"));
		Parent signupParent = (Parent)signupLoader.load();	
		signupScene = new Scene(signupParent);
		
		FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Logged.fxml"));
		Parent loggedParent = (Parent)loggedLoader.load();	
		loggedScene = new Scene(loggedParent);

		FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Stats.fxml"));
		Parent statsParent = (Parent)statsLoader.load();	
		statsScene = new Scene(statsParent);
		
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Game.fxml"));
		Parent gameParent = (Parent) gameLoader.load();	
		gameScene = new Scene(gameParent);
		/*** All Scenes End ***/
		
		/*** All Controllers Start ***/
		MenuController menuController = menuLoader.getController();
		LoginController loginController = loginLoader.getController();
		SignupController signupController = signupLoader.getController();
		LoggedController loggedController = loggedLoader.getController();
		StatsController statsController = statsLoader.getController();
		GameController  gameController = gameLoader.getController();
		/*** All Controllers End ***/
		
		primaryStage.setScene(menuScene);		
		primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static Network getNetwork() {
		return network;
	}

	public static void setNetwork(Network network) {
		Hockey.network = network;
	}

	public static User getSelf() {
		return self;
	}

	public static void setSelf(User self) {
		Hockey.self = self;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Hockey.primaryStage = primaryStage;
	}

	public static Scene getMenuScene() {
		return menuScene;
	}

	public static void setMenuScene(Scene menuScene) {
		Hockey.menuScene = menuScene;
	}

	public static Scene getLoginScene() {
		return loginScene;
	}

	public static void setLoginScene(Scene loginScene) {
		Hockey.loginScene = loginScene;
	}

	public static Scene getSignupScene() {
		return signupScene;
	}

	public static void setSignupScene(Scene signupScene) {
		Hockey.signupScene = signupScene;
	}

	public static Scene getLoggedScene() {
		return loggedScene;
	}

	public static void setLoggedScene(Scene loggedScene) {
		Hockey.loggedScene = loggedScene;
	}

	public static Scene getStatsScene() {
		return statsScene;
	}

	public static void setStatsScene(Scene statsScene) {
		Hockey.statsScene = statsScene;
	}

	public static Scene getGameScene() {
		return gameScene;
	}

	public static void setGameScene(Scene gameScene) {
		Hockey.gameScene = gameScene;
	}

	
	
	
	
}
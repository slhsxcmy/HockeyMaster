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
	private static User user = new User(); 
	
	public static final int NUMSCENES = 6;
	private static Scene[] scenes = new Scene[NUMSCENES];
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Hockey.primaryStage = primaryStage;
// https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
		
		
			
		// FXML
		//Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));
		
		/*** All Scenes Start ***/
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Parent menuParent = (Parent)menuLoader.load();	
		menuScene = new Scene(menuParent);
		scenes[0] = menuScene;
		
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Login.fxml"));
		Parent loginParent = (Parent)loginLoader.load();	
		loginScene = new Scene(loginParent);
		scenes[1] = loginScene;
		
		FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Signup.fxml"));
		Parent signupParent = (Parent)signupLoader.load();	
		signupScene = new Scene(signupParent);
		scenes[2] = signupScene;
		
		FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Logged.fxml"));
		Parent loggedParent = (Parent)loggedLoader.load();	
		loggedScene = new Scene(loggedParent);
		scenes[3] = loggedScene;
		
		FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Stats.fxml"));
		Parent statsParent = (Parent)statsLoader.load();	
		statsScene = new Scene(statsParent);
		scenes[4] = statsScene;
		
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Game.fxml"));
		Parent gameParent = (Parent) gameLoader.load();	
		gameScene = new Scene(gameParent);
		scenes[5] = gameScene;
		/*** All Scenes End ***/
		
		/*** All Controllers Start ***/
		menuController = menuLoader.getController();
		loginController = loginLoader.getController();
		signupController = signupLoader.getController();
		loggedController = loggedLoader.getController();
		statsController = statsLoader.getController();
		gameController = gameLoader.getController();
		/*** All Controllers End ***/
		
		primaryStage.setScene(menuScene);		
		primaryStage.show();
		
		// DEBUG
		//primaryStage.setScene(gameScene);
		
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static Stage primaryStage;
	private static Scene menuScene, loginScene, signupScene, loggedScene, statsScene, gameScene;
	private static MenuController menuController;
	private static LoginController loginController;
	private static SignupController signupController;
	private static LoggedController loggedController;
	private static StatsController statsController;
	private static GameController  gameController;
	
	
	public static Network getNetwork() {
		return network;
	}

	public static void setNetwork(Network network) {
		Hockey.network = network;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User self) {
		Hockey.user = self;
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

	public static MenuController getMenuController() {
		return menuController;
	}

	public static void setMenuController(MenuController menuController) {
		Hockey.menuController = menuController;
	}

	public static LoginController getLoginController() {
		return loginController;
	}

	public static void setLoginController(LoginController loginController) {
		Hockey.loginController = loginController;
	}

	public static SignupController getSignupController() {
		return signupController;
	}

	public static void setSignupController(SignupController signupController) {
		Hockey.signupController = signupController;
	}

	public static LoggedController getLoggedController() {
		return loggedController;
	}

	public static void setLoggedController(LoggedController loggedController) {
		Hockey.loggedController = loggedController;
	}

	public static StatsController getStatsController() {
		return statsController;
	}

	public static void setStatsController(StatsController statsController) {
		Hockey.statsController = statsController;
	}

	public static GameController getGameController() {
		return gameController;
	}

	public static void setGameController(GameController gameController) {
		Hockey.gameController = gameController;
	}

	
	
	
	
}
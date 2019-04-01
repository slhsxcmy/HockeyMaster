package hockey.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application{

    Pane playfield;

    Player p1 = new Player("p1");
    Player p2 = new Player("p2");
    Striker s1;
    //Striker s2 = new Striker();
    Puck puck = new Puck();
    double friction = 0.5;
    
//    public Game(String p1Name, String p2Name) {
//    	this.p1 = new Player("p1");
//        this.p2 = new Player("p2"); 
//    	s1 = new Striker();
//    	//s2 = new Striker();
//    	puck = new Puck();
//    }
    
    @Override
    public void start(Stage stage) {
    	 // create containers
    	 s1 = new Striker();
    	 BorderPane root = new BorderPane();
         StackPane layerPane = new StackPane();
         // playfield for our strikers
         playfield = new Pane();
         layerPane.getChildren().addAll(playfield);
         root.setCenter(layerPane);
         Scene scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
         stage.setScene(scene);
         stage.show();
         // capture mouse position
         scene.addEventFilter(MouseEvent.ANY, e -> {
             p1.getMouse().set(e.getX(), e.getY(), 0);
         });
         // process all strikers
         AnimationTimer loop = new AnimationTimer() {
             @Override
             public void handle(long now) {
                 // move
                 s1.step(p1.getMouse());
                 //s2.step(p1.getMouse());
                 s1.checkBoundaries();
                 //s2.checkBoundaries();
                 if (collision()) {
                 	puck.recalculate();
                 }
                 puck.step(friction);
                 if (collision()) {
                 	puck.recalculate();
                 }
                 // update in fx scene
                 s1.display();
                 //s2.display();
                 puck.display();
             }
         };
         loop.start();
    }
    
    public boolean collision() {
    	return puck.checkBoundaries() || puck.checkCollision(s1); //|| puck.checkCollision(s2);
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
}

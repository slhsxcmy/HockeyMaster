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

    public Player p1;
    public Player p2;
    public Striker s1;
    public Striker s2;
    public Puck puck;
    Goal goal1;
    Goal goal2;
    double friction;
    
    @Override
    public void start(Stage stage) {
//    	 p1 = new Player("p1");
//    	 p2 = new Player("p2");
    	 s1 = new Striker();
    	 s2 = new Striker();
    	 puck = new Puck();

    	 goal1 = new Goal("1", puck);
    	 goal2 = new Goal("2", puck);
    	 friction = .99;

    	 // create containers
    	 BorderPane root = new BorderPane();
         StackPane layerPane = new StackPane();
         // playfield for our strikers
         playfield = new Pane();
         layerPane.getChildren().addAll(playfield);
         root.setCenter(layerPane);
         Scene scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
         stage.setScene(scene);
         stage.show();
         playfield.getChildren().add(s1);
         playfield.getChildren().add(puck);
         playfield.getChildren().add(goal1);
         playfield.getChildren().add(goal2);
         // capture mouse position
         scene.addEventFilter(MouseEvent.ANY, e -> {
             p1.getMouse().set(e.getX(), e.getY());
         });
         // process all strikers
         AnimationTimer loop = new AnimationTimer() {
             @Override
             public void handle(long now) {
                 // move
                 s1.step(p1.getMouse());
                 //s2.step(p1.getMouse());
                 s1.checkBoundaries();
                 goal1.goalDetection("1");
                 goal2.goalDetection("2");
                 //s2.checkBoundaries();
                 puck.checkBoundaries();

                 puck.collision(s1);
                 puck.collision(s2);
                	//System.out.println("recalculating");

                 puck.step(friction);
                 // update in fx scene
                 s1.display();
                 //s2.display();
                 puck.display();
                 goal1.display();
                 goal2.display();
             }
         };
         loop.start();
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
}

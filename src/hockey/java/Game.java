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

    Player p1;
    Player p2;
    Striker s1;
    Striker s2;
    Puck puck;
    double friction;
    
    @Override
    public void start(Stage stage) {
    	 p1 = new Player("p1");
    	 p2 = new Player("p2");
    	 s1 = new Striker();
    	 s2 = new Striker();
    	 puck = new Puck();
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
             }
         };
         loop.start();
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
}

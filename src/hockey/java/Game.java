package hockey.java;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{

    Pane playfield;

    Player p1;
    Player p2;
    Striker s1;
    Striker s2;
    Puck puck;
    Goal goal1;
    Goal goal2;
    Walls walls1, walls2;
    double friction;
    
    public void displayWinner(Pane playfield, String name) {
    	
    }
    
    @Override
    public void start(Stage stage) {
    	 p1 = new Player("p1");
    	 p2 = new Player("p2");
    	 s1 = new Striker();
    	 //s2 = new Striker();
    	 puck = new Puck();

    	 goal1 = new Goal(1, puck);
    	 goal2 = new Goal(2, puck);
    	 walls1 = new Walls(1);
    	 walls2 = new Walls(2);
    	 friction = .985;
    	 
    	 Text p1s = new Text(Integer.toString(p1.getScore()));
     	 p1s.setFont(Font.font ("Verdana", 50));
     	 p1s.setFill(Color.RED);
     	 p1s.setX(350);
     	 p1s.setY(400);

     	 Text p2s = new Text(Integer.toString(p2.getScore()));
    	 p2s.setFont(Font.font ("Verdana", 50));
    	 p2s.setFill(Color.RED);
    	 p1s.setX(350);
    	 p2s.setY(300);
    	 
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
         playfield.getChildren().add(walls1);
         playfield.getChildren().add(walls2);
         playfield.getChildren().add(s1);
         playfield.getChildren().add(puck);
         playfield.getChildren().add(goal1);
         playfield.getChildren().add(goal2);
         playfield.getChildren().add(p1s);
         playfield.getChildren().add(p2s);
         //display static shapes
         goal1.display();
         goal2.display();
         walls1.display();
         walls2.display();
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
                 if (puck.checkBoundaries()) {
                	 //striker can't overlap with puck
                 }

                 puck.collision(s1);
                 //puck.collision(s2);
                 puck.step(friction);
                 // update in fx scene
                 s1.display();
                 //s2.display();
                 puck.display();
                 if (goal1.goalDetection(1)) {
                	 p1.score();
                	 p1s.setText(Integer.toString(p1.getScore()));
                	 stage.show();
                	 s1.reset(1);
                	 //s2.reset(2);
                	 
                 }
                 if (goal2.goalDetection(2)) {
                	 p2.score();
                	 p1s.setText(Integer.toString(p2.getScore()));
                	 stage.show();
                	 s1.reset(1);
                	 //s2.reset(2);
                 }
                 if (p1.getScore() == 7) {
                	 Text text = new Text(p1.getUsername() + " wins!");
                 	 text.setFont(Font.font ("Verdana", 50));
                 	 text.setFill(Color.RED);
                 	 text.setY(350);
                 	 playfield.getChildren().add(text);
                 	 stage.show();
                	 stop();
                 }
                 if (p2.getScore() == 7) {
                	 Text text = new Text(p2.getUsername() + " wins!");
                	 text.setFont(Font.font ("Verdana", 50));
                 	 text.setFill(Color.RED);
                 	 text.setY(350);
                 	 playfield.getChildren().add(text);
                 	 stage.show();
                	 stop();
                 } 
                 p1.displayScore(1);
                 p2.displayScore(2);
             }
         };
         loop.start();
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
}

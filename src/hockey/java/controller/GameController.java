package hockey.java.controller;

import java.util.Random;

import hockey.java.Hockey;
import hockey.java.front.CenterCircle;
import hockey.java.front.Goal;
import hockey.java.front.Midline;
import hockey.java.front.Player;
import hockey.java.front.PowerUp;
import hockey.java.front.PowerUpPuckSize;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.Walls;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameController {
	@FXML
	BorderPane borderPane;
	
	@FXML
	StackPane stackPane;
	
	Pane playfield;
    Player p1;
    Player p2;
    Striker s1;
    Striker s2;
    Puck puck;
    Goal goal1;
    Goal goal2;
    Walls walls1, walls2;
    Midline mid;
    CenterCircle center;
    double friction;
    
	public void init() {
		 System.out.println("init game start");
	    	
	   	 p1 = new Player("p1", 1);
	   	 p2 = new Player("p2", 2);
	   	 s1 = new Striker(p1);
	   	 //s2 = new Striker();
	   	
	   	 //u1 = new User();
	   	 //u2 = new User();
	   	 //u1.initStriker();
	   	 //u2.initStriker();
	   	
	   	 puck = new Puck();
	
	   	 //goal1 = new Goal(1, puck, u1.getStriker().getPlayer());
	   	 //goal2 = new Goal(2, puck, u2.getStriker().getPlayer());
	   	 goal1 = new Goal(1, puck, p1);
	   	 goal2 = new Goal(2, puck, p2);
	   	 walls1 = new Walls(1);
	   	 walls2 = new Walls(2);
	
	   	 mid = new Midline();
	   	 center = new CenterCircle();
	   	 friction = .988;
	   	 
	   	 //Text p1s = new Text(Integer.toString(u1.getStriker().getPlayer().getScore()));
	   	 Text p1s = new Text(Integer.toString(p1.getScore()));
	   	 p1s.setFont(Font.font ("Verdana", 50));
	   	 p1s.setFill(Color.RED);
	   	 p1s.setX(350);
	   	 p1s.setY(400);
	
	   	 //Text p2s = new Text(Integer.toString(u2.getStriker().getPlayer().getScore()));
	   	 Text p2s = new Text(Integer.toString(p2.getScore()));
	   	 p2s.setFont(Font.font ("Verdana", 50));
	   	 p2s.setFill(Color.RED);
	   	 p2s.setX(350);
	   	 p2s.setY(335);
	   	 
	   	 // create containers // playfield for our strikers 
	     playfield = new Pane();
	     borderPane.getChildren().addAll(playfield);
	     borderPane.setCenter(stackPane);
	     
	     playfield.getChildren().add(walls1);
	     playfield.getChildren().add(walls2);
	     playfield.getChildren().add(mid);
	     playfield.getChildren().add(center);
	     //playfield.getChildren().add(u1.getStriker());
	     playfield.getChildren().add(s1);
	     playfield.getChildren().add(puck);
	     playfield.getChildren().add(goal1);
	     playfield.getChildren().add(goal2);
	     playfield.getChildren().add(p1s);
	     playfield.getChildren().add(p2s);
	     //display static shapes
	     center.display();
	     goal1.display();
	     goal2.display();
	     walls1.display();
	     walls2.display();
	     PowerUp pu = new PowerUp();
	     PowerUpPuckSize puckPU = new PowerUpPuckSize();
		 playfield.getChildren().add(pu);
		 playfield.getChildren().add(puckPU);
	     mid.display();
        

    	 System.out.println("init game end");
    	
    	 if(Hockey.getGameScene() == null) System.out.println("null");
    	 else System.out.println("not null");
    	 // capture mouse position
    	 Hockey.getGameScene().addEventFilter(MouseEvent.ANY, e -> {
	       	 //u1.getStriker().getPlayer().getMouse().set(e.getX(), e.getY());
	       	 p1.getMouse().set(e.getX(), e.getY());
    	 });
    	 // process all strikers
    	 AnimationTimer loop = new AnimationTimer() {
       	 int time = 0;
       	 Random r = new Random();
       	 int ran = (int) (r.nextDouble() * 1000);
       	 	@Override
            public void handle(long now) {
       	 		// move
	           	pu.display();
	           	puckPU.display();
                s1.step(p1.getMouse(), mid);
                //s2.step(p1.getMouse());
                s1.checkBoundaries(puck);
                
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
               	 Hockey.getPrimaryStage().show();
               	 s1.reset(1);
               	 mid.reset();
               	 puck.resetSize();
               	 //s2.reset(2);
                }
                if (goal2.goalDetection(2)) {
               	 p2.score();
               	 p2s.setText(Integer.toString(p2.getScore()));
               	Hockey.getPrimaryStage().show();
               	 s1.reset(1);
               	 mid.reset();
               	 puck.resetSize();
               	 //s2.reset(2);
                }
                if (p1.getScore() == 7) {
               	 //Text text = new Text("  " + p1.getUsername() + " wins!");
                	 //text.setFont(Font.font ("Verdana", 50));
                	 //text.setFill(Color.RED);
                	 //text.setY(350);
                	 //playfield.getChildren().add(text);
               	 //display message based on winner or loser
                	Hockey.getPrimaryStage().show();
               	 stop();
                }
                if (p2.getScore() == 7) {
               	 //Text text = new Text("  " + p2.getUsername() + " wins!");
               	 //text.setFont(Font.font ("Verdana", 50));
                	 //text.setFill(Color.RED);
                	 //text.setY(350);
                	 //playfield.getChildren().add(text);
               	 //display message based on winner or loser
                	Hockey.getPrimaryStage().show();
               	 stop();
                } 
                puck.collision(mid, pu);
                puck.collision(puckPU);
                time++;
                if (time == ran) {
               	 time = 0;
               	 int choose = new Random().nextInt() % 2;
               	 if (choose == 0) {
               		 if (pu.hidden() && mid.inMiddle()) {
                   		 pu.reset();
                   	 }
               	 }
               	 else {
               		 if (puckPU.hidden() && puck.width == 30) {
                   		 puckPU.reset(puck);
                   	 }
               	 }
                }
            }
        };
        loop.start();
	}
    
}

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

public class GameController {
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private StackPane stackPane;
	
	private Pane playfield;
	
    private Player selfPlayer;
    private Player otherPlayer;
    private Striker selfStriker;
    private Striker otherStriker;
    private Puck puck;
    private Goal otherGoal;
    private Goal selfGoal;
    private Walls walls1, walls2;
    private Midline mid;
    private CenterCircle center;
    private double friction;
    
	public void init() {
		 System.out.println("init game start");
	    	
	   	 selfPlayer = new Player("selfPlayer", 1);
	   	 otherPlayer = new Player("otherPlayer", 2);
	   	 selfStriker = new Striker(selfPlayer);
	   	 otherStriker = new Striker(otherPlayer);
	   	 puck = new Puck();
	
	   	 otherGoal = new Goal(1, puck, selfPlayer);
	   	 selfGoal = new Goal(2, puck, otherPlayer);
	   	 walls1 = new Walls(1);
	   	 walls2 = new Walls(2);
	
	   	 mid = new Midline();
	   	 center = new CenterCircle();
	   	 friction = .988;
	   	 
	   	 //Text p1s = new Text(Integer.toString(u1.getStriker().getPlayer().getScore()));
	   	 Text p1s = new Text(Integer.toString(selfPlayer.getScore()));
	   	 p1s.setFont(Font.font ("Verdana", 50));
	   	 p1s.setFill(Color.RED);
	   	 p1s.setX(350);
	   	 p1s.setY(400);
	
	   	 //Text p2s = new Text(Integer.toString(u2.getStriker().getPlayer().getScore()));
	   	 Text p2s = new Text(Integer.toString(otherPlayer.getScore()));
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
	     playfield.getChildren().add(otherStriker);
	     playfield.getChildren().add(selfStriker);
	     playfield.getChildren().add(puck);
	     playfield.getChildren().add(otherGoal);
	     playfield.getChildren().add(selfGoal);
	     playfield.getChildren().add(p1s);
	     playfield.getChildren().add(p2s);
	     //display static shapes
	     center.display();
	     otherGoal.display();
	     selfGoal.display();
	     walls1.display();
	     walls2.display();
	     PowerUp pu = new PowerUp();
	     PowerUpPuckSize puckPU = new PowerUpPuckSize();
		 playfield.getChildren().add(pu);
		 playfield.getChildren().add(puckPU);
	     mid.display();
       
    	 // capture mouse position
    	 Hockey.getGameScene().addEventFilter(MouseEvent.ANY, e -> {
	       	 //u1.getStriker().getPlayer().getMouse().set(e.getX(), e.getY());
	       	 selfPlayer.getMouse().set(e.getX(), e.getY());
    	 });
    	
    	 System.out.println("init game end");
     	
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
                selfStriker.step(selfPlayer.getMouse(), mid);
                selfStriker.display();
                otherStriker.display();
                puck.display();
               
                // send selfStriker to server
                Hockey.getNetwork().getClient().sendTCP(selfStriker);
            }
        };
        loop.start();
	}
    
}

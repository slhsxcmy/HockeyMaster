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
import hockey.java.packet.PacketStriker;
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
	private BorderPane borderPane;

	@FXML
	private StackPane stackPane;

	private Pane playfield;

	
    //private static boolean started = false;
    
    private static Striker selfStriker;
    private static Striker otherStriker;
    private static Puck puck;
    private static Goal otherGoal;
    private static Goal selfGoal;
    private static Walls walls1, walls2;
    private static Midline mid;
    private static CenterCircle center;
    private static PowerUp pu;
    private static PowerUpPuckSize puckPU;
    
	public void init(int playerId) {
		 System.out.println("init game start");
	    	
	   	 selfStriker = new Striker(new Player(playerId));
	   	 otherStriker = new Striker(new Player(3-playerId));
	   	 puck = new Puck();
	
	   	 otherGoal = new Goal(1, puck, selfStriker.getPlayer());
	   	 selfGoal = new Goal(2, puck, otherStriker.getPlayer());
	   	 walls1 = new Walls(1);
	   	 walls2 = new Walls(2);
	
	   	 mid = new Midline();
	   	 center = new CenterCircle();
	   	 
	   	 pu = new PowerUp();
	     puckPU = new PowerUpPuckSize();
		 
	   	 
	   	 //Text p1s = new Text(Integer.toString(u1.getStriker().getPlayer().getScore()));
	   	 Text p1s = new Text(Integer.toString(selfStriker.getPlayer().getScore()));
	   	 p1s.setFont(Font.font ("Verdana", 50));
	   	 p1s.setFill(Color.RED);
	   	 p1s.setX(350);
	   	 p1s.setY(400);
	
	   	 //Text p2s = new Text(Integer.toString(u2.getStriker().getPlayer().getScore()));
	   	 Text p2s = new Text(Integer.toString(otherStriker.getPlayer().getScore()));
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
	     playfield.getChildren().add(pu);
		 playfield.getChildren().add(puckPU);
	     mid.display();
       
    	 // capture mouse position
    	 Hockey.getGameScene().addEventFilter(MouseEvent.ANY, e -> {
	       	 //u1.getStriker().getPlayer().getMouse().set(e.getX(), e.getY());
	       	 selfStriker.getPlayer().getMouse().set(e.getX(), e.getY());
    	 });

    	 System.out.println("init game end");
     	
    	
       
	}
	
	public void gameLoop() {
		
		
   	 	AnimationTimer loop = new AnimationTimer() {
	      	 Random r = new Random();
	      	 int ran = (int) (r.nextDouble() * 1000);
	      	 @Override
	      	 public void handle(long now) {
      	 		 // move
	      		 System.out.println(mid.getLocation());
	      		 pu.display();
	           	 puckPU.display();
	           	 selfStriker.step(selfStriker.getPlayer().getMouse(), mid);
	           	 selfStriker.checkBoundaries(puck);
	           	 selfStriker.display();
	           	 otherStriker.display();
	           	 puck.display();
               
	           	 // TODO check collison with wall
	           	 
	           	 // TODO check collison with midline
	           	 
	           	 // send selfStriker to server
	           	 // System.out.println("before sending PacketStriker"); 
	           	 System.out.println("Sending PacketStriker from id = " + selfStriker.getPlayer().getPlayerID());
	           	 Hockey.getNetwork().getClient().sendTCP(new PacketStriker(selfStriker.getPlayer().getPlayerID(),selfStriker.getLocation().x,selfStriker.getLocation().y,selfStriker.getVelocity().x,selfStriker.getVelocity().y));
	           	 // System.out.println("after sending PacketStriker");
               
                
           }
       };
       System.out.println("starting game loop");
       loop.start();
       
       
	}

	public static Striker getSelfStriker() {
		return selfStriker;
	}

	public static void setSelfStriker(Striker selfStriker) {
		GameController.selfStriker = selfStriker;
	}

	public static Striker getOtherStriker() {
		return otherStriker;
	}

	public static void setOtherStriker(Striker otherStriker) {
		GameController.otherStriker = otherStriker;
	}

	public static Puck getPuck() {
		return puck;
	}

	public static void setPuck(Puck puck) {
		GameController.puck = puck;
	}

}

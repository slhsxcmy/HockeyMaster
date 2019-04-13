package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal extends Pane {
	
	private PVector location;
	private PVector puckLocation;
	private Puck currPuck;
	private boolean goalDetected;
	private String name;
	private Player player;
	
	double width = 110;
	double height = Settings.BOARDER_HEIGHT;
	double xstart = 145;
	double bottomYstart = Settings.SCENE_HEIGHT - Settings.BOARDER_HEIGHT;
	
	Rectangle goal;
	
	Goal(int num, Puck puck, Player p){
		currPuck = puck;
		this.player = p;
		puckLocation = puck.getLocation();
		if(num == 1) {
			location = new PVector(xstart, 0);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
		}
		else {
			location = new PVector(xstart, bottomYstart);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
			
		}
		getChildren().add(goal);
	}
	
	//According to this player 1 should be on the bottom and he is scoing on is goal1
	void goalDetection(int num){
		if(num == 1) {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y < location.y+(height/2))
			{
				goalDetected = true;
				player.score();
				currPuck.resetPuckBottom();
				currPuck.display();
				System.out.println("GOAL1");
			}
		}
		else {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y > location.y-(height/2))
			{
				goalDetected = true;
				player.score();
				currPuck.resetPuckTop();
				currPuck.display();
				System.out.println("GOAL2");
			}
		}
	}
	
	public PVector getGoalLoc() {
		return location;
	}
	
	public void display() {
        relocate(location.x, location.y);
    }

}

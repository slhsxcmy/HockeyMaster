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
	
	double width = 110;
	double height = 12;
	
	Rectangle goal;
	
	Goal(String name, Puck puck){
		currPuck = puck;
		puckLocation = puck.getLocation();
		if(name.equals("1")) {
			location = new PVector(145, 0);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
		}
		else {
			location = new PVector(145, 689);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
			
		}
		getChildren().add(goal);
	}
	
	void goalDetection(String name){
		if(name.equals("1")) {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y < location.y+(height/2))
			{
				System.out.println("GOAL1");
			}
		}
		else {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y > location.y+(height/2))
			{
				System.out.println("GOAL2");
			}
		}
	}
	
	public void display() {
        relocate(location.x, location.y);
    }
	
}

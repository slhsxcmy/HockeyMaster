package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class PowerUpGoalSize extends Pane implements PowerUp {

	private PVector location;
	private boolean hidden;
	
	double width = 20;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;

	public void show(double x, double y) {
		move(x, y);
		hidden = false;
	}

	
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		
	}
}

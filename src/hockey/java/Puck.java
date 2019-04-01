package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	
	private PVector location;
    private PVector velocity;

    double width = 30;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
	
	public Puck() {
		location = new PVector(400, 200, 0);
        velocity = new PVector(0, 0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLUE);
        circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
	}
	
	public void step(double friction) {
		location.add(velocity);
		velocity.mult(friction);
	}
	
	public boolean checkBoundaries() {
		//todo
        if (location.x > Settings.SCENE_WIDTH) {

        } 
        else if (location.x < 0) {
            
        }

        if (location.y > Settings.SCENE_HEIGHT) {
            
        } 
        else if (location.y < 0) {
            
        }
        return false;
    }
	
	public boolean checkCollision(Object o) {
		//todo
		return false;
	}
	
	public void recalculate() {
		//todo
		velocity = new PVector(0, 0, 0);
	}
	
	public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector velocity() {
    	return velocity;
    }

}

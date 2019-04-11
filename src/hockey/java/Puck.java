package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	private double mass;
	
	private PVector location;
    private PVector velocity;

    double width = 30;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
    
    int collisioncounter = 0;
	
	public Puck() {
		mass = 5;
		
		location = new PVector(200, 400);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.3));

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
	
	public boolean collision(Striker s) {
		double px = location.x;
		double py = location.y;
		double sx = s.getLocation().x;
		double sy = s.getLocation().y;
		double sr = s.getRadius();
		if (Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
//			System.out.println("collision " + collisioncounter);
//			collisioncounter++;
			System.out.println(velocity.x + " " + velocity.y);
			return true;
		}
		return false;
	}
	
	public void recalculate(Striker s) {
		velocity.add(s.getVelocity());
	}
	
	public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector getVelocity() {
    	return velocity;
    }

}

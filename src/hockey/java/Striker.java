package hockey.java;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Striker extends Pane {
	
	private double mass;

    private PVector location;
    private PVector velocity;
    
    private Random ran = new Random();

    double width = 45;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;

    Circle circle;
	
	public Striker() {
		mass = 10;
		
		location = new PVector(ran.nextDouble() * width, ran.nextDouble() * height);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLUE);
        circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
	}
	
	public void step(PVector mouse) {
		velocity = PVector.sub(mouse, location, velocity);
        location.copy(mouse);
    }

    public void checkBoundaries() {

        if (location.x > Settings.SCENE_WIDTH-(width/2)-Settings.BOARDER_HEIGHT) {
            location.x = Settings.SCENE_WIDTH-(width/2)-Settings.BOARDER_HEIGHT;
        } else if (location.x < 0 +(width/2)+Settings.BOARDER_HEIGHT) {
            location.x = 0+(width/2)+Settings.BOARDER_HEIGHT;
        }

        if (location.y > Settings.SCENE_HEIGHT-(width/2)-Settings.BOARDER_HEIGHT) {
        	location.y = Settings.SCENE_HEIGHT-(width/2)-Settings.BOARDER_HEIGHT;
        } else if (location.y < 0 +(width/2)+Settings.BOARDER_HEIGHT) {
        	location.y = 0+(width/2)+Settings.BOARDER_HEIGHT;

        }
    }

    public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector getLocation() {
    	return location;
    }
    
    public PVector getVelocity() {
    	return velocity;
    }
    
    public double getRadius() {
    	return radius;
    }
    
    public double getMass() {
    	return mass;
    }
    
}

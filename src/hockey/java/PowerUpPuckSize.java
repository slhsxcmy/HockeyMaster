package hockey.java;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUpPuckSize extends Pane{
	
	private PVector location;
	private boolean hidden;
	
	double width = 20;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
    
	public PowerUpPuckSize() {
		Random r = new Random();
		location = new PVector(0, 0);
		location.x = r.nextDouble() * (Settings.SCENE_WIDTH - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT;
		location.y = r.nextDouble() * (Settings.SCENE_HEIGHT - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT;
		circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        hidden = false;

        circle.setStroke(Color.ORANGE);
        circle.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
        
	}
	
	public void display() {
		relocate(location.x - centerX, location.y - centerY);
	}
	
	public void activate(Puck p) {
		//change puck size here
		p.changePuckSize(10);
		move(-100, -100);
		hidden = true;
	}

	public PVector getLocation() {
		return location;
	}

	public double getRadius() {
		return radius;
	}
	
	public void move(double x, double y) {
		location.x = x;
		location.y = y;
		display();
	}
	
	public void reset(Puck p) {
		Random r = new Random();
		move(r.nextDouble() * (Settings.SCENE_WIDTH - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT, 
				r.nextDouble() * (Settings.SCENE_HEIGHT - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT);
		p.changePuckSize(30);
		hidden = false;
	}
	
	public boolean hidden() {
		return hidden;
	}

}

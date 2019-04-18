package hockey.java.front;

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
		location = new PVector(0, 0);
		location.x = -100;
		location.y = -100;
		circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        hidden = true;

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
		hide();
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
		move(r.nextDouble() * (BoardSettings.SCENE_WIDTH - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT, 
				r.nextDouble() * (BoardSettings.SCENE_HEIGHT - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT);
		hidden = false;
	}
	
	public void hide() {
		move(-100, -100);
		hidden = true;
	}
	
	public boolean hidden() {
		return hidden;
	}

}

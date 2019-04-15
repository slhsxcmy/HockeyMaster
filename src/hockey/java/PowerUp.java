package hockey.java;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUp extends Pane{
	
	private PVector location;
	private boolean hidden;
	
	double width = 20;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;

    Circle circle;
	
	public PowerUp() {
		location = new PVector(0, 0);

		location.x = -100;
		location.y = -100;
		circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        hidden = true;

        circle.setStroke(Color.GREEN);
        circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
	}
	
	public void display() {
		relocate(location.x - centerX, location.y - centerY);
	}
	
	public void activate(Midline m, Striker s) {
		int id = s.getPlayer().getPlayerID();
		if (id == 1) {
			m.move((m.startingY*0.5)-15);
			s.updateMidlineMult(0.5);
		}
		else {
			m.move((m.startingY*1.5)+15);
			s.updateMidlineMult(1.5);
		}
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
	
	public void reset() {
		Random r = new Random();
		move(r.nextDouble() * (Settings.SCENE_WIDTH - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT, 
				r.nextDouble() * (Settings.SCENE_HEIGHT - (2 * Settings.BOARDER_HEIGHT)) + Settings.BOARDER_HEIGHT);
		hidden = false;
	}
	
	public boolean hidden() {
		return hidden;
	}
	
}

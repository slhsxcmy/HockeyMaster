package hockey.java;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Striker extends Pane {
	
	private double mass;

    private PVector location;
    private PVector velocity;
    
    //required to keep player on his side
    private Player player;
    
    private Random ran = new Random();

    double width = 45;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    double mult = 1;

    Circle circle;
	
	public Striker(Player player) {
		this.player = player;
		
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
	
	public void step(PVector mouse, Midline mid) {
		velocity = PVector.sub(mouse, location, velocity);
		if (player.getPlayerID() == 1 && location.y == mid.getLocation() + mid.getHeight() + radius - 1 || 
				player.getPlayerID() == 2 && location.y == mid.getLocation() - mid.getHeight() - radius + 1) {
			velocity.y = 0;
		}
        location.copy(mouse);
    }
	
	public void checkBoundaries() {
		/*//TODO
		//CUrrently mouse goes across half line and physics is very off
		//PUCK SPEEDS UP TO MAX
		//player1 gets the bottom half
		if(player.getPlayerID() == 1) {
			if (location.x > Settings.SCENE_WIDTH-radius-Settings.BOARDER_HEIGHT) {
				location.x = Settings.SCENE_WIDTH-radius-Settings.BOARDER_HEIGHT;
			} else if (location.x < 0 +radius+Settings.BOARDER_HEIGHT) {
				location.x = 0+radius+Settings.BOARDER_HEIGHT;
			}

			if (location.y > Settings.SCENE_HEIGHT-radius-Settings.BOARDER_HEIGHT) {
				location.y = Settings.SCENE_HEIGHT-radius-Settings.BOARDER_HEIGHT;
			}
			//if the striker hits the midline
			else if (location.y < (radius+(Settings.SCENE_HEIGHT/2)-2+(Settings.BOARDER_HEIGHT/2))*mult) {
				location.y = (radius+(Settings.SCENE_HEIGHT/2)-2+(Settings.BOARDER_HEIGHT/2))*mult;
			}
		}
		else {
			if (location.x > Settings.SCENE_WIDTH-radius-Settings.BOARDER_HEIGHT) {
				location.x = Settings.SCENE_WIDTH-radius-Settings.BOARDER_HEIGHT;
			} else if (location.x < 0 +radius+Settings.BOARDER_HEIGHT) {
				location.x = 0+radius+Settings.BOARDER_HEIGHT;
			}
			//TODO
			//WE HAVE TO TEST THIS WHEN SERVER IS RUNNIGN
			//if striker 2 hits the midline
			if (location.y > (radius-(Settings.SCENE_HEIGHT/2)-2)*mult) {
				location.y = radius-(Settings.SCENE_HEIGHT/2)-2;
			} else if (location.y < (0 +radius+Settings.BOARDER_HEIGHT)*mult) {
				location.y = 0+radius+Settings.BOARDER_HEIGHT;
			}
		} */
		
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
	public void updateMidlineMult(double m) {
		mult = m;
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
    
    public Player getPlayer() {
    	return player;
    }
    
    public void reset(int player) {
    	mult = 1;
    	if (player == 1) {
    		location.x = 200;
    		location.y = 600;
    	}
    	else {
    		location.x = 200;
    		location.y = 200;
    	}
    	velocity.x = 0;
    	velocity.y = 0;
    }
}

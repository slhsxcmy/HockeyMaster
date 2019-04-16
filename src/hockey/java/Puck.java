package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	private double mass;
	boolean allowMove = true;
	
	public int connID;
	public PVector location;
    public PVector velocity;

    double width = 30;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
    
    int collisioncounter = 0;
    
    private Striker lastHit;
	
	public Puck() {
		mass = 5;
		
		location = new PVector(200, 350);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
        
        lastHit = null;
	}
	
	public void step(double friction) {
		location.add(velocity);
		velocity.mult(friction);
	}
	
	public boolean checkBoundaries() {
		boolean onBoundary = false;
		/*//todo
		//if puck is in the goal, keep it moving
		//Alot of constants here.. be careful of changing goal size
		if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y-radius < 0+Settings.BOARDER_HEIGHT))
		{}
		else if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y+radius > (Settings.SCENE_HEIGHT-Settings.BOARDER_HEIGHT)))
		{}
		else {	*/
	        if (location.x > Settings.SCENE_WIDTH - radius - Settings.BOARDER_HEIGHT) {
	        	location.x = Settings.SCENE_WIDTH - radius - Settings.BOARDER_HEIGHT - 1;
	        	velocity.x *= -1;
	        	onBoundary = true;
	        } 
	        else if (location.x < Settings.BOARDER_HEIGHT + radius) {
	        	location.x = radius + Settings.BOARDER_HEIGHT + 1;
	        	velocity.x *= -1;
	        	onBoundary = true;
	        }
	
	        if (location.y > Settings.SCENE_HEIGHT - radius - Settings.BOARDER_HEIGHT) {
	        	location.y = Settings.SCENE_HEIGHT - radius - Settings.BOARDER_HEIGHT - 1;
	            velocity.y *= -1;
	            onBoundary = true;
	        } 
	        else if (location.y < radius + Settings.BOARDER_HEIGHT) {
	        	location.y = radius + Settings.BOARDER_HEIGHT + 1;
	        	velocity.y *= -1;
	        	onBoundary = true;
	        }
		//}
		return onBoundary;
    }
	
	public void collision(Midline m, PowerUp pu) {
		double px = location.x;
		double py = location.y;
		double pux = pu.getLocation().x;
		double puy = pu.getLocation().y;
		double pur = pu.getRadius();
		if (Math.sqrt((px - pux) * (px - pux) + (py - puy) * (py - puy)) <= radius + pur) {
			pu.activate(m, lastHit);
		}
	}
	
	public void collision(PowerUpPuckSize pu) {
		double px = location.x;
		double py = location.y;
		double pux = pu.getLocation().x;
		double puy = pu.getLocation().y;
		double pur = pu.getRadius();
		if (Math.sqrt((px - pux) * (px - pux) + (py - puy) * (py - puy)) <= radius + pur) {
			pu.activate(this);
		}

	}
	
	public boolean collision(Striker s) {
		double px = location.x;
		double py = location.y;
		double sx = s.getLocation().x;
		double sy = s.getLocation().y;
		double sr = s.getRadius();
		if (Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
			//if contact with the left
			if(px < sx && py > (sy - .5 * sr) && py < (sy + .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px + radius + sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("LLEEEEEEEEFFFFFFFFFTTTTTTTTTTT");
				}
			}
			//top left
			if(px < sx && py > (sy - sr) && py < (sy - .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px + radius + sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("TOPPPPPPPPPP LLEEEEEEEEFFFFFFFFFTTTTTTTTTTT");
				}
			}
			//top
			else if(py < sy && px > (sx - .5 * sr) && px < (sx + .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py + radius + sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("TOPPPPPPPPPPPPP");
				}
			}
			//top right
			if(px > sx && py > (sy - sr) && py < (sy - .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px + radius + sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("TOPPPPPPPPPP RIIIIGGGGGGHTTT");
				}
			}
			//right
			else if(px > sx && py > (sy - .5 * sr) && py < (sy + .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px - radius - sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("RIIIIIGGGGGHHHHTTTT");
				}
			}
			//bottom right
			else if(py > sy && px > (sx + .5 * sr) && px < (sx + sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py - radius - sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("BOOOOTTTTTTOOOOOMMMMMM RIIIIGGGGGHHHHHHTTT");
				}
			}
			//bottom
			else if(py > sy && px > (sx - .5 * sr) && px < (sx + .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py - radius - sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("BOOOOTTTTTTOOOOOMMMMMM");
				}
			}
			//bottom left
			else if(py > sy && px > (sx - sr) && px < (sx - .5 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py - radius - sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					System.out.println("BOOOOTTTTTTOOOOOMMMMMM LLLLEEEEEEEFFFFFFFTTTTT");
				}
			}
			location.x = px;
			location.y = py;
			s.getLocation().x = sx;
			s.getLocation().y = sy;
			recalculate(s);
			lastHit = s;
			return true;
		}
		return false;
	}
	
	public void recalculate(Striker s) {
		PVector sV = new PVector(s.getVelocity().x, s.getVelocity().y);
		PVector pV = new PVector(velocity.x, velocity.y);
		if (sV.x == 0 && sV.y == 0) {
			pV.mult(-1);
		}
		else {
			pV.mult(mass - s.getMass());
			sV.mult(2 * s.getMass());
			pV.add(sV);
			pV.div(mass + s.getMass());
//			sV = new PVector(s.getVelocity().x, s.getVelocity().y);
//			sV.mult(-1);
//			s.setVelocity(sV);
//			s.step();
		}
		
		velocity.copy(pV);
		velocity.limit(30);
	}
	
	public void changePuckSize(double size) {
		this.width = size;
		this.height = width;
		this.radius = width/2;
		circle.setRadius(10/2);
		display();

	}
	
	public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector getVelocity() {
    	return velocity;
    }
    
    public PVector getLocation() {
    	return location;
    }
    
    public void move(double x, double y) {
    	location.x = x;
    	location.y = y;
    	velocity.x = 0;
    	velocity.y = 0;
    }
    
    public void resetSize() {
		this.width = 30;
		this.height = width;
		this.radius = width/2;
		circle.setRadius(30/2);
		display();
    }

}

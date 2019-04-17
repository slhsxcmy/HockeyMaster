package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	private double mass;
	boolean allowMove = true;
	
	private PVector location;
    private PVector velocity;

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
			//hypotneuse divided by root 2 
			double diag = (radius + sr)/(1.41421356);
			//if contact with the left
			if(px < sx && py >= (sy - .4 * sr) && py <= (sy + .4 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px + radius + sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("LLEEEEEEEEFFFFFFFFFTTTTTTTTTTT");
				}
			}
			//top
			else if(py < sy && px >= (sx - .4 * sr) && px <= (sx + .4 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py + radius + sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("TOPPPPPPPPPPPPP");
				}
			}
			//right
			else if(px > sx && py >= (sy - .4 * sr) && py <= (sy + .4 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(px - radius - sr, sy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("RIIIIIGGGGGHHHHTTTT");
				}
			}
			//bottom
			else if(py > sy && px >= (sx - .4 * sr) && px <= (sx + .4 * sr)) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					s.setPosition(sx, py - radius - sr);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("BOOOOTTTTTTOOOOOMMMMMM");
				}
			}
			//top left
			else if(px < sx && py < sy) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					double dx = sx-px+1;
					double dy = sy-py+1;
					s.setPosition((px+dx), py+dy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("TOPPPPPPPPPP LLEEEEEEEEFFFFFFFFFTTTTTTTTTTT");
				}
			}
			
			//top right
			else if(px > sx && py < sy) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					double dx = px-sx+1;
					double dy = sy-py+1;
					s.setPosition((px - dx) , py + dy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("TOPPPPPPPPPP RIIIIGGGGGGHTTT");
				}
			}
			
			//bottom right
			else if(px > sx && py > sy) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					double dx = px-sx+1;
					double dy = py-sy+1;
					s.setPosition((px - dx), py - dy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("BOOOOTTTTTTOOOOOMMMMMM RIIIIGGGGGHHHHHHTTT");
				}
			}
			
			//bottom left
			else if(px < sx && py > sy) {
				while(Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
					double dx = sx-px+1;
					double dy = py-sy+1;
					s.setPosition((px + dx), py - dy);
					px = location.x;
					py = location.y;
					sx = s.getLocation().x;
					sy = s.getLocation().y;
					//System.out.println("BOOOOTTTTTTOOOOOMMMMMM LLLLEEEEEEEFFFFFFFTTTTT");
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
		pV.mult(0.85);
		velocity.copy(pV);
		velocity.limit(25);
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

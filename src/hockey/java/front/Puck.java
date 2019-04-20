package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	private double mass;
	boolean allowMove = true;
	
	public int connID;
	public PVector location;
    public PVector velocity;

    public double width = 30;
    public double height = width;
    public double centerX = width / 2.0;
    public double centerY = height / 2.0;
    public double radius = width / 2.0;
    
    Circle circle;
    
    private Striker lastHit;
    
    //private boolean onWall;
	
	public Puck() {
		mass = 5;
		
		location = new PVector(200, 350);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.8));

        getChildren().add(circle);
        
        lastHit = null;
        
        //onWall = false;
	}
	
	public void step(double friction) {
		location.add(velocity);
		velocity.mult(friction);
	}
	
	
	public void checkPuckWalls() {
		if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y-radius < 0+BoardSettings.BOARDER_HEIGHT))
		{}
		else if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y+radius > (BoardSettings.SCENE_HEIGHT-BoardSettings.BOARDER_HEIGHT)))
		{}
		else {
	        if (location.x > BoardSettings.SCENE_WIDTH - radius - BoardSettings.BOARDER_HEIGHT) {
	        	location.x = BoardSettings.SCENE_WIDTH - radius - BoardSettings.BOARDER_HEIGHT - 1;
	        	velocity.x *= -1;
	        	
	        } 
	        else if (location.x < BoardSettings.BOARDER_HEIGHT + radius) {
	        	location.x = radius + BoardSettings.BOARDER_HEIGHT + 1;
	        	velocity.x *= -1;
	        	
	        }
	
	        if (location.y > BoardSettings.SCENE_HEIGHT - radius - BoardSettings.BOARDER_HEIGHT) {
	        	location.y = BoardSettings.SCENE_HEIGHT - radius - BoardSettings.BOARDER_HEIGHT - 1;
	            velocity.y *= -1;

	        } 
	        else if (location.y < radius + BoardSettings.BOARDER_HEIGHT) {
	        	location.y = radius + BoardSettings.BOARDER_HEIGHT + 1;
	        	velocity.y *= -1;

	        }
		}

	}
	
	// check wall hit
	/*public boolean checkBoundaries() {
		onWall = false;
		//todo
		//if puck is in the goal, keep it moving
		//Alot of constants here.. be careful of changing goal size
		if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y-radius < 0+BoardSettings.BOARDER_HEIGHT))
		{}
		else if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y+radius > (BoardSettings.SCENE_HEIGHT-BoardSettings.BOARDER_HEIGHT)))
		{}
		else {
	        if (location.x > BoardSettings.SCENE_WIDTH - radius - BoardSettings.BOARDER_HEIGHT) {
	        	location.x = BoardSettings.SCENE_WIDTH - radius - BoardSettings.BOARDER_HEIGHT - 1;
	        	velocity.x *= -1;
	        	// Try setting vel to 0 on second wall hit
	        	//if(onWall) velocity.x = 0;
	        	onWall = true;
	        } 
	        else if (location.x < BoardSettings.BOARDER_HEIGHT + radius) {
	        	location.x = radius + BoardSettings.BOARDER_HEIGHT + 1;
	        	velocity.x *= -1;
	        	// Try setting vel to 0 on second wall hit
	        	//if(onWall) velocity.x = 0;
	        	onWall = true;
	        }
	
	        if (location.y > BoardSettings.SCENE_HEIGHT - radius - BoardSettings.BOARDER_HEIGHT) {
	        	location.y = BoardSettings.SCENE_HEIGHT - radius - BoardSettings.BOARDER_HEIGHT - 1;
	            velocity.y *= -1;
	         // Try setting vel to 0 on second wall hit
	        	//if(onWall) velocity.y = 0;
	            onWall = true;
	        } 
	        else if (location.y < radius + BoardSettings.BOARDER_HEIGHT) {
	        	location.y = radius + BoardSettings.BOARDER_HEIGHT + 1;
	        	velocity.y *= -1;
	        	// Try setting vel to 0 on second wall hit
	        	//if(onWall) velocity.y = 0;
	        	onWall = true;
	        }
		}
		return onWall;
    }
    */
	
	// hit change midline location powerup
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
	
	// hit powerup change puck size
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
	
	// collision with striker
	public boolean collision(Striker s) {
		double px = location.x;
		double py = location.y;
		double pr = radius;
		double sx = s.getLocation().x;
		double sy = s.getLocation().y;
		double sr = s.getRadius();

		//System.out.println((px - sx) * (px - sx) + (py - sy) * (py - sy) - (pr + sr) * (pr + sr));
		if ((px - sx) * (px - sx) + (py - sy) * (py - sy) - (pr + sr) * (pr + sr) <= 0) {
			//System.out.println("collision");
			return true;
		} else {
			return false;
		}

	}

	// called in collision to recalculate movement 
	public void recalculate(Striker s) {
		// 1 = puck; 2 = striker

		float acc = 1;
		float max = 10;
		
		double m1 = mass;
		double m2 = s.getMass();
		
		PVector v1 = new PVector(this.getVelocity().x, this.getVelocity().y);
		PVector v2 = new PVector(s.getVelocity().x, s.getVelocity().y);

		PVector v1c = new PVector(PVector.sub(v1, v2));
		
		PVector c1 = this.getLocation();
		PVector c2 = s.getLocation();
		
		PVector n = PVector.normalize(PVector.sub(c2,c1));
		PVector t = PVector.normal(n);

		System.out.println("Normal : " + n.x + ", " + n.y);
		System.out.println("Tangent: " + t.x + ", " + t.y);

		PVector v1cn = PVector.mult(n, PVector.dot(n,v1c)); 
		PVector v1ct = PVector.mult(t, PVector.dot(t, v1c));
		
								/* reverse velocity       , force out puck       */
		PVector v1cnp = PVector.add(PVector.mult(v1cn, -1), PVector.mult(n, -1*acc)); // striker stays
		//PVector v1cnp = PVector.div(PVector.mult(v1cn, m1-m2), m1+m2); // elastic
		//PVector v2cnp = PVector.div(PVector.mult(v1cn, 2*m1), m1+m2);
		
		
		PVector v1p = PVector.add(PVector.add(v2, v1cnp), v1ct);
		//PVector v2p = PVector.add(v2, v2cnp);
		
		
		v1p.limit(max);
		//v2p.limit(max);
		
		this.setVelocity(v1p);
		//s.setVelocity(v2p);
		
		
		
/*		PVector v1 = new PVector(this.getVelocity().x, this.getVelocity().y);
		PVector v2 = new PVector(s.getVelocity().x, s.getVelocity().y);

		double m1 = mass;
		double m2 = s.getMass();

		PVector v1p = PVector.div(PVector.add(PVector.mult(v1, m1-m2),PVector.mult(v2, 2*m2)), m1+m2);
		PVector v2p = PVector.div(PVector.add(PVector.mult(v2, m2-m1),PVector.mult(v1, 2*m1)), m1+m2);
		
		this.setVelocity(v1p);
		s.setVelocity(v2p);
*/
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
    
    
    public void setPosition(PVector pv) {
    	location.copy(pv);
    }

    public void setPosition(double x, double y) {
    	location.x = x;
    	location.y = y;
    }
    
    public void setVelocity(PVector pv) {
    	velocity.copy(pv);
    }
    
    public void setVelocity(double x, double y) {
    	velocity.x = x;
    	velocity.y = y;
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
    

    public double getRadius() {
    	return radius;
    }

}

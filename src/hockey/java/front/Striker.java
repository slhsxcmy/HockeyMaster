package hockey.java.front;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Striker extends Pane {
	
	private double mass;

    private PVector location;
    private PVector velocity;
    private boolean started;

    
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
		started = true;
		
		mass = 10;
		
		location = new PVector(ran.nextDouble() * width, ran.nextDouble() * height);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        if (player.getPlayerID() == 1) {
        	circle.setStroke(Color.BLUE);
            circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.6));
        }
        else {
        	circle.setStroke(Color.CYAN);
        	circle.setFill(Color.CYAN.deriveColor(1, 1, 1, 0.6));
        }
        getChildren().add(circle);
	}
	
	public void step(PVector mouse, Midline mid) {
		velocity = PVector.sub(mouse, location);
		// midline
		if (player.getPlayerID() == 1 && location.y <= mid.getLocation() + mid.getHeight() + radius - 1 || 
				player.getPlayerID() == 2 && location.y >= mid.getLocation() - mid.getHeight() - radius + 1) {
			velocity.y = 0; 
			location.x = mouse.x;
		} // try adding else for not on midline
		/*else */location.copy(mouse);
    }
	
	/*
	public void checkBoundaries(Puck p) {
		//TODO
		//CUrrently mouse goes across half line and physics is very off
		//PUCK SPEEDS UP TO MAX
		//player1 gets the bottom half
		
		double px = p.getLocation().x;
		double py = p.getLocation().y;
		double pr = p.getRadius();
		double sx = location.x;
		double sy = location.y;
		if (p.onWall()) {
			while (p.onWall() && Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + pr) {
				p.checkBoundaries();
				System.out.println("While");
				double dx;
				double dy = py - sy + 10;
				if (px < sx) {
					dx = sx - px + 10;
					location.x = px + dx;
				}
				else {
					dx = px - sx + 10;
					location.x = px - dx;
				}
				if (py < sy) {
					dy = sy - py + 10;
					location.y = py + dy;
				}
				else {
					dy = py - sy + 10;
					location.y = py - dy;
				}
			}
		}
		
		// Walls and midline
		if(player.getPlayerID() == 1) {
			
			if (location.x > BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT) {
				location.x = BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT;
			} else if (location.x < 0 +radius+BoardSettings.BOARDER_HEIGHT) {
				location.x = 0+radius+BoardSettings.BOARDER_HEIGHT;
			}

			if (location.y > BoardSettings.SCENE_HEIGHT-radius-BoardSettings.BOARDER_HEIGHT) {
				location.y = BoardSettings.SCENE_HEIGHT-radius-BoardSettings.BOARDER_HEIGHT;
			}
			
			//if the striker hits the midline
			else if (started == true && location.y < (radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult) {
				//System.out.println("midline");
				location.y = (radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult;
			}
			else if (started == false && location.y < ((radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult)*1.5) {
				location.y = ((radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult*1.5);
			}
		}
		else if(player.getPlayerID() == 2) {
			if (location.x > BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT) {
				location.x = BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT;
			} else if (location.x < 0 +radius+BoardSettings.BOARDER_HEIGHT) {
				location.x = 0+radius+BoardSettings.BOARDER_HEIGHT;
			}
			//TODO
			//WE HAVE TO TEST THIS WHEN SERVER IS RUNNIGN
			//if striker 2 hits the midline
			if (location.y < (0 +radius+BoardSettings.BOARDER_HEIGHT)*mult) {
				location.y = 0+radius+BoardSettings.BOARDER_HEIGHT*mult;
			}
			else if (started == true && location.y > ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult) {
				location.y = (BoardSettings.SCENE_HEIGHT/2)-radius-2.5;
			}
			else if(started == false && location.y > ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult*0.5) {
				location.y = ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult*0.5;
			}
		}

	}
*/
	// limit striker to walls and midline
	public void checkStrikerWallsMidline() {
		// Walls and midline
				if(player.getPlayerID() == 1) {
					
					if (location.x > BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT) {
						location.x = BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT;
						velocity.x = 0; // added by caesar
					} else if (location.x < 0 +radius+BoardSettings.BOARDER_HEIGHT) {
						location.x = 0+radius+BoardSettings.BOARDER_HEIGHT;
						velocity.x = 0; // added by caesar
					}

					if (location.y > BoardSettings.SCENE_HEIGHT-radius-BoardSettings.BOARDER_HEIGHT) {
						location.y = BoardSettings.SCENE_HEIGHT-radius-BoardSettings.BOARDER_HEIGHT;
						velocity.y = 0; // added by caesar
					}
					
					//if the striker hits the midline
					else if (started == true && location.y < (radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult) {
						//System.out.println("midline");
						location.y = (radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult;
						velocity.y = 0; // added by caesar
					}
					else if (started == false && location.y < ((radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult)*1.5) {
						location.y = ((radius+(BoardSettings.SCENE_HEIGHT/2)-2+(BoardSettings.BOARDER_HEIGHT/2))*mult*1.5);
						velocity.y = 0; // added by caesar
					}
				}
				else if(player.getPlayerID() == 2) {
					if (location.x > BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT) {
						location.x = BoardSettings.SCENE_WIDTH-radius-BoardSettings.BOARDER_HEIGHT;
						velocity.x = 0; // added by caesar
					} else if (location.x < 0 +radius+BoardSettings.BOARDER_HEIGHT) {
						location.x = 0+radius+BoardSettings.BOARDER_HEIGHT;
						velocity.x = 0; // added by caesar
					}
					//TODO
					//WE HAVE TO TEST THIS WHEN SERVER IS RUNNIGN
					//if striker 2 hits the midline
					if (location.y < (0 +radius+BoardSettings.BOARDER_HEIGHT)*mult) {
						location.y = 0+radius+BoardSettings.BOARDER_HEIGHT*mult;
						velocity.y = 0; // added by caesar
					}
					else if (started == true && location.y > ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult) {
						location.y = (BoardSettings.SCENE_HEIGHT/2)-radius-2.5;
						velocity.y = 0; // added by caesar
					}
					else if(started == false && location.y > ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult*0.5) {
						location.y = ((BoardSettings.SCENE_HEIGHT/2)-radius-2.5)*mult*0.5;
						velocity.y = 0; // added by caesar
					}
				}
	}
	
	public void startGameBound() {
		started = true;
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
    
    
    public void reset(int player) {
    	mult = 1;
    	if (player == 1) {
    		location.x = 200;
    		location.y = 600;
    	}
    	else {
    		location.x = 200;
    		location.y = 100;
    	}
    	velocity.x = 0;
    	velocity.y = 0;
    }
    
    public Circle getCircle() { return circle; }

}

package hockey.java;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Midline extends Pane {
	
	private PVector location;
	
	double height = Settings.BOARDER_HEIGHT/2;
	double width = Settings.SCENE_WIDTH;
	double startingX = 0;
	double startingY = (Settings.SCENE_HEIGHT/2)-2;
	
	Rectangle rec;

	Midline(){
		location = new PVector(startingX, startingY);
		rec = new Rectangle();
		rec.setWidth(width);
		rec.setHeight(height);
		rec.setStroke(Color.BLUE.deriveColor(1, 1, 1, 0.3));
		rec.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));
		getChildren().add(rec);
	}
	//TODO add circle with line
	
	public void display() {
        relocate(location.x, location.y);
    }
	
	public void move(int y) {
		location.y = y;
		display();
	}

	public void reset() {
		location.y = startingY;
		display();
	}
	
	public boolean inMiddle() {
		return location.y == startingY;
	}
	
}

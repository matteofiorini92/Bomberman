package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Direction;
import model.Position;

public class Helix extends Enemy {
	
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put(Direction.INITIAL, "13");
		imageFiles.put(Direction.UP, "05 06 07 08");
		imageFiles.put(Direction.RIGHT, "13 14 15 16");
		imageFiles.put(Direction.DOWN, "01 02 03 04");
		imageFiles.put(Direction.LEFT, "09 10 11 12");
		imageFiles.put(Direction.DEAD, "72 73 74 75 76 77 78");
	}
	
	public Helix(int[] position)
	{
		super(position, new Image("images-helix/" + imageFiles.get(Direction.INITIAL) + ".png"));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}
	
}

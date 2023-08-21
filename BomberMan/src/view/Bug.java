package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Direction;
import model.Position;

public class Bug extends Enemy {

	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put(Direction.INITIAL, "26");
		imageFiles.put(Direction.UP, "10 11 12 13 14 15 16 17 18");
		imageFiles.put(Direction.RIGHT, "26 27 28 29 30 31 32");
		imageFiles.put(Direction.DOWN, "01 02 03 04 05 06 07 08 09");
		imageFiles.put(Direction.LEFT, "19 20 21 22 23 24 25");
	}

	public Bug(int[] position)
	{
		super(position, new Image("bug-64x96/" + imageFiles.get(Direction.INITIAL) + ".png"));
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}

}

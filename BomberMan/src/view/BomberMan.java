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

public class BomberMan extends Character {
	
	public static final int[] INITIAL_POSITION = {1, 2};
	
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put(Direction.INITIAL, "10");
		imageFiles.put(Direction.UP, "01 02 03");
		imageFiles.put(Direction.RIGHT, "11 10 12");
		imageFiles.put(Direction.DOWN, "07 08 09");
		imageFiles.put(Direction.LEFT, "05 04 06");
	}
	
	// private ImageView imageView;

	public BomberMan()
	{
		super(INITIAL_POSITION, new Image("bm-64x96/10.png"));
		
		this.setLayoutY(INITIAL_POSITION[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(INITIAL_POSITION[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);

        getChildren().add(getImageView());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.move((model.Character)o, imageFiles, arg);
	}
}

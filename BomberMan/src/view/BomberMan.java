package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

@SuppressWarnings("deprecation")
public class BomberMan extends Character {
	
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final Double INITIAL_SPEED = model.BomberMan.INITIAL_SPEED;
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadDirectionStringProperties(imageFiles, "resources/characters/bomberMan.properties");
	}

	public BomberMan()
	{
		super(INITIAL_POSITION, new Image("images/-bm/" + imageFiles.get(Direction.INITIAL) + ".png"), INITIAL_SPEED);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}
	
}

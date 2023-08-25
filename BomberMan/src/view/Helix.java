package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

@SuppressWarnings("deprecation")
public class Helix extends Character {
	
	public static final Double INITIAL_SPEED = model.Helix.HELIX_SPEED;
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadDirectionStringProperties(imageFiles, "resources/characters/helix.properties");
	}
	
	public Helix(int[] position)
	{
		super(position, new Image("/images/-helix/" + imageFiles.get(Direction.INITIAL) + ".png"), INITIAL_SPEED);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}
	
}

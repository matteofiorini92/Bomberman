package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

/**
 * view of the bug enemy
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Bug extends Character {

	public static final Double SPEED = model.Bug.SPEED;
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadDirectionStringProperties(imageFiles, "resources/characters/bug.properties");
	}

	public Bug(int[] position)
	{
		super(position, new Image("images/-bug/" + imageFiles.get(Direction.INITIAL) + ".png"), SPEED);
	}
	
	/**
	 * OO pattern
	 * calls the character update method for move, die, become invincible etc.
	 */
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}

}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

/**
 * view of the bomberman character
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class BomberMan extends Character {
	public static BomberMan bomberMan;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final Double INITIAL_SPEED = model.BomberMan.INITIAL_SPEED;
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadDirectionStringProperties(imageFiles, "resources/characters/bomberMan.properties");
	}

	private BomberMan()
	{
		super(INITIAL_POSITION, new Image("images/-bm/" + imageFiles.get(Direction.INITIAL) + ".png"), INITIAL_SPEED);
	}
	
	/**
	 * singleton pattern
	 * @return the only existing instance of the class, or creates one
	 */
	public static BomberMan getInstance() {
		if (bomberMan == null) {
			bomberMan = new BomberMan();
		}
		return bomberMan;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}
	
}

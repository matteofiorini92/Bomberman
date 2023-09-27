package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
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
	
	private static String imagesPath = "images/-bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase() + "/";

	private BomberMan()
	{
		super(INITIAL_POSITION, new Image(imagesPath + imageFiles.get(Direction.INITIAL) + ".png"), INITIAL_SPEED);
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
	
	/**
	 * OO pattern
	 * calls the character update method for move, die, become invincible etc.
	 */
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}
	
	/**
	 * used to start a new game
	 */
	public void reset() {
		StackPane.setMargin(this, new Insets(INITIAL_POSITION[0] * Item.ITEM_HEIGHT - HEIGHT_DIFFERENCE, 0, 0, INITIAL_POSITION[1] * Item.ITEM_WIDTH));
		BomberMan.imagesPath = "images/-bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase() + "/";
		Character.prefixes.remove(model.BomberMan.class);
		Character.prefixes.put(model.BomberMan.class, "bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase());
		this.setImage(new Image(imagesPath + imageFiles.get(Direction.INITIAL) + ".png"));
		this.setSpeed(INITIAL_SPEED);
	}
	
}

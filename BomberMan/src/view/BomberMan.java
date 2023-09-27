package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Direction;

/**
 * view of the bomberman character
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class BomberMan extends Character {
	private static BomberMan bomberMan;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final double INITIAL_SPEED = model.BomberMan.INITIAL_SPEED;
	private static Map<Direction, String> imageFiles = new HashMap<>();
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
	 * overrides the die method of character as BomberMan has an animation significantly different from enemies when dying
	 */
	@Override
	public void die(model.Character character, Map<Direction, String> imageFiles) {
		String prefix = "bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase();
		String[] files = imageFiles.get(model.Direction.DEAD).split("\\s+");
		
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame + 1;
			Image image = new Image("images/-" + prefix + "/" + files[frame] + ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH/(files.length + 1) * framePlusOne), event -> {
				this.setImage(image);
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
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
		Character.removePrefix(model.BomberMan.class);
		Character.addPrefix(model.BomberMan.class, "bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase());
		this.setImage(new Image(imagesPath + imageFiles.get(Direction.INITIAL) + ".png"));
		this.setSpeed(INITIAL_SPEED);
	}
	
}

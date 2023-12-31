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
import model.Hiding;


/**
 * view of a character
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public abstract class Character extends Element {
	
	public static final int CHARACTER_HEIGHT = (int) (Item.ITEM_HEIGHT * 1.5);
	public static final int CHARACTER_WIDTH = Item.ITEM_WIDTH;
	public static final int HEIGHT_DIFFERENCE = CHARACTER_HEIGHT - Item.ITEM_HEIGHT;

	public static final int INVINCIBILITY_FRAMES = 50;
	public static final int TIME_FOR_DEATH = 750;
	public static final int DEATH_FRAMES = 20;
	
	private double speed;
	private double timeForMovement;
	
	private static Map<Class<? extends model.Character>, String> prefixes = new HashMap<>();
	static {
		prefixes.put(model.BomberMan.class, "bm/" + model.Player.getInstance().getAvatar().toString().toLowerCase());
		prefixes.put(model.Helix.class, "helix");
		prefixes.put(model.Bug.class, "bug");
	}

	/**
	 * constructor
	 * @param position the position of the character
	 * @param image the image of the character
	 * @param speed the speed of the character, used for movement animation
	 */
	public Character(int[] position, Image image, double speed)
	{
		super(image);
		this.speed = speed;
		this.timeForMovement = model.Character.TIME_FOR_MOVEMENT / this.speed;
		StackPane.setMargin(this, new Insets(position[0] * Item.ITEM_HEIGHT - HEIGHT_DIFFERENCE, 0, 0, position[1] * Item.ITEM_WIDTH));
		
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * OO pattern
	 * @param o the observable that changed
	 * @param arg an array of arguments that changes based on the type of change
	 * @param imageFiles image files of the specific type of character that is updating
	 */
	public void update(Observable o, Object arg, Map<Direction, String> imageFiles) {
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.MOVE)) {			
			move((model.Character)o, imageFiles, args[1]);
		}
		else if (args[0].equals(model.ChangeType.BECOME_INVINCIBLE)) {
			flash(model.Character.INVINCIBILITY_TIME, INVINCIBILITY_FRAMES, false, null);
		}
		else if (args[0].equals(model.ChangeType.DIE)) {
			die((model.Character)o, imageFiles);
		}
		else if (args[0].equals(model.ChangeType.CHANGE_SPEED)) {
			changeSpeed((Double)args[1]);
		}
	}

	/**
	 * move animation of a character
	 * @param character the character that is moving
	 * @param imageFiles the images of the character
	 * @param arg the previous position of the character
	 */
	public void move(model.Character character, Map<Direction, String> imageFiles, Object arg) {
		
		String prefix = prefixes.get(character.getClass());
		Direction newDirection =  character.getDirection();

		
		int[] newPosition = character.getPosition();
		int[] prevPosition = (int[]) arg;
		
		String[] files = imageFiles.get(newDirection).split("\\s+");
		
		int xMove = newPosition[1] - prevPosition[1];
		int yMove = newPosition[0] - prevPosition[0];
		
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame+1;
			Image image = new Image("images/-" + prefix + "/" + files[frame]+ ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(timeForMovement/files.length * framePlusOne), event -> {
		    	this.setImage(image);
		    	Insets insets = new Insets(
		    			prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * framePlusOne / files.length - HEIGHT_DIFFERENCE,
		    			0,
		    			0,
		    			prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * framePlusOne / files.length
		    			);
		    	StackPane.setMargin(this, insets);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		
		timeline.play();
	
	}
	
	
	/**
	 * Flashing animation for when a character loses a life without dying
	 */
	
	public void flash(int time, int frames, boolean isDying, model.Character character) {
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < frames; frame++) {
			final int framePlusOne = frame + 1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(time/frames * framePlusOne), event -> {
				this.setVisible(framePlusOne % 2 == 0);				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		if (isDying) {
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH), event -> {
				this.setVisible(false);
				if (character instanceof Hiding && ((Hiding) character).isHidingSomething()) {
					model.Item modelItem = (model.Item) ((model.Hiding)character).getHiddenHidable();
					Hidable viewHidable = new Hidable((model.Hidable)modelItem);
					modelItem.addObserver(viewHidable);				
				}
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	/**
	 * animation for when the character dies
	 * @param character the model of the dead character
	 * @param imageFiles the image files of the character
	 */
	public void die(model.Character character, Map<Direction, String> imageFiles) {
		flash(TIME_FOR_DEATH, DEATH_FRAMES, true, character);
	}
	
	
	private void changeSpeed(double newSpeed) {
		this.speed = newSpeed;
		this.timeForMovement = model.Character.TIME_FOR_MOVEMENT / newSpeed;
		controller.KeyListeners.updateThrottleDelay();
	}
	
	public static void removePrefix(Class<? extends model.Character> className) {
		prefixes.remove(className);
	}
	
	public static void addPrefix(Class<? extends model.Character> className, String path) {
		prefixes.put(className, path);
	}
	
}

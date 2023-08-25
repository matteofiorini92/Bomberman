package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.Direction;
import model.HidePowerUp;

@SuppressWarnings("deprecation")
public abstract class Character extends Element {
	
	public static final int CHARACTER_HEIGHT = (int) (Item.ITEM_HEIGHT * 1.5);
	public static final int CHARACTER_WIDTH = Item.ITEM_WIDTH;
	public static final int HEIGHT_DIFFERENCE = CHARACTER_HEIGHT - view.Item.ITEM_HEIGHT;

	public static final int INVINCIBILITY_FRAMES = 20;
	public static int TIME_FOR_DEATH = 1500;
	
//	public static int TIME_FOR_MOVEMENT = model.Character.INITIAL_TIME_FOR_MOVEMENT ;
	
	private Double speed;
	private Double timeForMovement;
	
	public static Map<Class<? extends model.Character>, String> prefixes = new HashMap<>();
	static {
		prefixes.put(model.BomberMan.class, "bm");
		prefixes.put(model.Helix.class, "helix");
		prefixes.put(model.Bug.class, "bug");
	}

	public Character(int[] position, Image image, Double speed)
	{
		super(image);
		this.speed = speed;
		this.timeForMovement = model.Character.INITIAL_TIME_FOR_MOVEMENT / this.speed;
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - HEIGHT_DIFFERENCE);
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);
		
	}
	
	public Double getTimeForMovement() { return timeForMovement; }
	
	public void update(Observable o, Object arg, Map<Direction, String> imageFiles) {
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.MOVE)) {			
			move((model.Character)o, imageFiles, args[1]);
		}
		else if (args[0].equals(model.ChangeType.LOSE_LIFE) || args[0].equals(model.ChangeType.BECOME_INVINCIBLE)) {
			flash();
		}
		else if (args[0].equals(model.ChangeType.DIE)) {
			die((model.Character)o, imageFiles);
		}
		else if (args[0].equals(model.ChangeType.CHANGE_SPEED)) {
			changeSpeed((Double)args[1]);
		}
	}

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
		    	this.setLayoutX(prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * framePlusOne / files.length);
		    	this.setLayoutY(prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * framePlusOne / files.length - HEIGHT_DIFFERENCE);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		
		timeline.play();
	
	}
	
	
	/**
	 * Flashing animation for when a character loses a life without dying
	 */
	
	public void flash() {
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < INVINCIBILITY_FRAMES; frame++) {
			final int framePlusOne = frame + 1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(model.Character.INVINCIBILITY_TIME/INVINCIBILITY_FRAMES * framePlusOne), event -> {
				this.setVisible(framePlusOne % 2 == 0);				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	
	public void die(model.Character character, Map<Direction, String> imageFiles) {
		
		String prefix = prefixes.get(character.getClass());
		String[] files = imageFiles.get(model.Direction.DEAD).split("\\s+");

		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame + 1;
			Image image = new Image("images/-" + prefix + "/" + files[frame] + ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH/(files.length + 1) * framePlusOne), event -> {
		    	this.setImage(image);
		    	System.out.println(TIME_FOR_DEATH/(files.length + 1) * framePlusOne);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH), event -> {
			this.setVisible(false);
			if (character instanceof HidePowerUp && ((HidePowerUp) character).isHidingSomething()) {
				model.PowerUp modelPowerUp = ((model.HidePowerUp)character).getHiddenPowerUp();
				view.PowerUp viewPowerUp = new view.PowerUp(modelPowerUp);
				modelPowerUp.addObserver(viewPowerUp);				
			}
	    });
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
			
	}
	
	private void changeSpeed(Double newSpeed) {
		this.speed = newSpeed;
		this.timeForMovement = model.Character.INITIAL_TIME_FOR_MOVEMENT / this.speed;
	}
	
	
	
}

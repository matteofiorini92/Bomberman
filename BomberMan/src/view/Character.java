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

public abstract class Character extends Element {
	
	public static final int CHARACTER_HEIGHT = 96;
	public static final int CHARACTER_WIDTH = 64;
	public static final int HEIGHT_DIFFERENCE = CHARACTER_HEIGHT - view.Item.ITEM_HEIGHT;

	public static final long INVULNERABILITY_TIME = 3000;
	public static final int INVULNERABILITY_FRAMES = 20;
	
	public static long TIME_FOR_MOVEMENT = 375;
	public static long TIME_FOR_DEATH = 3000;
	
	
	public static Map<Class<? extends model.Character>, String> prefixes = new HashMap<>();
	static {
		prefixes.put(model.BomberMan.class, "bm");
		prefixes.put(model.Helix.class, "helix");
		prefixes.put(model.Bug.class, "bug");
	}

	private ImageView imageView;
	
	public Character(int[] position, Image im1)
	{
		imageView = new ImageView(im1);
		
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - HEIGHT_DIFFERENCE);
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);
		
        getChildren().add(imageView);
	}
	
	public void update(Observable o, Object arg, Map<Direction, String> imageFiles) {
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.MOVE)) {			
			move((model.Character)o, imageFiles, args[1]);
		}
		else if (args[0].equals(model.ChangeType.LOSE_LIFE)) {
			loseLife();
		}
		else if (args[0].equals(model.ChangeType.DIE)) {
			die((model.Character)o, imageFiles);
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
			Image image = new Image("images-" + prefix + "/" + files[frame]+ ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_MOVEMENT/files.length * framePlusOne), event -> {
		    	imageView.setImage(image);
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
	
	public void loseLife() {
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < INVULNERABILITY_FRAMES; frame++) {
			final int framePlusOne = frame + 1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(INVULNERABILITY_TIME/INVULNERABILITY_FRAMES * framePlusOne), event -> {
				imageView.setVisible(framePlusOne % 2 == 0);				
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
			Image image = new Image("images-" + prefix + "/" + files[frame] + ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH/(files.length + 1) * framePlusOne), event -> {
		    	imageView.setImage(image);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_DEATH), event -> {
	    	imageView.setVisible(false);
	    });
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
	}
	
	
	
	
	
}

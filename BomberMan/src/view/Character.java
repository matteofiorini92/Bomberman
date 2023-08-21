package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Direction;
import model.Position;

public abstract class Character extends Element {
	public static final long INVULNERABILITY_TIME = 3000;
    public static final int CHARACTER_HEIGHT = 96;
    public static final int CHARACTER_WIDTH = 64;
	public static long TIME_FOR_MOVEMENT = 375;
	
	private int heightDifference = CHARACTER_HEIGHT - view.Item.ITEM_HEIGHT;
	
	public static Map<Class<? extends model.Character>, String> prefixes = new HashMap<>();
	static {
		prefixes.put(model.BomberMan.class, "bm");
		prefixes.put(model.Helix.class, "helix");
		prefixes.put(model.Bug.class, "bug");
	}

	private Direction direction;
	private ImageView imageView;
	
	public Character(int[] position, Image im1)
	{
		this.direction = Direction.INITIAL;
		imageView = new ImageView(im1);
		
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - heightDifference);
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);
		
        getChildren().add(getImageView());
		
//		imageView.setFitHeight(96);
//		imageView.setFitWidth(64);
	}

	public Direction getDirection()	{ return direction;	}
	public void setDirection(Direction direction) {	this.direction = direction;	}
	
	public ImageView getImageView() { return imageView; }
	

	public void move(model.Character character, Map<Direction, String> imageFiles, Object arg) {
		
		String prefix = prefixes.get(character.getClass());
		Direction newDirection =  character.getDirection();
		
		if (newDirection.equals(Direction.INITIAL)) {
			Image image = new Image(prefix + "-64x96/"+ imageFiles.get(newDirection) +".png");
			imageView.setImage(image);
		} else {
			int[] newPosition = character.getPosition();
			int[] prevPosition = (int[]) arg;
			
			String[] files = imageFiles.get(newDirection).split("\\s+");
			
			int xMove = newPosition[1] - prevPosition[1];
			int yMove = newPosition[0] - prevPosition[0];
			
			Timeline timeline = new Timeline();
			
			for (int i = 0; i < files.length; i++) {
				final int iPlusOne = i+1;
				Image image = new Image(prefix + "-64x96/" + files[i]+ ".png");
				KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_MOVEMENT/files.length * iPlusOne), event -> {
			    	imageView.setImage(image);
			    	this.setLayoutX(prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * iPlusOne / files.length);
			    	this.setLayoutY(prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * iPlusOne / files.length - 32);
			    });
				timeline.getKeyFrames().add(keyFrame);
			}
			
			timeline.play();
		}	
	}
	
	public void loseLife() {
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < 10; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(INVULNERABILITY_TIME/10 * framePlusOne), event -> {

				
				imageView.setVisible(framePlusOne % 2 == 0);	
				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	
	public void die() {
		
	}
	
	
	public void update(Observable o, Object arg, Map<Direction, String> imageFiles) {
		Object[] args = (Object[]) arg;

		if (args[0].equals(model.ChangeType.MOVE)) {			
			move((model.Character)o, imageFiles, args[1]);
		}
		else if (args[0].equals(model.ChangeType.LOSE_LIFE)) {
			//flash();
			loseLife();
		}
		else if (args[0].equals(model.ChangeType.DIE)) {
			die();
		}
	}
	
	
	
}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Direction;
import model.Position;

public class BomberMan extends Character {
	
	public static final int[] INITIAL_POSITION = {1, 2};
	
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put("N", "02");
		imageFiles.put("E", "10");
		imageFiles.put("S", "08");
		imageFiles.put("W", "04");
		imageFiles.put("MN", "01 02 03");
		imageFiles.put("ME", "11 10 12");
		imageFiles.put("MS", "07 08 09");
		imageFiles.put("MW", "05 04 06");
	}
	
	private ImageView imageView;

	public BomberMan()
	{
		super(INITIAL_POSITION, Direction.E);
		Image im1 = new Image("bm-64x96/10.png");
		imageView = new ImageView(im1);
		imageView.setFitHeight(96);
		imageView.setFitWidth(64);
		
		this.setLayoutY(INITIAL_POSITION[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(INITIAL_POSITION[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);

        getChildren().add(imageView);
	}
	
//	public void turn(Direction direction) {
//		
//	}

	@Override
	public void update(Observable o, Object arg)
	{
		Direction newDirection = ((model.BomberMan) o).getDirection();
		Image image = new Image("bm-64x96/"+ imageFiles.get(newDirection.toString()) +".png");
		imageView.setImage(image);

		if (arg instanceof int[]) {
			int[] newPosition = ((model.BomberMan) o).getPosition();
			int[] prevPosition = (int[]) arg;
			move(newDirection, prevPosition, newPosition);
			// this.setLayoutY(newPosition[0] * Item.ITEM_HEIGHT - 32); // to improve
			// this.setLayoutX(newPosition[1] * Item.ITEM_WIDTH);
		}
		
	}

	private void move(Direction direction, int[] prevPosition, int[] newPosition) {
		String[] files = imageFiles.get("M"+direction.toString()).split("\\s+");
		Image sw1 = new Image("bm-64x96/" + files[0]+ ".png");
		Image sw2 = new Image("bm-64x96/" + files[1]+ ".png");
		Image sw3 = new Image("bm-64x96/" + files[2]+ ".png");
		
		int xMove = newPosition[1] - prevPosition[1];
		int yMove = newPosition[0] - prevPosition[0];
		
		Timeline timeline = new Timeline(
			    new KeyFrame(Duration.millis(125), event -> {
			    	imageView.setImage(sw1);
			    	this.setLayoutX(prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * 1 / 3);
			    	this.setLayoutY(prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * 1 / 3 - 32);
			    }),
			    new KeyFrame(Duration.millis(250), event -> { 
			    	imageView.setImage(sw2);
			    	this.setLayoutX(prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * 2 / 3);
			    	this.setLayoutY(prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * 2 / 3 - 32);
		    	}),
			    new KeyFrame(Duration.millis(375), event -> { 
			    	imageView.setImage(sw3);
			    	this.setLayoutX(prevPosition[1] * Item.ITEM_WIDTH + xMove * Item.ITEM_WIDTH * 3 / 3);
			    	this.setLayoutY(prevPosition[0] * Item.ITEM_HEIGHT + yMove * Item.ITEM_HEIGHT * 3 / 3 - 32);
		    	})
			);

		timeline.play();
	}

}

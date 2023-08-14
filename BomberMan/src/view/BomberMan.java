package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Position;

public class BomberMan extends Character {
	
	public static final Position INITIAL_POSITION = new Position(1, 2);
	
	private static Map<String, String> imageFiles = new HashMap<>();
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
		
		int[] coordinates = INITIAL_POSITION.getCoordinates();
		
		this.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);

		// Rectangle rectangle = new Rectangle(CHARACTER_WIDTH, CHARACTER_HEIGHT, Color.TRANSPARENT);
        getChildren().add(imageView);
	}
	
	public void turn(Direction direction) {
		
	}
	
	public void move(Direction direction, double speed) {
		
	}

	@Override
	public void update(Observable o, Object arg)
	{
		
		int[] coordinates = ((model.Element) o).getPosition().getCoordinates();
		this.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);
	}


}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Direction;
import model.Position;

public class Helix extends Enemy {
	
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put(Direction.INITIAL, "13");
		imageFiles.put(Direction.UP, "05 06 07 08");
		imageFiles.put(Direction.RIGHT, "13 14 15 16");
		imageFiles.put(Direction.DOWN, "01 02 03 04");
		imageFiles.put(Direction.LEFT, "09 10 11 12");
	}

//	private ImageView imageView;

	
	public Helix(int[] position)
	{
		super(position, new Image("helix-64x96/" + imageFiles.get(Direction.INITIAL) + ".png"));
//		Image im1 = new Image("helix-64x96/" + imageFiles.get(Direction.INITIAL) + ".png");
//		imageView = new ImageView(im1);
//		imageView.setFitHeight(96);
//		imageView.setFitWidth(64);
		
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);
        getChildren().add(getImageView());
	}

//	@Override
//	public void update(Observable o, Object arg)
//	{
//		
//		int[] coordinates = ((model.Element) o).getPosition();
//		this.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT - 32); // to improve
//		this.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);
//	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.move((model.Character)o, imageFiles, arg);
	}

}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Direction;
import model.Position;

public class Bug extends Enemy {
	
	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put(Direction.INITIAL, "26");
		imageFiles.put(Direction.UP, "10 11 12 13 14 15 16 17 18");
		imageFiles.put(Direction.RIGHT, "26 27 28 29 30 31 32");
		imageFiles.put(Direction.DOWN, "01 02 03 04 05 06 07 08 09");
		imageFiles.put(Direction.LEFT, "19 20 21 22 23 24 25");
	}

//	private ImageView imageView;

	public Bug(int[] position)
	{
		super(position, new Image("bug-64x96/" + imageFiles.get(Direction.INITIAL) + ".png"));
//		Image im1 = new Image("bug-64x96/" + imageFiles.get(Direction.INITIAL) + ".png");
//		imageView = new ImageView(im1);
//		imageView.setFitHeight(96);
//		imageView.setFitWidth(64);
//		
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);
        getChildren().add(getImageView());
	}

	@Override
	public void update(Observable o, Object arg)
	{
		int[] coordinates = ((model.Element) o).getPosition();
		this.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);
	}

}

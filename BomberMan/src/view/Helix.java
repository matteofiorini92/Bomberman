package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Position;

public class Helix extends Enemy {
	
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put("N", "05");
		imageFiles.put("E", "13");
		imageFiles.put("S", "01");
		imageFiles.put("W", "09");
		imageFiles.put("MN", "05 06 07 08");
		imageFiles.put("ME", "13 14 15 16");
		imageFiles.put("MS", "01 02 03 04");
		imageFiles.put("MW", "09 10 11 12");
	}

	private ImageView imageView;

	
	public Helix(int[] position, Direction direction)
	{
		super(position, direction);
		Image im1 = new Image("helix-64x96/13.png");
		imageView = new ImageView(im1);
		imageView.setFitHeight(96);
		imageView.setFitWidth(64);
		
		this.setLayoutY(position[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(position[1] * Item.ITEM_WIDTH);

		StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);
        getChildren().add(imageView);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		
		int[] coordinates = ((model.Element) o).getPosition();
		this.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT - 32); // to improve
		this.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);
	}

}

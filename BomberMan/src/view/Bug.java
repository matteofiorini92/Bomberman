package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

@SuppressWarnings("deprecation")
public class Bug extends Character {

	public static Map<Direction, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadDirectionStringProperties(imageFiles, "resources/characters/bug.properties");
	}

	public Bug(int[] position)
	{
		super(position, new Image("images/-bug/" + imageFiles.get(Direction.INITIAL) + ".png"));
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg, imageFiles);
	}

}

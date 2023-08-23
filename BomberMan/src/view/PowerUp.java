package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Direction;

public class PowerUp extends Item {

	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/power-ups.properties");
	}
	
	public PowerUp(model.PowerUp modelPowerUp)
	{		
		super(new ImageView());
		// TODO Auto-generated constructor stub
	}
	
	// new ImageView(new Image("images/-power-ups/" + imageFiles.get(modelPowerUp.getClass().getName()).split("\\s+")[0] + ".png"))

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub

	}

}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PowerUp extends Item {
	public static final int POWER_UP_ANIMATION = 100;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/power-ups.properties");
	}
//	private Timeline timeline = new Timeline();
	
	public PowerUp(model.PowerUp modelPowerUp)
	{		
		super(new ImageView(new Image("images/-power-ups/" + imageFiles.get(modelPowerUp.getClass().getName()).split("\\s+")[0] + ".png")));
		startAnimation(modelPowerUp);
		
	}
	
	// new ImageView(new Image("images/-power-ups/" + imageFiles.get(modelPowerUp.getClass().getName()).split("\\s+")[0] + ".png"))

	private void startAnimation(model.PowerUp modelPowerUp) {
		Timeline timeline = new Timeline();
		int[] modelPowerUpPosition = modelPowerUp.getPosition();
		System.out.println(modelPowerUpPosition[0]);
		System.out.println(modelPowerUpPosition[1]);
		this.setLayoutX(modelPowerUpPosition[1]*view.Item.ITEM_WIDTH);
		this.setLayoutY(modelPowerUpPosition[0]*view.Item.ITEM_HEIGHT);
		String[] files = imageFiles.get(modelPowerUp.getClass().getName()).split("\\s+");
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame + 1;
			Image image = new Image("images/-power-ups/" + files[frame] + ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(POWER_UP_ANIMATION/files.length * framePlusOne), event -> {
				this.getImageView().setImage(image);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub

	}

}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
public class Hidable extends Item {
	public static final int POWER_UP_ANIMATION = 100;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/power-ups.properties");
	}
	private Timeline timeline = new Timeline();
	
	public Hidable(model.Hidable modelHidable)
	{		
		super(null);
		startAnimation(modelHidable);
		
	}
	
	// new ImageView(new Image("images/-power-ups/" + imageFiles.get(modelPowerUp.getClass().getName()).split("\\s+")[0] + ".png"))

	private void startAnimation(model.Hidable modelHidable) {
		
		
		int[] modelHidablePosition = ((model.Item)modelHidable).getPosition();
		view.GameBody.getInstance().setTile(this, modelHidablePosition);

		this.setLayoutX(modelHidablePosition[1]*view.Item.ITEM_WIDTH);
		this.setLayoutY(modelHidablePosition[0]*view.Item.ITEM_HEIGHT);

		String[] files = imageFiles.get(modelHidable.getClass().getName()).split("\\s+");
		
		if (modelHidable instanceof model.Exit) {
			Image image = new Image("images/-power-ups/" + files[0] + ".png");
			this.setImage(image);
		}
		else {
			for (int frame = 0; frame < files.length; frame++) {
				final int framePlusOne = frame + 1;
				Image image = new Image("images/-power-ups/" + files[frame] + ".png");
				KeyFrame keyFrame = new KeyFrame(Duration.millis(POWER_UP_ANIMATION/files.length * framePlusOne), event -> {
					this.setImage(image);
				});
				timeline.getKeyFrames().add(keyFrame);
			}
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		this.setVisible(false);
	}

}

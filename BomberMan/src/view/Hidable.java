package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * the view of hidable objects (e.g. power ups and exit)
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Hidable extends Item {
	public static final int POWER_UP_ANIMATION = 100;
	private static Map<String, String> imageFiles = new HashMap<>();
	static {
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/power-ups.properties");
	}
	private Timeline timeline = new Timeline();
	
	/**
	 * constructor. called when the hiding object is destroyed/dies
	 * @param modelHidable the corresponding model hidable object
	 * starts the object animation when instantiated (or simply sets the image if hidable is the exit)
	 */
	public Hidable(model.Hidable modelHidable)
	{		
		super(null);
		startAnimation(modelHidable);
		
	}
	
	private void startAnimation(model.Hidable modelHidable) {
		
		
		int[] modelHidablePosition = ((model.Item)modelHidable).getPosition();
		GameBoard.getInstance().setTile(this, modelHidablePosition);

		this.setLayoutX(modelHidablePosition[1] * Item.ITEM_WIDTH);
		this.setLayoutY(modelHidablePosition[0] * Item.ITEM_HEIGHT);

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
	
	
	/**
	 * OO pattern
	 * called when object is picked up
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		this.setVisible(false);
	}

}

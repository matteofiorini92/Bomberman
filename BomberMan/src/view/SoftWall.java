package view;

import javafx.util.Duration;

import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SoftWall extends Item {
	
	private ImageView imageView;

	public SoftWall(String desc)
	{
		String[] files = desc.split("\\s+");
		Image sw1 = new Image("tiles-64x64/" + files[0]+ ".png");
		Image sw2 = new Image("tiles-64x64/" + files[1]+ ".png");
		Image sw3 = new Image("tiles-64x64/" + files[2]+ ".png");
		Image sw4 = new Image("tiles-64x64/" + files[3]+ ".png");
		imageView = new ImageView(sw1);
		imageView.setFitHeight(64);
		imageView.setFitWidth(64);
		
		Timeline timeline = new Timeline(
			    new KeyFrame(Duration.millis(125), event -> imageView.setImage(sw1)),
			    new KeyFrame(Duration.millis(250), event -> imageView.setImage(sw2)),
			    new KeyFrame(Duration.millis(375), event -> imageView.setImage(sw3)),
			    new KeyFrame(Duration.millis(500), event -> imageView.setImage(sw4))
			);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		timeline.play();
		
		getChildren().add(imageView);

	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}

}

package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Direction;

public class Bomb extends Item {
	
	public static final long TIME_FOR_EXPLOSION = 3000;
	private ImageView imageView = new ImageView();
	private String sequence = "70 71 70 69 70 71 70 69";
	private Image smallBomb = new Image("tiles-64x64/69.png");
	private Image mediumBomb = new Image("tiles-64x64/70.png");
	private Image bigBomb = new Image("tiles-64x64/71.png");
	
	public Bomb() {
//		imageView.setImage(mediumBomb);
		imageView.setFitHeight(64);
		imageView.setFitWidth(64);
		
//		this.setLayoutY(); // to improve
//		this.setLayoutX();

		// StackPane.setAlignment(this, javafx.geometry.Pos.BOTTOM_RIGHT);

        getChildren().add(imageView);
		
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		if (arg.equals("TRIGGER")) {
			int[] position = ((model.Bomb) o).getPosition();
			this.setLayoutX(position[1]*64);
			this.setLayoutY(position[0]*64);

			String[] files = sequence.split("\\s+");
			
			Timeline timeline = new Timeline();
			
			for (int i = 0; i < files.length; i++) {
				final int iPlusOne = i+1;
				Image image = new Image("tiles-64x64/" + files[i]+ ".png");
				KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_EXPLOSION/files.length * iPlusOne), event -> {
			    	imageView.setImage(image);
			    });
				timeline.getKeyFrames().add(keyFrame);
			}
			
			timeline.play();
		}
		
	}
}

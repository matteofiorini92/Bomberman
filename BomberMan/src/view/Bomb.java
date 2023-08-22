package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Direction;
import model.Element;

public class Bomb extends Item {
	
	public static final long TIME_TO_TRIGGER = 3000;
	public static final long TIME_FOR_EXPLOSION = 1500;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put("ex", "01 02 03 04 05");
		imageFiles.put("midUp", "11 12 13 14 15");
		imageFiles.put("edgeUp", "16 17 18 19 20");
		imageFiles.put("midDown", "11 12 13 14 15");
		imageFiles.put("edgeDown", "21 22 23 24 25");
		imageFiles.put("midLeft", "06 07 08 09 10");
		imageFiles.put("edgeLeft", "31 32 33 34 35");
		imageFiles.put("midRight", "06 07 08 09 10");
		imageFiles.put("edgeRight", "26 27 28 29 30");
	}
	private String explosionSequence = "01 02 03 04 05";
//	private ImageView imageView = new ImageView();
	private String sequence = "70 71 70 69 70 71 70 69";
	private Image smallBomb = new Image("tiles-64x64/69.png");
	private Image mediumBomb = new Image("tiles-64x64/70.png");
	private Image bigBomb = new Image("tiles-64x64/71.png");
	GridPane gridPane = new GridPane();
	
	public Bomb() {
		super(new ImageView());
//		imageView.setImage(mediumBomb);
//		imageView.setFitHeight(64);
//		imageView.setFitWidth(64);
        getChildren().add(gridPane);
		
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		if (arg.equals(model.ChangeType.TRIGGER)) {
			triggerBomb((model.Bomb)o);
		}
		if (arg.getClass().equals(new ArrayList<Object>().getClass()) && ((ArrayList<Object>) arg).get(0).equals(model.ChangeType.EXPLODE)) {
			System.out.println("I'm exploding");
			explode((model.Bomb)o, (ArrayList<Object>) arg);
		}
		
	}
	
	public void triggerBomb(model.Bomb bomb) {
		int[] position = bomb.getPosition();
		this.setLayoutX(position[1]*64);
		this.setLayoutY(position[0]*64);

		String[] files = sequence.split("\\s+");
		
		Timeline timeline = new Timeline();
		
		for (int i = 0; i < files.length; i++) {
			final int iPlusOne = i+1;
			Image image = new Image("tiles-64x64/" + files[i]+ ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_TO_TRIGGER/files.length * iPlusOne), event -> {
		    	this.getImageView().setImage(image);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		
		timeline.play();
	}
	
	public void explode(model.Bomb bomb, ArrayList<Object> arg) {
		
		int range = bomb.getRange();
		
		int[] bombPosition = bomb.getPosition();
		
		String[][] grid = (String[][]) arg.get(1);
		
		Timeline timeline = new Timeline();
		
		for (int frame = 0; frame < 5; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_EXPLOSION/6 * framePlusOne), event -> {
				
				for (int i = 0; i < range * 2 + 1; i++) {
					for (int j = 0; j < range * 2 + 1; j++) {
						String type = grid[i][j];
						if (type != null) {							
							String[] files = imageFiles.get(type).split("\\s+");
							Image im = new Image("explosions-64x64/" + files[framePlusOne-1] + ".png");
							ImageView iv = new ImageView(im);
							gridPane.add(iv, j, i);
						}
						else {
							Rectangle rect = new Rectangle(64,64, Color.TRANSPARENT);
							gridPane.add(rect, j, i);
						}
					}
				}

				this.setLayoutX((bombPosition[1] - range) * 64);
				this.setLayoutY((bombPosition[0] - range) * 64);
				
				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_EXPLOSION), event -> {
			getChildren().clear();
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
	}
	
	
}

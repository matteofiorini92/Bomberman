package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
public class Bomb extends Item {
	
	public static final long TIME_TO_TRIGGER = 3000;
	public static final long TIME_FOR_EXPLOSION = 1500;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		String currLevel = controller.Main.getCurrLevel();
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/explosions/" + currLevel + ".properties");
	}
	
	private GridPane gridPane = new GridPane();
	private Pane pane = new Pane();					// have to use a pane for precise positioning
	
	public Bomb() {
		super(null);
		pane.getChildren().add(gridPane);
        view.Board.getInstance().getItemsPane().getChildren().add(pane);
	}
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		Object[] args = (Object[]) arg;
		if (args[0].equals(model.ChangeType.TRIGGER)) {
			triggerBomb((model.Bomb)o);
		}
		else if (args[0].equals(model.ChangeType.EXPLODE)) {
			explode((model.Bomb)o, (String[][]) args[1]);
		}
		
	}
	
	public void triggerBomb(model.Bomb bomb) {
		int[] position = bomb.getPosition();
		this.setLayoutX(position[1]*view.Item.ITEM_WIDTH);
		this.setLayoutY(position[0]*view.Item.ITEM_HEIGHT);

		String[] files = imageFiles.get("beforeEx").split("\\s+");
		
		Timeline timeline = new Timeline();
		
		for (int i = 0; i < files.length; i++) {
			final int iPlusOne = i+1;
			Image image = new Image("images/-tiles/" + files[i]+ ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_TO_TRIGGER/files.length * iPlusOne), event -> {
		    	this.setImage(image);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	
	public void explode(model.Bomb bomb, String[][] grid) {
		
		int range = bomb.getRange();
		int[] bombPosition = bomb.getPosition();
		Timeline timeline = new Timeline();
		


		for (int frame = 0; frame < 5; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_EXPLOSION/6 * framePlusOne), event -> {
				
				for (int i = 0; i < range * 2 + 1; i++) {
					for (int j = 0; j < range * 2 + 1; j++) {
						String type = grid[i][j];
						if (type != null) {							
							String[] files = imageFiles.get(type).split("\\s+");
							Image im = new Image("images/-explosions/" + files[framePlusOne-1] + ".png");
							ImageView iv = new ImageView(im);
							gridPane.add(iv, j, i);
						}
						else {
							Rectangle rect = new Rectangle(view.Item.ITEM_WIDTH,view.Item.ITEM_HEIGHT, Color.TRANSPARENT);
							gridPane.add(rect, j, i);
						}
					}
				}
				gridPane.setLayoutX((bombPosition[1] - range) * view.Item.ITEM_WIDTH);
				gridPane.setLayoutY((bombPosition[0] - range) * view.Item.ITEM_HEIGHT);
				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_FOR_EXPLOSION), event -> {
			setImage(null);
			gridPane.getChildren().clear();
			
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}

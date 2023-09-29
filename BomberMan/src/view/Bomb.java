package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * view of bomb item
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Bomb extends Item {
	
	public static final int TIME_TO_EXPLODE = model.Bomb.TIME_TO_EXPLODE;
	public static final int EXPLOSION_ANIMATION = 500;
	private static Map<String, String> imageFiles = new HashMap<>();
	
	private GridPane gridPane = new GridPane();
	private Pane pane = new Pane();					// have to use a pane for precise positioning
	private Timeline timeline = new Timeline();
	private static String tilesPath;
	private static String explosionsPath;
	
	public Bomb() {
		super(null);
		pane.getChildren().add(gridPane);
		ObservableList<Node> gameBodyChildren = GameBoard.getInstance().getItemsPane().getChildren(); 
		int bombStackPaneIndex = gameBodyChildren.indexOf(this);
		gameBodyChildren.add(bombStackPaneIndex + 1, this.pane);
	}
	
	
	/**
	 * OO pattern
	 * used for explosions and trigger the initial animation
	 */
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
	
	
	private void triggerBomb(model.Bomb bomb) {
		int[] position = bomb.getPosition();
		StackPane.setMargin(this, new Insets(position[0] * Item.ITEM_HEIGHT, 0, 0, position[1] * Item.ITEM_WIDTH));

		String[] files = imageFiles.get("beforeEx").split("\\s+");
		
		for (int i = 0; i < files.length; i++) {
			final int iPlusOne = i+1;
			Image image = new Image(tilesPath + files[i]+ ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(TIME_TO_EXPLODE/files.length * iPlusOne), event -> {
		    	this.setImage(image);
		    });
			timeline.getKeyFrames().add(keyFrame);
		}
		timeline.play();
	}
	
	/**
	 * explosion animation
	 * @param bomb the model bomb that is exploding
	 * @param grid an array of arrays of strings that describe the surroundings of the exploding bomb
	 */
	public void explode(model.Bomb bomb, String[][] grid) {
		int range = bomb.getRange();
		int[] bombPosition = bomb.getPosition();
		
		timeline.stop();
		timeline.getKeyFrames().clear();

		for (int frame = 0; frame < 5; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(EXPLOSION_ANIMATION/6 * framePlusOne), event -> {
				
				for (int i = 0; i < range * 2 + 1; i++) {
					for (int j = 0; j < range * 2 + 1; j++) {
						String type = grid[i][j];
						if (type != null) {							
							String[] files = imageFiles.get(type).split("\\s+");
							Image im = new Image(explosionsPath + files[framePlusOne-1] + ".png");
							ImageView iv = new ImageView(im);
							gridPane.add(iv, j, i);
						}
						else {
							Rectangle rect = new Rectangle(Item.ITEM_WIDTH, Item.ITEM_HEIGHT, Color.TRANSPARENT);
							gridPane.add(rect, j, i);
						}
					}
				}
				gridPane.setLayoutX((bombPosition[1] - range) * Item.ITEM_WIDTH);
				gridPane.setLayoutY((bombPosition[0] - range) * Item.ITEM_HEIGHT);
				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		KeyFrame keyFrame = new KeyFrame(Duration.millis(EXPLOSION_ANIMATION), event -> {
			setImage(null);
			gridPane.getChildren().clear();
			
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	public static void loadImageFiles() {
		tilesPath = "images/-tiles/" + controller.Level.getCurrLevel() + "/";
		explosionsPath = "images/-explosions/" + controller.Level.getCurrLevel() + "/";
		imageFiles.clear();
		String currLevel = controller.Level.getCurrLevel();
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/explosions/" + currLevel + ".properties");
	}
}

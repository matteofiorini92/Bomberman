package view;

import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("deprecation")
public class SoftWall extends Item {
	
	public static final long SOFT_WALL_ANIMATION = 500;
	public static final long SOFT_WALL_EXPLOSION = 1500;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		String currLevel = controller.Main.getCurrLevel();
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/tiles/" + currLevel + ".properties");
	}
	
	private Timeline timeline = new Timeline(); // class attribute so that it can be accessed by both startAnimation and update (for explosions)
	
	public SoftWall(String desc)
	{
		super(new ImageView());
		startAnimation(desc);
	}
	
	private void startAnimation(String desc) {
		String[] files = imageFiles.get(desc).split("\\s+");
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame + 1;
			Image image = new Image("images/-tiles/" + files[frame] + ".png");
			KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_ANIMATION/files.length * framePlusOne), event -> {
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
		
		view.Board viewBoard = view.Board.getInstance();
		model.Board modelBoard = model.Board.getInstance();
		timeline.getKeyFrames().clear();
		timeline = new Timeline();
		
		/**
		 * Soft wall explosion animation
		 */
		
		String[] files = imageFiles.get("swe").split("\\s+");
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION/(files.length+1) * framePlusOne), event -> {
				Image im = new Image("images/-tiles/" + files[framePlusOne-1] + ".png");
				this.getImageView().setImage(im);				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		
		
		int[] softWallPosition = ((model.Element) o).getPosition();
		int y = softWallPosition[0];
		int x = softWallPosition[1];
		
		/**
		 * Last frame of animation (after the softwall exploded)
		 */
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION), event -> {
			
		
			// remove shadow from tile below if present
			
			int[] positionBelow = { y + 1, x };
			model.Element cellBelow = modelBoard.getCell(positionBelow);
			
			if (cellBelow instanceof model.EmptyTile) {
				String desc = "e";
				Image im = new Image("images/-tiles/" + imageFiles.get(desc) + ".png");
				Tile tileBelow = new Tile(desc, im);
				cellBelow.addObserver(tileBelow);
				viewBoard.setTile(tileBelow, positionBelow);
				
			}
			else if (cellBelow instanceof model.SoftWall) {
				String desc = "sw";
				SoftWall tileBelow = new view.SoftWall(desc);
				cellBelow.addObserver(tileBelow);
				viewBoard.setTile(tileBelow, positionBelow);
			}
			
			// set tile where SoftWall was to either empty, empty with softWall shadow, empty with wall shadow or empty with border shadow
			
			if (((model.SoftWall)o).isHidingSomething()) {
				new view.PowerUp(((model.SoftWall)o).getHiddenPowerUp());
			} else {				
				int[] positionAbove = { y - 1, x };
				model.Element cellAbove = modelBoard.getCell(positionAbove);
				
				String desc = "e";
				if (cellAbove instanceof model.Wall && positionAbove[0] == 1) {
					desc = "ebs";
				}
				else if (cellAbove instanceof model.Wall && positionAbove[0] != 1) {
					desc = "ews";
				}
				else if (cellAbove instanceof model.SoftWall) {
					desc = "esws";
				}
				String file = imageFiles.get(desc);
				Image im = new Image("images/-tiles/" + file + ".png");
				this.getImageView().setImage(im);
				viewBoard.setTile(new view.Tile(desc, im), softWallPosition);
			}
			
			
		});
		

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
	}

}

package view;

import javafx.util.Duration;
import model.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SoftWall extends Item {
	
	public static final long SOFT_WALL_ANIMATION = 500;
	public static final long SOFT_WALL_EXPLOSION = 1500;
	public static Map<String, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put("sw", "05 06 07 08");	// soft wall
		imageFiles.put("sws", "13 14 15 16");	// soft wall with shadow
	}
	
	private Timeline timeline = new Timeline(); // class attribute so that it can be access by both startAnimation and update (for explosions)
	
	public SoftWall(String desc)
	{
		super(new ImageView());
		startAnimation(desc);
	}
	
	private void startAnimation(String desc) {
		String[] files = imageFiles.get(desc).split("\\s+");
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame + 1;
			Image image = new Image("tiles-64x64/" + files[frame] + ".png");
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
		view.Board b = view.Board.getInstance();
		String imageFiles = "63 64 65 66 67 68";
		String[] files = imageFiles.split("\\s+");
		timeline.getKeyFrames().clear();
		timeline = new Timeline();
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION/(files.length+1) * framePlusOne), event -> {
				
				Image im = new Image("tiles-64x64/" + files[framePlusOne-1] + ".png");
				this.getImageView().setImage(im);				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		
		
		int[] softWallPosition = ((model.Element) o).getPosition();
		int y = softWallPosition[0];
		int x = softWallPosition[1];
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION), event -> {
			
		
			// remove shadow from tile below if present
			
			int[] positionBelow = {y+1, x};
			model.Element cellBelow = model.Board.getInstance().getCell(positionBelow);
			
			if (cellBelow instanceof model.EmptyTile) {
				
				view.Tile tileBelow = (Tile)view.Board.getInstance().getTile(positionBelow);
				Image im = new Image("tiles-64x64/" + "20" + ".png");
				tileBelow.getImageView().setImage(im);
				view.Board.getInstance().setTile(tileBelow, positionBelow);
				
			}
			if (cellBelow instanceof model.SoftWall) {
				
				// SoftWall tileBelow = (SoftWall)view.Board.getInstance().getTile(positionBelow);
				String desc = "05 06 07 08";
				SoftWall tileBelow = new view.SoftWall(desc);
				cellBelow.addObserver(tileBelow);
				view.Board.getInstance().setTile(tileBelow, positionBelow);
			}
			
			// set tile where SoftWall was to either empty, empty with softWall shadow, empty with wall shadow or empty with border shadow
			
			int[] positionAbove = {y-1, x};
			model.Element cellAbove = model.Board.getInstance().getCell(positionAbove);
			String desc = "e";
			String file = "20";
			if (cellAbove instanceof model.Wall && positionAbove[0] == 1) {
				desc = "ebs";
				file = "12"; // file shouldn't be set here
			}
			if (cellAbove instanceof model.Wall && positionAbove[0] != 1) {
				desc = "ews";
				file = "12"; // file shouldn't be set here
			}
			if (cellAbove instanceof model.SoftWall) {
				desc = "esws";
				file = "21";
			}
			Image im = new Image("tiles-64x64/" + file + ".png");
			this.getImageView().setImage(im);	
			view.Board.getInstance().setTile(new view.Tile(desc, im), softWallPosition);
		});
		

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		
	}

}

package view;

import javafx.util.Duration;

import java.util.Observable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SoftWall extends Item {
	
	public static final long SOFT_WALL_EXPLOSION = 1500;
	private ImageView imageView;
	private Timeline timeline = new Timeline();
	
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
		
		timeline = new Timeline(
			    new KeyFrame(Duration.millis(125), event -> imageView.setImage(sw1)),
			    new KeyFrame(Duration.millis(250), event -> imageView.setImage(sw2)),
			    new KeyFrame(Duration.millis(375), event -> imageView.setImage(sw3)),
			    new KeyFrame(Duration.millis(500), event -> imageView.setImage(sw4))
			);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		getChildren().add(imageView);

	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		String imageFiles = "63 64 65 66 67 68";
		String[] files = imageFiles.split("\\s+");
		timeline.getKeyFrames().clear();
		timeline = new Timeline();
//		timeline.setCycleCount(1);
		
		for (int frame = 0; frame < files.length; frame++) {
			final int framePlusOne = frame+1;
			KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION/(files.length+1) * framePlusOne), event -> {
				
				Image im = new Image("tiles-64x64/" + files[framePlusOne-1] + ".png");
				imageView.setImage(im);				
			});
			timeline.getKeyFrames().add(keyFrame);
		}
		
		
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(SOFT_WALL_EXPLOSION), event -> {
			
			int[] softWallPosition = ((model.Element) o).getPosition();
			int y = softWallPosition[0];
			int x = softWallPosition[1];
		
			// remove shadow from tile below if present
			
			int[] positionBelow = {y+1, x};
			model.Element cellBelow = model.Board.getInstance().getCell(positionBelow);
			
			if (cellBelow instanceof model.EmptyTile) {
				view.Tile tileBelow = (Tile)view.Board.getInstance().getTile(positionBelow);
				Image im = new Image("tiles-64x64/" + "20" + ".png");
				tileBelow.setImageView(im);
			}
			if (cellBelow instanceof model.SoftWall) {
				
				// SoftWall tileBelow = (SoftWall)view.Board.getInstance().getTile(positionBelow);
				String desc = "05 06 07 08";
				SoftWall tileBelow = new view.SoftWall(desc);
				cellBelow.addObserver(tileBelow);
				view.Board.getInstance().setTile(tileBelow, positionBelow);
			}
			
			// set tile where SoftWall was to either empty, empty with square shadow or empty with round shadow
			
			int[] positionAbove = {y-1, x};
			model.Element cellAbove = model.Board.getInstance().getCell(positionAbove);
			String file = "20";
			if (cellAbove instanceof model.Wall) {
				file = "12";
			}
			if (cellAbove instanceof model.SoftWall) {
				file = "21";
			}
			Image im = new Image("tiles-64x64/" + file + ".png");
			imageView.setImage(im);	
		});
		
		
		
		
		
		
		
		
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
		
		
	}

}

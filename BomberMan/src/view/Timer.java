package view;

import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * view of the timer
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Timer implements Observer {
	private static Timer timer;
	private Image blackSquare = new Image("images/-board-header/timer-black.png"); 
	
	private Timer () {}
	
	/**
	 * singleton pattern
	 * @return the only existing instance of timer, or creates one
	 */
	public static Timer getInstance() {
		if (timer == null) {
			timer = new Timer();
		}
		return timer;
	}
	
	/**
	 * OO pattern
	 * used to add black boxes to the timer to mark passing of time
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.TIME_DECREASE)) {
			ImageView imageView = new ImageView(blackSquare);
			GameHeader gameHeader = GameHeader.getInstance();
			gameHeader.getChildren().add(imageView);
			int numberOfBlocks = ((model.Timer.INITIAL_TIME - (int)args[1]) / model.Timer.STEP);
			int clock = numberOfBlocks < 14 ? 0 : 63;
			int leftInset = 69 + clock + 22 * numberOfBlocks;
			GameHeader.setMargin(imageView, new Insets(75, 0, 0, leftInset));
			
		}
		
	}

}

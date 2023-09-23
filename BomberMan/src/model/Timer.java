package model;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * model of timer
 * 
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Timer extends Observable {

	private static Timer timer;
	public static final int INITIAL_TIME = 280;
	public static final int STEP = 10;
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> future;
	
	private int currTime;
	
	private Timer() {
		reset();
	}
	
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
	 * resets the timer to its INITIAL_TIME
	 */
	public void reset() {
		currTime = INITIAL_TIME;
	}
	
	/**
	 * starts the timer
	 */
	public void start() {
		Runnable decrease = () -> {
			Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread 				
				start();
			});
		};
		future = executor.schedule(decrease, 1000, TimeUnit.MILLISECONDS);
		if (currTime % STEP == 0) {
			Object[] args = { model.ChangeType.TIME_DECREASE, currTime };
			setChanged();
			notifyObservers(args);
		}
		currTime--;
		if (currTime == 0) {
			stop();
			Object[] args = { model.ChangeType.TIME_DECREASE, currTime };
			setChanged();
			notifyObservers(args);
		}
	}
	
	/**
	 * stops the timer
	 */
	public void stop() {
		future.cancel(true);
	}
	
	
}

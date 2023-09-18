package controller;

public abstract class Timer {
	
	@SuppressWarnings("deprecation")
	public static void load() {
		
		model.Timer modelTimer = model.Timer.getInstance();
		view.Timer viewTimer = view.Timer.getInstance();
		modelTimer.addObserver(viewTimer);
		modelTimer.reset();
		modelTimer.start();
		
	}
}

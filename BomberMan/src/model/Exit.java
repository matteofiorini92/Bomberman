package model;


public class Exit extends PowerUp { // extends PowerUp because can be hidden by softwalls
	
	public static final int POINTS = 0;

	public Exit()
	{
		super(POINTS);
		this.setDisappearOnWalkOn(false);
	}

	@Override
	public boolean execute() {
		if (model.Enemy.getAliveEnemies().size() == 0) { // check if there are enemies alive
			this.setDisappearOnWalkOn(true);
			super.execute();
			System.out.println("I'm here");
			return true;
		}
		return false;
	}

}

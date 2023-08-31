package model;

public class Exit extends PowerUp { // extends PowerUp because can be hidden by softwalls
	
	public static final int POINTS = 0;

	public Exit()
	{
		super(POINTS);
		this.setDisappearOnWalkOn(false);
	}

	@Override
	public void execute() {
		System.out.println(model.Enemy.getEnemiesAlive().size());
		System.out.println(this.disappearsOnWalkOn());
		if (model.Enemy.getEnemiesAlive().size() == 0) { // check if there are enemies alive
			this.setDisappearOnWalkOn(true);
			super.execute();
		}
		
		
		
	}

}

package model;


public class Exit extends Item implements Hidable { // extends PowerUp because can be hidden by softwalls
	
//	public static final int POINTS = 0;

	public Exit()
	{
		super(null);
		this.setDisappearOnWalkOn(false);
	}

	@SuppressWarnings("deprecation")
	public boolean getOut() {
		if (model.Enemy.getAliveEnemies().size() == 0) { // check if there are enemies alive
			this.setDisappearOnWalkOn(true);
			Object[] args = { model.ChangeType.GET_OUT };
			setChanged();
			notifyObservers(args);
			return true;
		}
		return false;
	}

}

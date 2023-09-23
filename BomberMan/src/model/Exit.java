package model;

/**
 * model class for exit
 * @author Matteo
 *
 */
public class Exit extends Item implements Hidable { // extends PowerUp because can be hidden by softwalls
	
	public Exit()
	{
		super(null);
		this.setDisappearOnWalkOn(false);
	}

	/**
	 * try to get to the next level. only works if there are no enemies alive
	 * @return true if bomberman gets to the next level, false otherwise
	 */
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

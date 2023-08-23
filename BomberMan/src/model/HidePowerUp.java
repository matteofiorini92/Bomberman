package model;

public interface HidePowerUp {

	public void setHiddenPowerUp(model.PowerUp powerUp);
	public boolean isHidingSomething();
	public void showHiddenPowerUp();
	public model.PowerUp getHiddenPowerUp();
}

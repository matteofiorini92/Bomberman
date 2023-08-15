package view;

import java.util.Observable;

import javafx.scene.image.Image;
import model.Direction;

public abstract class Enemy extends Character {

	public Enemy(int[] position, Image image)
	{
		super(position, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub

	}

}

package view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * view of the base scene to which the base group is added
 * @author Matteo
 *
 */
public class BaseScene extends Scene {
	
	private static BaseScene baseScene;

	private BaseScene()
	{
		super(BaseGroup.getInstance(), Item.ITEM_WIDTH * 17, Item.ITEM_HEIGHT * 15, Color.BLACK);
	}
	
	public static BaseScene getInstance() {
		if (baseScene == null) {
			baseScene = new BaseScene();
		}
		return baseScene;
	}

}

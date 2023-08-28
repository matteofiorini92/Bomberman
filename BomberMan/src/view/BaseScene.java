package view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class BaseScene extends Scene {
	
	public static BaseScene baseScene;

	private BaseScene()
	{
		super(BaseGroup.getInstance(), view.Item.ITEM_WIDTH * 17, view.Item.ITEM_HEIGHT * 14.5, Color.BLACK);
	}
	
	public static BaseScene getInstance() {
		if (baseScene == null) {
			baseScene = new BaseScene();
		}
		return baseScene;
	}

}
package view;

import javafx.scene.Group;

public class BaseGroup extends Group {
	public static BaseGroup baseGroup;
	
	private BaseGroup() {}
	
	public static BaseGroup getInstance() {
		if (baseGroup == null) {
			baseGroup = new BaseGroup();
		}
		return baseGroup;
	}
}

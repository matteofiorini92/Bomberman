package view;

import javafx.scene.Group;

/**
 * view base group to which all visible nodes are added
 * @author Matteo
 *
 */
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

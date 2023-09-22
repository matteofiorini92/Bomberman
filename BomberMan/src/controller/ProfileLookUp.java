package controller;


/**
 * to load the profile lookup screen
 * @author Matteo
 *
 */
public abstract class ProfileLookUp {
	
	public static void load() {
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.Boards.ProfileLookUp());		
	}
	
}

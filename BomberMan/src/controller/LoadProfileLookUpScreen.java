package controller;

import view.BaseGroup;

public class LoadProfileLookUpScreen {
	public LoadProfileLookUpScreen() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.BoardProfileLookUp());
	}
}

package controller;

import view.BaseGroup;

public class LoadPlayerLookUpScreen {
	public LoadPlayerLookUpScreen() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.BoardProfileLookUp());
	}
}

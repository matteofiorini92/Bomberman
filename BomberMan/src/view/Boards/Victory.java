package view.Boards;

public class Victory extends End {

	public Victory()
	{
		super(
			"You Won!",
			"New Game",
			15,
			event -> controller.NewGame.load(),
			"Profile Screen",
			12,
			event -> {
				String playerName = model.Player.getInstance().getName();
				controller.ExistingProfile.load(playerName);
			}
		);
	}

}

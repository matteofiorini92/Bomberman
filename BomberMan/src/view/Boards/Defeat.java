package view.Boards;

/**
 * view of defeat board
 * @author Matteo
 *
 */
public class Defeat extends End {
	
	public Defeat()
	{
		super(
			"You Lost :(",
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

package view.boards;

/**
 * a profile board for an existing player profile
 * @author Matteo
 *
 */
public class ExistingProfile extends Profile {

	/**
	 * 
	 * @param wins current wins of this player
	 * @param losses current losses of this player
	 * @param totalScore current total score of this player
	 * @param nickname nickname of this player
	 * @param avatar current avatar of this player
	 */
	public ExistingProfile(int wins, int losses, int totalScore, String nickname, model.Avatar avatar)
	{
		super(wins, losses, totalScore, nickname, avatar);
	}

}

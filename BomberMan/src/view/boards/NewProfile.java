package view.boards;

/**
 * a profile board with all stats at 0, no nickname and default avatar
 * @author Matteo
 *
 */
public class NewProfile extends Profile {
	
	public NewProfile() {
		super(0, 0, 0, null, model.Avatar.WHITE);
	}
}

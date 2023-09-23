package model;

/**
 * model of Player
 * @author Matteo
 *
 */
public class Player {
	private static Player player;
	public static final int INITIAL_SCORE = 0;
	private static String nickname;
	private static int totalScore;
	private static int wins;
	private static int losses;
	private static Avatar avatar;
	
	private Player() {
		setScore(INITIAL_SCORE);
	}
	
	/**
	 * singleton pattern
	 * @return the only existing instance of Player, or creates one
	 */
	public static Player getInstance() { 
		if (player == null) {
			player = new Player();
			wins = 0;
			losses = 0;
			totalScore = 0;
			
		}
		return player;
	}

	public String getName() { return nickname; }
	public void setName(String nickname) { Player.nickname = nickname; }

	public int getScore() {	return totalScore; }
	public void setScore(int totalScore) { Player.totalScore = totalScore; }
	
	public int getWins() { return wins; }
	public void setWins(int wins) {	Player.wins = wins; }
	
	public int getLosses() { return losses;	}
	public void setLosses(int losses) {	Player.losses = losses; }
	
	public Avatar getAvatar() {	return avatar; }
	public void setAvatar(Avatar avatar) { Player.avatar = avatar; }

	public void addPoints(int points) { totalScore += points; }
	public void losePoints(int points) { totalScore -= points; }
	
}

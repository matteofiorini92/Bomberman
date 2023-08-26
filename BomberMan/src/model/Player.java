package model;

public class Player {
	private static Player player;
	public static final int INITIAL_SCORE = 0;
	private String nickname;
	private int totalScore;
	private int wins;
	private int losses;
	private Avatar avatar;
	
	private Player() {
		setScore(INITIAL_SCORE);
	}
	
	public static Player getInstance() { 
		if (player == null) {
			player = new Player();
		}
		return player;
	}

	public String getName() { return nickname; }
	public void setName(String nickname) { this.nickname = nickname; }

	public int getScore() {	return totalScore; }
	public void setScore(int totalScore) { this.totalScore = totalScore; }

	public void addPoints(int points) { totalScore += points; }
	public void losePoints(int points) { totalScore -= points; }
	
}

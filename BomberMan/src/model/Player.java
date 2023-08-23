package model;

public class Player {
	private static Player player;
	public static final int INITIAL_SCORE = 0;
	private String name;
	private int score;
	
	private Player() {
		setScore(INITIAL_SCORE);
	}
	
	public static Player getInstance() { 
		if (player == null) {
			player = new Player();
		}
		return player;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getScore() {	return score; }
	public void setScore(int score) { this.score = score; }

	public void addPoints(int points) { score += points; }
	public void losePoints(int points) { score -= points; }
	
}

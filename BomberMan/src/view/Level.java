package view;

public class Level {
	
	private String levelNumber;
	private Board board;

	public Level(String levelNumber)
	{
		this.levelNumber = levelNumber;
		this.board = new Board(levelNumber);
	}

}

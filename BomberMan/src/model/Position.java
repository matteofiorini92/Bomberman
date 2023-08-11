package model;

public class Position {
	private int[] p = new int[2];
	
	public Position(int x, int y) {
		p[0] = x;
		p[1] = y;
	}
	
	public void setPosition(int[] p) {
		this.p[0] = p[0];
		this.p[1] = p[1];
	}
	
	public int[] getPosition() { return p; }
}

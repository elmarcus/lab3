package board;

public class Piece {

	private int player;
	private int i;
	private int j;
	
	public Piece(int i, int j, int player)
	{
		this.player = player;
		this.i = i;
		this.j = j;
	}
	
	public void setPlayer(int player)
	{
		this.player = player;
	}
	
	public int getPlayer()
	{
		return this.player;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	
}

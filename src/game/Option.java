package game;

import java.util.ArrayList;
import java.util.List;

import board.Piece;

public class Option {
	
	public int numOfFlips;
	public int i;
	public int j;
	public List<Piece> piecesToFlip = new ArrayList<Piece>();
	
	public Option(int n, int i, int j)
	{
		this.i = i;
		this.j = j;
		this.numOfFlips = n;
	}

	
	
}

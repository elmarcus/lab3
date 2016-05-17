package board;

import java.util.ArrayList;
import java.util.List;
import game.Option;

public class Board {
	private Piece board[][];
	private List<Piece> playerOnePieces;
	private List<Piece> playerTwoPieces;
	private int size = 0;
	
	public Board(int size)
	{
		board = new Piece[size][size];
		playerOnePieces = new ArrayList<Piece>();
		playerTwoPieces = new ArrayList<Piece>();
		this.size = size;
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				board[i][j] = new Piece(i, j, 0);
			}
		}
	}
	
	public void print()
	{
		StringBuilder ln = null;
		
		for(int i = 0; i < size; i++)
		{
			ln = new StringBuilder();
			
			for(int j = 0; j < size; j++)
			{
				if(board[i][j] == null)
				{
					ln.append("- ");
				
				}
				else if(board[i][j].getPlayer() == 1)
				{
					ln.append("w ");
				}
				else
				{
					ln.append("b ");
				}
			}
			System.out.println(ln.toString());
		}
	}

	public void placePiece(int i, int j, int player)
	{
		if(player == 1)
		{
			playerOnePieces.add(new Piece(i,j, player));
		}
		else
		{
			playerTwoPieces.add(new Piece(i,j, player));
		}
		
		board[i][j].setPlayer(player);
	}
	
	public List<Option> getAvailableMoves(int player)
	{
		List<Option> availableMoves = new ArrayList<Option>();
		List<Piece> piecesToScan;
		int opponent;
		
		if(player == 1)
		{
			opponent = 2;
			piecesToScan = playerOnePieces;
		}
		else
		{
			opponent = 1;
			piecesToScan = playerTwoPieces;
		}
		
		for(Piece p: piecesToScan)
		{
			List<Piece> neighbors = getSurroundingValues(p);
			
			for(Piece n: neighbors)
			{
				if(n.getPlayer() == opponent)
				{
					int iDir = p.getI() - n.getI(); 
					int jDir = p.getJ() - n.getJ();
					
					int i = n.getI();
					int j = n.getJ();
					
					int f = 1;
					
					while(true)
					{
						i += iDir;
						j += jDir;
						
						if(i == size || i < 0 || j == size || j < 0)
							break;
						else if(board[i][j].getPlayer() == 0)
						{
							availableMoves.add(new Option(f,i,j));
							break;
						}
						else
							f++;
					}
				}
			}
		}
		
		return availableMoves;
	}
	
	public List<Piece> getSurroundingValues(Piece p)
	{
		List<Piece> values = new ArrayList<Piece>();
		
		if(p.getI() - 1 >= 0)
		{
			values.add(board[p.getI()-1][p.getJ()]);
			
			if(p.getJ() - 1 >= 0)
				values.add(board[p.getI()-1][p.getJ()-1]);
			
			if(p.getJ() + 1 != size)
				values.add(board[p.getI()-1][p.getJ()+1]);
		}
		
		if(p.getI() + 1 != size)
		{
			values.add(board[p.getI()+1][p.getJ()]);
			
			if(p.getJ() - 1 >= 0)
				values.add(board[p.getI()+1][p.getJ()-1]);
			if(p.getJ() + 1 != size)
				values.add(board[p.getI()+1][p.getJ()+1]);
		}
		
		if(p.getJ() - 1 >= 0)
		{
			values.add(board[p.getI()][p.getJ()-1]);
		}
		
		if(p.getJ() + 1 != size)
		{
			values.add(board[p.getI()][p.getJ()+1]);
		}

		return values;
	}
	
	public Board getCopy()
	{
		Board b = new Board(this.size);
		b.setBoard(this.board);
		b.setPlayerOnePieces(this.playerOnePieces);
		b.setPlayerTwoPieces(this.playerTwoPieces);
		
		return b;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	public void setPlayerOnePieces(List<Piece> playerOnePieces) {
		this.playerOnePieces = playerOnePieces;
	}

	public void setPlayerTwoPieces(List<Piece> playerTwoPieces) {
		this.playerTwoPieces = playerTwoPieces;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}

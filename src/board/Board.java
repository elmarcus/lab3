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

		board[size/2][size/2].setPlayer(1);
		board[size/2-1][size/2-1].setPlayer(1);
		playerOnePieces.add(new Piece(size/2,size/2, 1));
		playerOnePieces.add(new Piece(size/2-1,size/2-1, 1));
		board[size/2-1][size/2].setPlayer(2);
		board[size/2][size/2-1].setPlayer(2);
		playerTwoPieces.add(new Piece(size/2-1,size/2, 2));
		playerTwoPieces.add(new Piece(size/2,size/2-1, 2));

	}

	public void print()
	{
		StringBuilder ln = null;

		for(int i = 0; i < size; i++)
		{
			ln = new StringBuilder();

			for(int j = 0; j < size; j++)
			{
				if(board[i][j].getPlayer() == 0)
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

	public void placePiece(Option o, int player) throws Exception
	{
		if(player == 1)
		{
			playerOnePieces.add(new Piece(o.i,o.j, player));
		}
		else
		{
			playerTwoPieces.add(new Piece(o.i,o.j, player));
		}

		if(board[o.i][o.j].getPlayer() != 0)
			throw new Exception();

		board[o.i][o.j].setPlayer(player);

		if(o.piecesToFlip.size() != 0)
		{

			for(Piece p: o.piecesToFlip)
			{
				board[p.getI()][p.getJ()].setPlayer(player);
				if(player == 1)
				{
					playerOnePieces.add(new Piece(p.getI(),p.getJ(), player));
					playerTwoPieces.remove(p);
				}
				else
				{
					playerTwoPieces.add(new Piece(p.getI(),p.getJ(), player));
					playerOnePieces.remove(p);
				}
			}
		}
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
					int iDir = -1 *(p.getI() - n.getI()); 
					int jDir = -1*(p.getJ() - n.getJ());

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
		Piece new_board[][] = new Piece[size][size];
		for (int i = 0; i < size; i++){
			for (int j = 0 ; j < size; j++) {
				Piece old_piece = this.board[i][j];
				new_board[i][j] = new Piece(old_piece.getI(), old_piece.getJ(), old_piece.getPlayer());
			}
		}
		b.setBoard(new_board);
		List<Piece> new_playerOnePieces = new ArrayList<Piece>();
		for (Piece p : this.playerOnePieces) {
			new_playerOnePieces.add(new Piece(p.getI(), p.getJ(), p.getPlayer()));
		}
		List<Piece> new_playerTwoPieces = new ArrayList<Piece>();
		for (Piece p : this.playerTwoPieces) {
			new_playerTwoPieces.add(new Piece(p.getI(), p.getJ(), p.getPlayer()));
		}
		b.setPlayerOnePieces(new_playerOnePieces);
		b.setPlayerTwoPieces(new_playerTwoPieces);

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

	public void findScore() {

		if(playerOnePieces.size() > playerTwoPieces.size())
		{
			System.out.println("PLAYER 1 WINS!");

		}
		else if(playerOnePieces.size() < playerTwoPieces.size())
		{
			System.out.println("PLAYER 2 WINS!");
		}
		else
		{
			System.out.println("DRAW");
		}

		System.out.println("PLAYER 1: " + Integer.toString(playerOnePieces.size()));
		System.out.println("PLAYER 2: " + Integer.toString(playerTwoPieces.size()));
	}



}

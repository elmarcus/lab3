package board;

public class Board {
	public int board[][] = new int[8][8];
	
	public void print()
	{
		StringBuilder ln = null;
		
		for(int i = 0; i < 8; i++)
		{
			ln = new StringBuilder();
			
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j] == 0)
				{
					ln.append("- ");
				
				}
				else if(board[i][j] == 1)
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
		
	}
	

}

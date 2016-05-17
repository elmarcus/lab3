package game;

import board.Board;
import computerPlayer.*;

public class Controller {
	
	
	public static void main(String[] args)
	{
		int boardSize = Integer.parseInt(args[0]);
		
		if(boardSize % 2 == 1)
			boardSize++;
			
		Board board = new Board(boardSize);
		
		SmartPlayer player1 = new SmartPlayer();
		SmartPlayer player2 = new SmartPlayer();
		
		//setup phase
		board.placePiece(boardSize/2 , boardSize/2, 1);
		board.placePiece(boardSize/2+1 , boardSize/2+1, 1);
		board.placePiece(boardSize/2+1 , boardSize/2, 2);
		board.placePiece(boardSize/2 , boardSize/2+1, 2);
		
		//play game phase
	}
}

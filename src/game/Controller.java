package game;

import java.util.List;
import java.util.Scanner;

import board.Board;
import computerPlayer.*;

public class Controller {
	
	
	public static void main(String[] args)
	{
		int boardSize = Integer.parseInt(args[0]);
		boolean human = false;
		
		boardSize&=0xFE;
		
		System.out.println("Human Player 1? Y/N");
		
		Scanner s = new Scanner(System.in);
		String userInput = s.next();
		
		Board board = new Board(boardSize);
		
		if(userInput.toLowerCase().equals("y"))
		{
			human = true;
		}
		else
		{
			SmartPlayer player1 = new SmartPlayer();
		}
		
		SmartPlayer player2 = new SmartPlayer();
		
		//setup phase
		try
		{
		board.placePiece(boardSize/2 , boardSize/2, 1);
		board.placePiece(boardSize/2+1 , boardSize/2+1, 1);
		board.placePiece(boardSize/2+1 , boardSize/2, 2);
		board.placePiece(boardSize/2 , boardSize/2+1, 2);
		}
		catch(Exception e)
		{
			System.err.println("I have no idea how this happened");
		}
		
		
		int round = 0;
		boolean pass = false;
		
		//play game phase
		while(pass)
		{
			round++;
			//PLAYER 1 TURN
			System.out.println("ROUND: " + Integer.toString(round));
			board.print();
			
			if(!human) 
			{
				List<Option> options = board.getAvailableMoves(1);
				//do stuff with alpha beta pruning
				//make move
				board.placePiece(p.i, p.j, 1);
			}
			else
			{
				while(true)
				{
					System.out.println("Choose a row and column");
					int row = s.nextInt();
					int col = s.nextInt();
					
					try
					{
						board.placePiece(row, col, 1); 
					}
					catch(Exception e)
					{
						continue;
					}
					
					break;
				}
			}
			
			//PLAYER 2 TURN
			List<Option> options = board.getAvailableMoves(2);
			board.placePiece(p.i, p.j, 2);
		}
		
	}
}

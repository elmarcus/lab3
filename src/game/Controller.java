package game;

import java.util.List;
import java.util.Scanner;

import board.Board;
import computerPlayer.*;

public class Controller {


	public static void main(String[] args)
	{

		boolean human = false;
		AlphaBetaPrunner abp = null;
		int boardSize = 0;
		Board board = null;


		Scanner s = new Scanner(System.in);
		while(true)
		{
			System.out.println("Select board size");
			boardSize = s.nextInt();
			boardSize&=0xFE;
			boardSize = Math.max(boardSize, 8);
			System.out.println("Board Size is: "+Integer.toString(boardSize));
			board = new Board(boardSize);
			abp = new AlphaBetaPrunner(-1, boardSize, boardSize);

			System.out.println("Human Player 1? Y/N");

			String userInput = s.next();

			if(userInput.toLowerCase().equals("y"))
			{
				human = true;
				break;
			}
			else
			{
				SmartPlayer player1 = new SmartPlayer();
				break;
			}
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
		int pass = 0;
		System.out.println(boardSize);
		
		//play game phase
		while(pass < 2)
		{
			pass = 0;
			round++;
			System.out.println("ROUND: " + Integer.toString(round));
			board.print();

			//PLAYER 1 TURN
			if(!human) 
			{
				Option option = abp.getNextMove(board, 1);
				//do stuff with alpha beta pruning
				//make move
				if(option != null)
				{
					try {
						board.placePiece(option.i, option.j, 1);
					} catch (Exception e) {
						System.err.println("MOVE EXCEPTION.PLAYER 1");
					}
				}
				else
					pass++;
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
			Option option = abp.getNextMove(board, 2);

			if(option != null)
			{
				try {
					board.placePiece(option.i, option.j, 2);
				} catch (Exception e) {
					System.err.println("MOVE EXCEPTION.PLAYER 2");
				}
			}
			else pass++;
		}
		
//		board.findScore();

	}
}

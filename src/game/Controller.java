package game;

import java.util.List;
import java.util.Scanner;
import board.Board;
import board.Piece;

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
			boardSize = Math.max(boardSize, 4);
			System.out.println("Board Size is: "+Integer.toString(boardSize));
			board = new Board(boardSize);
			abp = new AlphaBetaPrunner(7, boardSize*boardSize, -1*boardSize*boardSize, false);

			System.out.println("Human Player 1? Y/N");

			String userInput = s.next();

			if(userInput.toLowerCase().equals("y"))
			{
				human = true;
				break;
			}
			else
			{
				break;
			}
		}


		//setup phase

		int round = 0;
		int pass = 0;
		System.out.println(boardSize);
		board.print();
		//play game phase
		while(pass < 2)
		{
			pass = 0;
			round++;
			System.out.println("ROUND: " + Integer.toString(round));


			//PLAYER 1 TURN
			if(!human) 
			{
				Option option = abp.getNextMove(board, 1);

				if(option != null)
				{
					try {
						board.placePiece(option, 1);
					} catch (Exception e) {
						System.err.println("MOVE EXCEPTION.PLAYER 1");
					}
				}
				else
					pass++;
				board.print();
			}
			else
			{
				while(true)
				{

					int linum = 1;
					List<Option> suggestedMoves = board.getAvailableMoves(1);
					System.out.println("Choose a suggestion");
					for(Option o: suggestedMoves)
					{
						System.out.println("("+Integer.toString(linum) + ") R:" + Integer.toString(o.i + 1) + " C:" + 
								Integer.toString(o.j + 1));
						linum++;
					}

					System.out.println("(" + Integer.toString(linum) + ") Pass");

					try
					{
						int input = 0;
						input = s.nextInt();

						if(input < 0 || input > linum)
							continue;

						if(input == linum)
						{
							break;
						}
						
						board.placePiece(suggestedMoves.get(input - 1), 1); 
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
					board.placePiece(option, 2);
				} catch (Exception e) {
					System.err.println("MOVE EXCEPTION.PLAYER 2");
				}

			}
			else pass++;
			System.out.println("******************");
			board.print();
		}

		board.findScore();

	}
}

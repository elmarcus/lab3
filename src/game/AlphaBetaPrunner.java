package game;

import board.Board;
import game.Option;
import java.util.*;


public class AlphaBetaPrunner {
	
	private int max_depth;
	private int default_max;
	private int default_min;
	
	public AlphaBetaPrunner(int max_depth, int default_max, int default_min) {
		super();
		this.max_depth = max_depth;
		this.default_max = default_max;
		this.default_min = default_min;
	}

	private int max (Board board, int player,  int alpha, int beta, int depth, int max_score, int min_score) {
		if (cutoffTest(board, depth, alpha, beta))
			return max_score;
		List< Option > options = board.getAvailableMoves(player); 
		if (options.size() == 0)
			return max_score;
		int ft = beta;
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			ft = min(new_board, getNextPlayer(player), alpha, beta, depth +1, max_score + opt.numOfFlips, min_score); ///
			alpha = Math.max(alpha, ft);
		}
		if (alpha >= ft)
				return ft;	
		return alpha;
	}
	
	private int min(Board board, int player,  int alpha, int ft, int depth, int max_score, int min_score) {
		if (cutoffTest(board, depth, alpha, ft))
			return min_score;
		List< Option > options = board.getAvailableMoves(player); //replace with real function
		if (options.size() == 0)
			return min_score;
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			ft = Math.min( ft, max(new_board, getNextPlayer(player), alpha, ft, depth +1, max_score, min_score + opt.numOfFlips)); ///
			if (ft < alpha)
				return alpha;
		}
		return ft;	
	}
	
	public Option getNextMove(Board board, int player) {
		List< Option > options = board.getAvailableMoves(player); //replace with real function
		int max = -1;
		Option bestOption = null;
		for (Option opt : options) {
			int m = max(addOptionToBoard(board, player, opt), player, this.default_min, this.default_max, 1, opt.numOfFlips, 0);
			if (m > max) {
				max = m;
				bestOption = opt;
			}
		}
		return bestOption;
	}

	private boolean cutoffTest(Board board, int depth, int alpha, int beta) {
		if (depth == this.max_depth || alpha == beta)
			return true;
		else
			return false;
	}

	
	private int getNextPlayer (int currentPalyer) {
		return currentPalyer % 2 + 1;
	}
	
	private Board addOptionToBoard(Board board, int player, Option option) {
		Board new_board = board.getCopy(); //replace with real copy constructor
		try{
			new_board.placePiece(option, player);
		}
		catch(Exception e)
		{
			System.err.println("INVALID  MOVE. ALPHA BETA PRUNING");
		}
		return new_board;
	}
}

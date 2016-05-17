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

	private int max (Board board, int player,  int alpha, int beta, int depth) {
		if (cutoffTest(board, depth, alpha, beta))
			return alpha;
		List< Option > options = board.getAvailableMoves(player); 
		if (options.size() == 0)
			return alpha;
		int ft = beta;
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			ft = min(new_board, getNextPlayer(player), alpha, beta, depth +1);
			alpha = Math.max(alpha, ft);
		}
		if (alpha >= ft)
				return ft;	
		return alpha;
	}
	
	private int min(Board board, int player,  int alpha, int ft, int depth) {
		if (cutoffTest(board, depth, alpha, ft))
			return ft;
		List< Option > options = board.getAvailableMoves(player); //replace with real function
		if (options.size() == 0)
			return ft;
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			ft = Math.min( ft, max(new_board, getNextPlayer(player), alpha, ft, depth +1));
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
			int m = max(addOptionToBoard(board, player, opt), player, this.default_max, this.default_min, 1);
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
		Board new_board = board.getCopy();
		new_board.placePiece(option.i, option.j, player);
		return new_board;
	}
}

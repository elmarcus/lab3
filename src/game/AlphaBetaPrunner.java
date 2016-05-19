package game;

import board.Board;
import game.Option;
import java.util.*;


public class AlphaBetaPrunner {
	
	private int max_depth;
	private int default_max;
	private int default_min;
	private boolean smart = false;
	
	public AlphaBetaPrunner(int max_depth, int default_max, int default_min, boolean smart) {
		super();
		this.max_depth = max_depth;
		this.default_max = default_max;
		this.default_min = default_min;
		this.smart = smart;
	}

	private int max (Board board, int player,  int alpha, int beta, int depth, int max_score, int min_score) {
		if (cutoffTest(board, depth, alpha, beta))
			return max_score;
		List< Option > options = board.getAvailableMoves(player); 
		if (options.size() == 0)
			return max_score;
		if (this.smart) {
			options = sortByPriority(options, board.getSize());
		}
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
		if (this.smart) {
			options = sortByPriority(options, board.getSize());
		}
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			ft -= Math.min( ft, max(new_board, getNextPlayer(player), alpha, ft, depth +1, max_score, min_score + opt.numOfFlips)); ///
			if (ft < alpha)
				return alpha;
		}
		return ft;	
	}
	
	public Option getNextMove(Board board, int player) {
		List< Option > options = board.getAvailableMoves(player); //replace with real function
		int max = -1;
		Option bestOption = null;
		if (this.smart) {
			options = sortByPriority(options, board.getSize());
		}
		for (Option opt : options) {
			Board new_board = addOptionToBoard(board, player, opt);
			int new_player = getNextPlayer(player);
			int m = min(new_board, new_player, this.default_min, this.default_max, 1, opt.numOfFlips, 0);
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
	
	private List <Option> sortByPriority(List <Option> options, int board_size) {
		List <Option> sorted_options = new ArrayList <Option> ();
		List <Double> priorities = new ArrayList <Double> ();
		for (int i = 0; i < options.size(); i++) {
			double pr = getPriority(options.get(i), board_size);
			boolean inserted = false;
			for (int j = 0; j < sorted_options.size(); j++) {
				if (priorities.get(j) < pr) {
					sorted_options.set(j, options.get(i));
					priorities.set(j, pr);
					inserted = true;
					break;
				}
			}
			if (!inserted) {
				sorted_options.add(options.get(i));
				priorities.add(pr);
			}
		}
		return sorted_options;
	}
	
	
	private double getPriority(Option option, int board_size) {
		
		//weights
		double edgeWeight = -0.5;
		double cornerWeight = 2;
		double gainWeight = 1;
		double nearCornerWeight = -1;
		
		double priority = 0;
		
		if (option.i == 0 || option.i == board_size-1)
			if (option.j == 0 || option.j == board_size - 1)
				priority += cornerWeight;
			else if (option.j == 1 || option.j == board_size - 2) 	
				priority += nearCornerWeight;
			else
				priority += edgeWeight;
		if (option.j == 0 || option.j == board_size-1)
			if (option.i == 1 || option.i == board_size - 2) 	
				priority += nearCornerWeight;
			else
				priority += edgeWeight;
		priority += gainWeight * ( (double)(option.numOfFlips) / (double)(board_size - 2) );
		
		return priority;

	}
}

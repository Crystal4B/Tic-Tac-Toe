import javax.swing.JButton;

public class Minimax
{
	/**
	 * Calculates the most optimal move for the AI to execute
	 * @param board the current state of the board
	 * @return the coordinates of the move deemed most optimal
	 */
	public static int[] computerMove(JButton board[][])
	{
		int bestScore =  -Integer.MAX_VALUE;
		int bestMove[] = new int[2];
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[row].length; col++)
			{
				if (board[row][col].getText().equals(""))
				{
					board[row][col].setText("O");
					int score = minimax(board, 0, false);
					board[row][col].setText("");
					if (score > bestScore)
					{
						bestScore = score;
						bestMove[0] = row;
						bestMove[1] = col;
					}
				}
			}
		}
		return bestMove;
	}
	
	/**
	 * Calculates the most optimal move for the AI to execute
	 * @param board the state of the board
	 * @param depth the depth in the tree
	 * @param isMaximizing whether it's maximizing or minimizing
	 * @return best score on that board
	 */
	private static int minimax(JButton board[][], int depth, boolean isMaximizing)
	{
		EndState result = Window.isVictory(board);
		if (result != null)
		{
			String state = result.getState();
			switch(state)
			{
				case "O": return 1;
				case "X": return -1;
				case "T": return 0;
			}
		}
	
		int bestScore = isMaximizing ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[row].length; col++)
			{
				if (board[row][col].getText().equals(""))
				{
					board[row][col].setText(isMaximizing ? "O" : "X");
					if (isMaximizing)
					{
						bestScore = Math.max(minimax(board, depth + 1, false), bestScore);
					}
					else
					{
						bestScore = Math.min(minimax(board, depth + 1, true), bestScore);
					}
					board[row][col].setText("");
				}
			}
		}
		return bestScore;
	}
}
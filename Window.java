import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame
{
	private JButton buttonGrid[][];
	private String player;
	private boolean isSingle;

	public Window(boolean isSingle)
	{
		super("XO Game");

		buttonGrid = new JButton[3][3];
		player = "X";
		this.isSingle = isSingle;

		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(3, 3));
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 3; col++)
			{
				buttonGrid[row][col] = new JButton("");
				buttonGrid[row][col].addActionListener(buttonListener);
				buttonGrid[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
				buttonGrid[row][col].setFocusPainted(false);
				buttonGrid[row][col].setContentAreaFilled(false);
				gamePanel.add(buttonGrid[row][col]);
			}
		}

		setSize(500, 500);
		setContentPane(gamePanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

	/**
	 * Listener for executing moves as buttons are pressed
	 */
	ActionListener buttonListener = (ActionEvent e) ->
	{
		for (int row = 0; row < buttonGrid.length; row++)
		{
			for (int col = 0; col < buttonGrid[row].length; col++)
			{
				if (buttonGrid[row][col] == e.getSource())
				{
					JButton button = (JButton) e.getSource();
					button.setText(player);
					button.setEnabled(false);

					EndState playerWon = isVictory(buttonGrid);
					if (playerWon != null)
					{
						endScreen();
					}
					player = player.equals("X") ? "O" : "X";

					if (isSingle && playerWon == null)
					{
						int bestMove[] = Minimax.computerMove(buttonGrid);
						buttonGrid[bestMove[0]][bestMove[1]].setText(player);
						buttonGrid[bestMove[0]][bestMove[1]].setEnabled(false);

						EndState computerWon = isVictory(buttonGrid);
						if (computerWon != null)
						{
							endScreen();
						}
						player = player.equals("X") ? "O" : "X";
					}
					break;
				}
			}
		}
	};

	/**
	 * Updates the screen to the finished state
	 */
	public void endScreen()
	{
		for (JButton buttonRow[] : buttonGrid) 
		{
			for (JButton btn : buttonRow)
			{
				btn.setEnabled(false);
			}
		}
	}

	/**
	 * Checks the current state of the game
	 * @param board the board being checked for it's state
	 * @return EndState object showing who won and where it has happen
	 */
	public static EndState isVictory(JButton board[][])
	{
		// Horizontal / Vertical
		for (int i = 0; i < board.length; i++)
		{
			if (board[i][0].getText().equals(board[i][1].getText()) && board[i][1].getText().equals(board[i][2].getText()) && !board[i][0].getText().equals(""))
			{
				return new EndState(board[i][0].getText(), "H", i);
			}
			else if (board[0][i].getText().equals(board[1][i].getText()) && board[1][i].getText().equals(board[2][i].getText()) && !board[0][i].getText().equals(""))
			{
				return new EndState(board[0][i].getText(), "V", i);
			}
		}

		// Diagnal
		String middle = board[1][1].getText();
		if (board[0][0].getText().equals(middle) && board[2][2].getText().equals(middle) && !board[1][1].getText().equals(""))
		{
			return new EndState(board[1][1].getText(), "DD");
		}
		if (board[0][2].getText().equals(middle) && board[2][0].getText().equals(middle) && !board[1][1].getText().equals(""))
		{
			return new EndState(board[1][1].getText(), "DU");
		}

		// Next Turn Possible ?
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j].getText().equals(""))
				{
					return null;
				}
			}
		}
		return new EndState("T");
	}
}
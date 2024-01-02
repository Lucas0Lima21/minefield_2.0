package vision;

import javax.swing.JFrame;

import model.Game_Board;

@SuppressWarnings("serial")
public class Main_Screen extends JFrame{
	
	public Main_Screen() {
		Game_Board GameBoard = new Game_Board(16, 30, 50);
		add(new Panel(GameBoard));
		
		setTitle("minefield");
		setSize(690,438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main_Screen();
	}
}

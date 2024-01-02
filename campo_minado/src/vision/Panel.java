package vision;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Game_Board;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	public  Panel(Game_Board gameBoard) {
		setLayout(new  GridLayout( gameBoard.getLines(), gameBoard.getColumns()));
		
		gameBoard.toEachField(f -> add(new Button_Field(f)));
		
		gameBoard.registerObserve(e -> {
		
			SwingUtilities.invokeLater(() -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "wim :)");
				} else {
					JOptionPane.showMessageDialog(this, "Game over :(");
				}
			
				gameBoard.restart();
			});
		});
	}
}

package vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import model.Field;
import model.Field_Event;
import model.Field_Observe;

@SuppressWarnings("serial")
public class Button_Field extends JButton implements Field_Observe,  MouseListener{
	
	private final Color  bg_default = new Color(184,184,184);
	private final Color  bg_mark = new Color(8,179,247);
	private final Color  bg_explode = new Color(189,66,68);
	private final Color  text_greem = new Color(0,100,0);

	private Field field;
	
	public Button_Field(Field field) {
		this.field = field;
		setBackground(bg_default);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		field.registerObserve(this);
	}
	
	@Override
	public void eventOccurred(Field field, Field_Event event) {
		switch(event) {
		case OPEN:
			applyStyleOPen();
			break;
		case MARK:
			applyStyleMark();
			break;
		case EXPLODE:
			applyStyleExplode();
			break;
		default:
			applyStyleDefault();
			break;
		}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
		
	}

	private void applyStyleDefault() {
		setBackground(bg_default);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}

	private void applyStyleExplode() {
		setBackground(bg_explode);
		setForeground(Color.WHITE);
		setText("X");
		
	}

	private void applyStyleMark() {
		setBackground(bg_mark);
		setForeground(Color.BLACK);
		setText("M");
	}
		
	private void applyStyleOPen() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if(field.isMine()) {
			setBackground(bg_explode);
			return;
		}
		
		setBackground(bg_default);
		
		switch (field.mineNeighbor()) {
		case 1:
			setForeground(text_greem);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		String values = !field.safeNeighbors() ? 
				field.mineNeighbor() + "" : "";
		setText(values);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			field.open();
		} else {
			field.changeMarked();
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}

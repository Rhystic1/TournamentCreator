package ui;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class DarkButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	@wbp.parser.constructor
	*/
	public DarkButton(String text) {
		super(text);

		Color back = new Color(23, 23, 23);

		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, back, Color.black));
		setFocusPainted(false);
		setBackground(Color.black);
		setForeground(Color.green);
		setOpaque(false);
	}

}

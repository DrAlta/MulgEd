import javax.swing.*;
import java.awt.Dimension;

/**
 *	class BlackLabel
 *	This class inherits from JLabel, and makes sure the text is black
 *	Also, the size can be set using a special constructor
 **/

public class BlackLabel extends JLabel {
	
	// Members
	Dimension min = null;
	
	// CTor
	public BlackLabel(String text) {
		super(text);
		setForeground(java.awt.Color.black);
	}
	
	// CTor
	public BlackLabel(String text, int minx, int miny) {
		super(text);
		setForeground(java.awt.Color.black);
		min = new Dimension(minx, miny);
	}
	
	// If set, use the minimum size
	public Dimension getMinimumSize() {
		if (min != null) return min;
		else return super.getMinimumSize();
	}
	
	// If set, use the preferred size
	public Dimension getPreferredSize() {
		if (min != null) return min;
		else return super.getPreferredSize();
	}
}
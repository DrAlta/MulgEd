import javax.swing.*;
import java.awt.*;

public class MyScrollPane extends JScrollPane {

	Dimension min = null;

	public MyScrollPane(JComponent view, int hbar, int vbar, int minx, int miny) {
		super(view, hbar, vbar);
		min = new Dimension(minx, miny);		
	}
	
	public MyScrollPane(JComponent view, int hbar, int vbar) {
		super(view, hbar, vbar);
	}
	
	public Dimension getMinimumSize() {
		if (min != null)
			return min;
		else
			return super.getMinimumSize();
	}
	
	public Dimension getPreferredSize() {
		if (min != null)
			return min;
		else
			return super.getPreferredSize();
	}
	
}
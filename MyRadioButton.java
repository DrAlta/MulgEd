import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;

public class MyRadioButton extends JRadioButton implements ActionListener{
	
	MyDialog o;
	int m;
	Dimension min = null;
	
	public MyRadioButton(MyDialog o, int m, String s, boolean b) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyRadioButton(MyDialog o, int m, String s, boolean b, int x, int y) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
		min = new Dimension(x,y);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (o != null)
			o.process(m,0,0,0);
	}
	
	public Dimension getMinimumSize() {
		if (min != null) return min;
		else return super.getMinimumSize();
	}
	
	public Dimension getPreferredSize() {
		if (min != null) return min;
		else return super.getPreferredSize();
	}
}
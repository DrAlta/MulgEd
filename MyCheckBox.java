import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;

public class MyCheckBox extends JCheckBox implements ActionListener{
	
	MyDialog o;
	int m;
	boolean persistant = false;
	boolean selected = false;
	Dimension min = null;
	
	public MyCheckBox(MyDialog o, int m, String s) {
		super(s);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyCheckBox(MyDialog o, int m, String s, boolean b) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyCheckBox(MyDialog o, int m, String s, boolean b, int x, int y) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
		min = new Dimension(x,y);
	}
	
	void setPersistant(boolean p,boolean selected) {
		persistant = p;
		this.selected = selected;
		setSelected(selected);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (persistant)
			setSelected(selected);
		else if (o != null)
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
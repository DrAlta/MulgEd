import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;

public class MyButton extends JButton implements ActionListener{
	
	MyDialog o;
	int msg;
	Dimension min = null;
	
	public MyButton(MyDialog o, int msg, String text) {
		super(text);
		this.o = o;
		this.msg = msg;
		addActionListener(this);
	}
	
	public MyButton(MyDialog o, int msg, String text, int key) {
		super(text);
		this.o = o;
		this.msg = msg;
		setMnemonic(key);
		addActionListener(this);
	}
	
	public MyButton(MyDialog o, int msg, String text, int x, int y) {
		super(text);
		this.o = o;
		this.msg = msg;
		addActionListener(this);
		min = new Dimension(x,y);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (o != null) {
			o.process(msg,0,0,0);
		}
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
import javax.swing.*;
import java.awt.event.*;

public class MyMenuItem extends JMenuItem implements ActionListener{
	
	MyDialog o;
	int m;
	
	public MyMenuItem(MyDialog o, int m, String s) {
		super(s);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyMenuItem(MyDialog o, int m, String s, int key) {
		super(s);
		this.o = o;
		this.m = m;
		setMnemonic(key);
		addActionListener(this);
	}
	
	public MyMenuItem(MyDialog o, int m, String s, KeyStroke ks) {
		super(s);
		this.o = o;
		this.m = m;
		setAccelerator(ks);
		addActionListener(this);
	}
	
	public MyMenuItem(MyDialog o, int m, String s, KeyStroke ks, int key) {
		super(s);
		this.o = o;
		this.m = m;
		setMnemonic(key);
		setAccelerator(ks);
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (o != null)
			o.process(m,0,0,0);
	}
}
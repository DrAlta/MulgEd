import javax.swing.*;
import java.awt.event.*;

public class MyRadioButtonMenuItem extends JRadioButtonMenuItem implements ActionListener{
	
	MyDialog o;
	int m;
	
	public MyRadioButtonMenuItem(MyDialog o, int m, String s, boolean b) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyRadioButtonMenuItem(MyDialog o, int m, String s, boolean b, int key) {
		super(s,b);
		this.o = o;
		this.m = m;
		setMnemonic(key);
		addActionListener(this);
	}
	
	public MyRadioButtonMenuItem(MyDialog o, int m, String s, boolean b, KeyStroke ks) {
		super(s,b);
		this.o = o;
		this.m = m;
		setAccelerator(ks);
		addActionListener(this);
	}
	
	public MyRadioButtonMenuItem(MyDialog o, int m, String s, boolean b, KeyStroke ks, int key) {
		super(s,b);
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
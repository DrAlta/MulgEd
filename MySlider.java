import javax.swing.*;
import javax.swing.event.*;

public class MySlider extends JSlider implements ChangeListener {
	
	MyDialog o;
	int msg;
	
	public MySlider(MyDialog o, int msg, int min, int max, int pos) {
		super(min, max, pos);
		this.o = o;
		this.msg = msg;
		addChangeListener(this);
	}
	
	public void stateChanged(ChangeEvent e) {
		o.process(msg,0,0,0);
	}
	
}
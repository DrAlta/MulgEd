import javax.swing.*;
import java.awt.event.*;

public class MyComboBox extends JComboBox implements ItemListener {
	
	MyDialog o;
	int msg;
	DefaultComboBoxModel model = new DefaultComboBoxModel();
	
	public MyComboBox(MyDialog o, int msg) {
		super();
		setModel(model);
		this.o = o;
		this.msg = msg;
		addItemListener(this);
	}
	
	public void itemStateChanged(ItemEvent e) {
		o.process(msg,0,0,0);
	}
	
}
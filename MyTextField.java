import javax.swing.*;
import java.awt.event.*;

public class MyTextField extends JTextField implements ActionListener, InputMethodListener {
	
	MyDialog o;
	int mEnter, mText;
	
	public MyTextField(MyDialog o, int mEnter, int mText, String s) {
		super(s);
		this.o = o;
		this.mEnter = mEnter;
		this.mText = mText;
		addActionListener(this);
		addInputMethodListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (o != null)
			o.process(mEnter,0,0,0);
	}
	
	public void caretPositionChanged(InputMethodEvent e) {
	}
	
	public void inputMethodTextChanged(InputMethodEvent e) {
		if (o != null)
			o.process(mText,0,0,0);
	}
	
}
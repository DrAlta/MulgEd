import javax.swing.*;
import java.awt.event.*;

public class MyScrollBar extends JScrollBar implements AdjustmentListener {
	MyDialog o;
	int msg;
	
	public MyScrollBar(MyDialog o, int msg, int orientation, int value, int extent, int min, int max) {
		super(orientation, value, extent, min, max);
		this.o = o;
		this.msg = msg;
		addAdjustmentListener(this);
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (o != null)
			o.process(msg,0,0,0);
	}
	
}
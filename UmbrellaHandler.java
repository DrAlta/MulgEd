import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class UmbrellaHandler extends MyDialog {
	
	static final int MSG_OK = 1;
	static final int MSG_CANCEL = 2;
	static final int MSG_SECONDS = 3;
	
	MySlider seconds = new MySlider(this, MSG_SECONDS, 0,60,10);
	
	public UmbrellaHandler(MyDialog parent) {
		super("Parachute time:",true);
		setSize(200,100);
		
		seconds.setMajorTickSpacing(10);
		seconds.setMinorTickSpacing(1);
		seconds.createStandardLabels(10);
		seconds.setSnapToTicks(true);
		seconds.setPaintTicks(true);
		seconds.setPaintLabels(true);
		
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		MyButton mb;
		
		layout.setConstraints(seconds,new GridBagConstraints(0,0,2,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(seconds);
		
		mb = new MyButton(this,MSG_OK,"Ok", KeyEvent.VK_O);
		layout.setConstraints(mb,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(mb);
		
		mb = new MyButton(this,MSG_CANCEL,"Cancel", KeyEvent.VK_C);
		layout.setConstraints(mb,new GridBagConstraints(1,1,1,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(mb);
	}
	
	public void process(int msg, int x, int y, int z) {
		switch (msg) {
			case MSG_OK:
				result = OK;
				hide();
				break;
			case MSG_CANCEL:
				result = CANCEL;
				hide();
				break;
			case MSG_SECONDS:
				break;
		}
	}
}
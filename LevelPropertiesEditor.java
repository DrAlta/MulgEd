import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;

public class LevelPropertiesEditor extends MyDialog {
	
	static final int MSG_OK = 1;
	static final int MSG_CANCEL = 2;
	static final int MSG_ENTER = 3;
	
	MyTextField name = new MyTextField(this,MSG_ENTER,0,"");
	JSlider width = new JSlider(1,4,1);
	JSlider height = new JSlider(1,4,1);
	
	public LevelPropertiesEditor(MyDialog parent) {
		super(parent,"Properties",true);
		setSize(300,180);
		width.setMajorTickSpacing(1);
		width.setMinorTickSpacing(1);
		width.createStandardLabels(1);
		width.setSnapToTicks(true);
		width.setPaintTicks(true);
		width.setPaintLabels(true);
		
		height.setMajorTickSpacing(1);
		height.setMinorTickSpacing(1);
		height.createStandardLabels(1);
		height.setSnapToTicks(true);
		height.setPaintTicks(true);
		height.setPaintLabels(true);
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		MyButton mb;
		l = new BlackLabel("Name:");
		layout.setConstraints(l,new GridBagConstraints(0,0,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(name,new GridBagConstraints(1,0,3,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(name);
		
		l = new BlackLabel("Width in screens:");
		layout.setConstraints(l,new GridBagConstraints(0,1,3,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(width,new GridBagConstraints(3,1,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(width);
		
		l = new BlackLabel("Height in screens:");
		layout.setConstraints(l,new GridBagConstraints(0,2,3,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(height,new GridBagConstraints(3,2,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(height);
		
		JPanel p = new JPanel();
		p.add(new MyButton(this,MSG_OK,"Ok",KeyEvent.VK_O));
		p.add(new MyButton(this,MSG_CANCEL,"Cancel", KeyEvent.VK_C));
		layout.setConstraints(p,new GridBagConstraints(0,4,4,1,0,1,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(p);
	}
	
	public void process(int message,int x, int y, int z) {
		switch (message) {
			case MSG_OK:
				result = OK;
				hide();
				break;
			case MSG_CANCEL:
				result = CANCEL;
				hide();
				break;
			case MSG_ENTER:
				process(MSG_OK,0,0,0);
				break;
		}
	}
	
	
}
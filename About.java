import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;

/**
 *	class About
 *	This class implements the about dialog.
 **/

public class About extends MyDialog {
	
	// Message IDs
	static final int MSG_OK = 1;
	static final int MSG_VISIT =2;
	
	// Constant execution strings, for "Visit"
	static final String GOTO_NO = "http://www.geocities.com/ilan-tayary/contents.htm";
	static final String GOTO_WINDOWS = "start http://www.geocities.com/ilan-tayary/contents.htm";
	static final String GOTO_LINUX = "http://www.geocities.com/ilan-tayary/contents.htm";
	
	// the MulgEd icon.
	MyCanvas icon;
	
	// Ctor
	public About(MyDialog father) {
		super(father, "About MulgEd", true);
		setSize(300,210);
		// Add all dialog components.
		addComponents();
	}
	
	// Main init method
	void addComponents() {
		// We use a grid bag, because it's the most flexible.
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		// Add the icon
		icon = new MyCanvas(this, 32,32);
		Image i = new ImageIcon("MulgEdIcon.gif").getImage();
		icon.getGraphics().drawImage(i,0,0,icon.getBackground(),this);
		layout.setConstraints(icon,new GridBagConstraints(0,0,1,3,0,0,c.CENTER,c.NONE,new Insets(1,10,1,10),0,0));
		paneadd(icon);
		// Add the labels.
		l = new BlackLabel("MulgEd V2.1");
		layout.setConstraints(l,new GridBagConstraints(1,0,1,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("By Ilan Tayary, April 1999 - November 2000");
		layout.setConstraints(l,new GridBagConstraints(1,1,1,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("Email: ilan-tayary@yahoo.com");
		layout.setConstraints(l,new GridBagConstraints(1,2,1,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("Visit the MulgEd homepage at:");
		layout.setConstraints(l,new GridBagConstraints(0,3,2,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("http://www.geocities.com/ilan-tayary/contents.htm");
		layout.setConstraints(l,new GridBagConstraints(0,4,2,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("for updates and other new stuff.");
		layout.setConstraints(l,new GridBagConstraints(0,5,2,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("In the memory of Itzhak Rabin. Israeli Prime Minister who was");
		layout.setConstraints(l,new GridBagConstraints(0,6,2,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		l = new BlackLabel("murdered on November 4th, 1995 by an enemy of peace.");
		layout.setConstraints(l,new GridBagConstraints(0,7,2,1,1,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		// the buttons have their own "FlowLayout"ed panel
		JPanel p = new JPanel();
		p.add(new MyButton(this,MSG_OK,"Ok", KeyEvent.VK_O));
		p.add(new MyButton(this,MSG_VISIT,"Visit MulgEd Homepage", KeyEvent.VK_V));
		layout.setConstraints(p,new GridBagConstraints(0,8,2,1,0,1,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(p);
	}
	
	
	// Message handler.
	public void process(int message, int x, int y, int z) {
		switch (message) {
			case MSG_OK:
				hide();
				break;
			case MSG_VISIT:
				// try to figure out the operating system, and run the browser.
				try {
					String ss = (String)System.getProperties().get("os.name");
					if (ss == null)
						Runtime.getRuntime().exec(GOTO_NO);
					else if (ss.startsWith("Windows"))
						Runtime.getRuntime().exec(GOTO_WINDOWS);
					else if (ss.startsWith("Linux"))
						Runtime.getRuntime().exec(GOTO_LINUX);
					else
						Runtime.getRuntime().exec(GOTO_NO);
				} catch (IOException ioe) {}
				break;
		}
	}
	
	// pressnig the X is the same as pressing "Ok"
	public void windowClosing(WindowEvent e) {
		process(MSG_OK,0,0,0);
	}
	
}
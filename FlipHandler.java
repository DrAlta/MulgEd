/*
This file is part of MulgEd - The Mulg game and level editor.
Copyright (C) 1999-2003 Ilan Tayary, a.k.a. [NoCt]Yonatanz
Website: http://mulged.sourceforge.net
Contact: yonatanz at actcom.co.il (I do not like SPAM)

MulgEd is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

MulgEd is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MulgEd, in a file named COPYING; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *	class FlipHandler
 *	This class implements the Flip connection dialog.
 **/

public class FlipHandler extends MyDialog {
	
	// Messages
	static final int MSG_XCH = 1;
	static final int MSG_YCH = 2;
	static final int MSG_OK = 3;
	static final int MSG_CANCEL = 4;
	
	// Controls
	MySlider Achannel = new MySlider(this,MSG_XCH,0,15,0);
	MySlider Bchannel = new MySlider(this,MSG_YCH,0,15,0);
	
	// CTor
	public FlipHandler(MyDialog parent) {
		super("Connect flip tile",true);
		setSize(210,160);
		
		// set the Sliders parameters
		Achannel.setMajorTickSpacing(3);
		Achannel.setMinorTickSpacing(1);
		Achannel.createStandardLabels(3);
		Achannel.setSnapToTicks(true);
		Achannel.setPaintTicks(true);
		Achannel.setPaintLabels(true);
		
		Bchannel.setMajorTickSpacing(3);
		Bchannel.setMinorTickSpacing(1);
		Bchannel.createStandardLabels(3);
		Bchannel.setSnapToTicks(true);
		Bchannel.setPaintTicks(true);
		Bchannel.setPaintLabels(true);
		
		// Add controls to the dialog
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		MyButton mb;
		JPanel p;
		
		// Add the sliders with their labels
		l = new BlackLabel("X channel:");
		layout.setConstraints(l,new GridBagConstraints(0,0,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(Achannel,new GridBagConstraints(1,0,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(Achannel);
		
		l = new BlackLabel("Y channel:");
		layout.setConstraints(l,new GridBagConstraints(0,1,1,1,0,0,c.WEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(Bchannel,new GridBagConstraints(1,1,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(Bchannel);
		
		// the buttons have their own panel
		p = new JPanel();
		p.add(new MyButton(this,MSG_OK,"Ok", KeyEvent.VK_O));
		p.add(new MyButton(this,MSG_OK,"Cancel", KeyEvent.VK_C));
		
		layout.setConstraints(p,new GridBagConstraints(0,2,2,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(p);
		
	}
	
	// Message handler
	public void process(int msg, int x,int y,int z) {
		switch (msg) {
			case MSG_XCH:
				break;
			case MSG_YCH:
				break;
			case MSG_OK:
				result = OK;
				hide();
				break;
			case MSG_CANCEL:
				result = CANCEL;
				hide();
				break;
		}
	}
}
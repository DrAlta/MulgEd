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
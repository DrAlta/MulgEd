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
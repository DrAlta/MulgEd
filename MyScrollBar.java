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
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
import java.awt.Dimension;

public class MyCheckBox extends JCheckBox implements ActionListener{
	
	MyDialog o;
	int m;
	boolean persistant = false;
	boolean selected = false;
	Dimension min = null;
	
	public MyCheckBox(MyDialog o, int m, String s) {
		super(s);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyCheckBox(MyDialog o, int m, String s, boolean b) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
	}
	
	public MyCheckBox(MyDialog o, int m, String s, boolean b, int x, int y) {
		super(s,b);
		this.o = o;
		this.m = m;
		addActionListener(this);
		min = new Dimension(x,y);
	}
	
	void setPersistant(boolean p,boolean selected) {
		persistant = p;
		this.selected = selected;
		setSelected(selected);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (persistant)
			setSelected(selected);
		else if (o != null)
			o.process(m,0,0,0);
	}
	
	public Dimension getMinimumSize() {
		if (min != null) return min;
		else return super.getMinimumSize();
	}
	
	public Dimension getPreferredSize() {
		if (min != null) return min;
		else return super.getPreferredSize();
	}
}
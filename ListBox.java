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
import java.awt.Dimension;
import java.awt.event.*;

public class ListBox extends JList implements ListSelectionListener, KeyListener, MouseListener, MouseMotionListener {
	
	DefaultListModel model;
	Dimension d = null;
	MyDialog o;
	int selectmsg, dbclickmsg, entermsg, insertmsg, deletemsg;
	int mouseupmsg, mousedownmsg, mousemovemsg, mouseleavemsg;
	
	public ListBox(MyDialog o, int selectmsg, int dbclickmsg, int entermsg, int insertmsg, int deletemsg, int mouseupmsg, int mousedownmsg, int mousemovemsg, int mouseleavemsg, int sx, int sy) {
		super(new DefaultListModel());
		this.o = o;
		this.selectmsg = selectmsg;
		this.dbclickmsg = dbclickmsg;
		this.entermsg = entermsg;
		this.insertmsg = insertmsg;
		this.deletemsg = deletemsg;
		this.mouseupmsg = mouseupmsg;
		this.mousedownmsg = mousedownmsg;
		this.mousemovemsg = mousemovemsg;
		this.mouseleavemsg = mouseleavemsg;
		addListSelectionListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		model = (DefaultListModel)getModel();
		d = new Dimension(sx, sy);
	}
	
	public ListBox(MyDialog o, int selectmsg, int dbclickmsg, int entermsg, int insertmsg, int deletemsg, int mouseupmsg, int mousedownmsg, int mousemovemsg, int mouseleavemsg) {
		super(new DefaultListModel());
		this.o = o;
		this.selectmsg = selectmsg;
		this.dbclickmsg = dbclickmsg;
		this.entermsg = entermsg;
		this.insertmsg = insertmsg;
		this.deletemsg = deletemsg;
		this.mouseupmsg = mouseupmsg;
		this.mousedownmsg = mousedownmsg;
		this.mousemovemsg = mousemovemsg;
		this.mouseleavemsg = mouseleavemsg;
		addListSelectionListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		model = (DefaultListModel)getModel();
	}
	
	public Dimension getPreferredSize() {
		if (d != null) 
			return d;
		else 
			return super.getPreferredSize();
	}
	
	public Dimension getMinimumSize() {
		if (d != null) 
			return d;
		else 
			return super.getMinimumSize();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		o.process(selectmsg,0,0,0);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1) {
			o.process(dbclickmsg,0,0,0);
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
		o.process(mouseleavemsg,0,0,0);
	}
	public void mousePressed(MouseEvent e) {
		if ( (e.getClickCount() == 1) && ( (e.getModifiers() & e.BUTTON1_MASK)!=0) )
			o.process(mousedownmsg,0,0,0);
	}
	public void mouseReleased(MouseEvent e) {
		o.process(mouseupmsg,0,0,0);
	}
	
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				o.process(entermsg,0,0,0);
				break;
			case KeyEvent.VK_INSERT:
				o.process(insertmsg,0,0,0);
				break;
			case KeyEvent.VK_DELETE:
				o.process(deletemsg,0,0,0);
				break;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		o.process(mousemovemsg,e.getX(),e.getY(),0);
	}
	public void mouseMoved(MouseEvent e) {
		o.process(mousemovemsg,e.getX(),e.getY(),0);
	}
	
}
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Color;

public class MyCanvas extends JComponent implements MouseListener, MouseMotionListener {
	
	Dimension size = null;
	BufferedImage content;
	MyDialog o = null;
	Graphics g;
	int clickMsg, pressMsg, releaseMsg, moveMsg;
	
	public MyCanvas(MyDialog o, int clickMsg, int pressMsg, int releaseMsg, int moveMsg, int x, int y) {
		this.o = o;
		this.clickMsg = clickMsg;
		this.pressMsg = pressMsg;
		this.releaseMsg = releaseMsg;
		this.moveMsg = moveMsg;
		size = new Dimension(x, y);
		content = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		g = content.createGraphics();
		g.setColor(o.getContentPane().getBackground());
		g.fillRect(0,0,x,y);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public MyCanvas(MyDialog o, int x, int y) {
		size = new Dimension(x, y);
		content = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		g = content.getGraphics();
		g.setColor(o.getContentPane().getBackground());
		g.fillRect(0,0,x,y);
	}
	
	public Graphics getGraphics() {
		return g;
	}
	
	public Image getImage() {
		return content;
	}
	
	public void paint(Graphics g) {
		g.drawImage(content,0,0,this);
	}
	
	void refresh() {
		repaint();
//		paintImmediately(getBounds(null));
	}
	
	public boolean imageUpdate(Image i, int inf, int x, int y, int w, int h) {
		return false;
	}
	
	public Dimension getPreferredSize() {
		return size;
	}
	
	public Dimension getMinimumSize() {
		return size;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (o != null)
			o.process(clickMsg, e.getX(),e.getY(),e.getModifiers());
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		if (o != null)
			o.process(pressMsg, e.getX(),e.getY(),e.getModifiers());
	}
	public void mouseReleased(MouseEvent e) {
		if (o != null)
			o.process(releaseMsg, e.getX(),e.getY(),e.getModifiers());
	}
	
	public void mouseMoved(MouseEvent e) {
		if (o != null)
			o.process(moveMsg, e.getX(),e.getY(),e.getModifiers());
	}
	public void mouseDragged(MouseEvent e) {
		if (o != null)
			o.process(moveMsg, e.getX(),e.getY(),e.getModifiers());
	}

	
}
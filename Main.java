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
import java.lang.Thread;

public class Main {
	static GameEditor g;
	static java.awt.Button button = new java.awt.Button();
		
	public static void main(String[] args) {
	    LoadingDlg dlg = new LoadingDlg();
	    dlg.show();
	    try {
	        java.lang.Thread.sleep(100);
	    } catch (InterruptedException e)
	    {}
		g = new GameEditor();
		g.setFonts(g);
		g.setMenuFonts();
		g.setLocation();
		dlg.hide();
		g.show();
	}
}
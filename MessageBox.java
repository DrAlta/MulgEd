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

import java.awt.*;
import javax.swing.*;

public class MessageBox {
	
	public static void showErrorMessage(MyDialog parent, String text, String title) {
		JOptionPane.showMessageDialog(parent,text,title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showYesNoMessage(MyDialog parent, String text, String title) {
		return JOptionPane.showConfirmDialog(parent,text,title, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
	public static int showYesNoCancelMessage(MyDialog parent, String text, String title) {
		return JOptionPane.showConfirmDialog(parent,text,title, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
}
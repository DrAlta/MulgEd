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

public class NoteHandler extends MyDialog {
	
	static final int MSG_OK = 1;
	static final int MSG_CANCEL = 2;
	static final int MSG_NOTE = 3;
	static final int MSG_NEWNOTE = 4;
	
	MyComboBox noteChoice = new MyComboBox(this,MSG_NOTE);
	MyButton newButton = new MyButton(this,MSG_NEWNOTE,"New note",KeyEvent.VK_N);
	GameEditor ged;
	
	public NoteHandler(MyDialog parent, GameEditor ged) {
		super("Please choose a note:",true);
		this.ged = ged;
		setSize(250,90);
		noteChoice.addItem("sdf");
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		MyButton mb;
		
		layout.setConstraints(noteChoice,new GridBagConstraints(0,0,3,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(noteChoice);
		mb = new MyButton(this,MSG_OK,"Ok",KeyEvent.VK_O);
		layout.setConstraints(mb,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(mb);
		mb = new MyButton(this,MSG_CANCEL,"Cancel",KeyEvent.VK_C);
		layout.setConstraints(mb,new GridBagConstraints(1,1,1,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(mb);
		layout.setConstraints(newButton,new GridBagConstraints(2,1,1,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(newButton);
	}
	
	public int showDlgNewNote() {
		return showDlg();
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
			case MSG_NEWNOTE:
				String newText = (String)JOptionPane.showInputDialog(this,"Please enter new text for the note","New Note",JOptionPane.PLAIN_MESSAGE);
				if ((newText != null) && (newText.length() > 0)) {
					ged.game.notes[ged.game.noteCount] = newText;
					ged.noteList.model.addElement(newText);
					noteChoice.addItem(newText);
					noteChoice.setSelectedIndex(ged.game.noteCount);
					ged.game.noteCount++;
				}
				break;
			case MSG_NOTE:
				break;
		}
	}
}
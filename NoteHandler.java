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
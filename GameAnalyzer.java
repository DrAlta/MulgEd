import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;

/**
 *	class GameAnalyzer
 *	This class implements the game analyzer dialog.
 **/

public class GameAnalyzer extends MyDialog {
	
	// Messages
	static final int MSG_CLOSE = 1;
	
	// Controls
	BlackLabel name = new BlackLabel("");
	BlackLabel author = new BlackLabel("");
	BlackLabel levels = new BlackLabel("");
	BlackLabel notes = new BlackLabel("");
	BlackLabel compat = new BlackLabel("");
	
	// Members
	String minVersion;
	
	// CTor
	public GameAnalyzer(MyDialog parent) {
		super(parent,"Game Analysis",true);
		setSize(300,140);
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		
		l = new BlackLabel("Game Name:");
		layout.setConstraints(l,new GridBagConstraints(0,0,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(name,new GridBagConstraints(1,0,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(name);
		
		l = new BlackLabel("Author Name:");
		layout.setConstraints(l,new GridBagConstraints(0,1,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(author,new GridBagConstraints(1,1,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(author);
		
		l = new BlackLabel("Number of levels:");
		layout.setConstraints(l,new GridBagConstraints(0,2,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(levels,new GridBagConstraints(1,2,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(levels);
		
		l = new BlackLabel("Number of notes:");
		layout.setConstraints(l,new GridBagConstraints(0,3,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(notes,new GridBagConstraints(1,3,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(notes);
		
		layout.setConstraints(compat,new GridBagConstraints(0,4,2,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(compat);
		
		MyButton close = new MyButton(this,MSG_CLOSE,"Close", KeyEvent.VK_C);
		layout.setConstraints(close,new GridBagConstraints(0,5,2,1,1,1,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(close);
	}
	
	// Analyzes a game and updates the controls and members
	void analyze(Game g) {
		name.setText(g.name);
		author.setText(g.author);
		levels.setText(String.valueOf(g.levelCount));
		notes.setText(String.valueOf(g.noteCount));
		// limit the MulgII version to a version or higher.
		minVersion="2a";
		if (g.levelCount > 16) 
			limitVersion("2g");
		for (int lev = 0; lev < g.levelCount; lev++) {
			Level level = g.levels[lev];
			for (int y = 0; y<level.height; y++) {
				for (int x = 0; x<level.width; x++) {
					Cell cell = level.cells[x][y];
					switch (cell.tile) {
					case 0x71:	case 0x72:	case 0x73:	case 0x74:
					case 0x75:	case 0x76:	case 0x77:	case 0x78:
					case 0x79:	case 0x7a:	case 0x7b:
						limitVersion("2f");
						break;
					case 0x7c:	case 0x7d:	case 0x7e:	case 0x7f:
					case 0x80:	case 0x81:	case 0x82:	case 0x83:
					case 0x84:
					case Cell.C_MemorizeBox0:	case Cell.C_MemorizeBox1:
					case Cell.C_MemorizeBox2:	case Cell.C_MemorizeBox3:
						limitVersion("2h");
						break;
					case 0x85: case 0x86: case 0x87: case 0x88: case 0x89: case 0x8a: case 0x8b:
					case 0x8c: case 0x8d: case 0x8e: case 0x8f: 
					case 0x90: case 0x91: case 0x92:
						limitVersion("2n");
						break;
					}
				}
			}
		}
		compat.setText("This game is compatible with Mulg version "+xlateVersion(minVersion)+" and later only.");		
	}
	
	// translate the version string to a readable one.
	public String xlateVersion(String s) {
		String ver="";
		switch (s.charAt(0)) {
		case '2':
			ver+="II";
			break;
		}
		ver+=s.substring(1);
		return ver;
	}

	// limit the minimum version to a specific one
	public void limitVersion(String s) {
		char maj = s.charAt(0);
		char min = s.charAt(1);
		if (maj > minVersion.charAt(0)) {
			minVersion = s;
			return;
		} else if (maj == minVersion.charAt(0)) {
			if (min > minVersion.charAt(1)) {
				minVersion = s;
				return;
			}
		}
	}
	
	// default close = like pressing "Close"
	public void windowClosing(WindowEvent e) {
		process(MSG_CLOSE,0,0,0);
	}
	
	// Message handler
	public void process(int message, int x, int y, int z) {
		switch(message) {
			case MSG_CLOSE:
				hide();
				break;
		}
	}
}
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LevelAnalyzer extends MyDialog {
	
		public class Problem extends Object
		{
			String text;
			String explanation1;
			String explanation2;
			String explanation3;
			
			public Problem() {
			}
			
			public Problem(String text)	{
				this.text = text;
			}
			
			public Problem(String text, String explanation1, String explanation2, String explanation3)	{
				this.text = text;
				this.explanation1 = explanation1;
				this.explanation2 = explanation2;
				this.explanation3 = explanation3;
			}
			
			public String toString() {
				return text;
			}
		
		}
		
	static final int MSG_PROBLEM = 1;
	static final int MSG_CLOSE = 2;
	static final int MSG_CHANNEL = 3;
	static final int MSG_XCHANNEL = 4;
	static final int MSG_YCHANNEL = 5;
	
	static final int MAX_PROBLEMS = 50;
	
	BlackLabel name = new BlackLabel("");
	BlackLabel width = new BlackLabel("");
	BlackLabel height = new BlackLabel("");
	MyCheckBox topbottom = new MyCheckBox(this,0,"Wrap around top-bottom");
	MyCheckBox leftright = new MyCheckBox(this,0,"Wrap around left-right");
	ListBox problems = new ListBox(this,MSG_PROBLEM, MSG_PROBLEM, MSG_PROBLEM,0,0,0,0,0,0);
	MyScrollPane probScroll;
	MyComboBox channel = new MyComboBox(this,MSG_CHANNEL);
	BlackLabel lch = new BlackLabel("");
	MyComboBox xchannel = new MyComboBox(this,MSG_XCHANNEL);
	BlackLabel lxch = new BlackLabel("");
	MyComboBox ychannel = new MyComboBox(this,MSG_YCHANNEL);
	BlackLabel lych = new BlackLabel("");
	BlackLabel explanation1 = new BlackLabel("");
	BlackLabel explanation2 = new BlackLabel("");
	BlackLabel explanation3 = new BlackLabel("");
	JPanel probPanel = new JPanel();
	Level level;
	Game game;
	
	int[] xch = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int[] ych = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int[] ch = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};	
	
	public LevelAnalyzer(MyDialog parent) {
		super(parent,"Level Analysis",true);
		setSize(540,270);
		addComponents();
	}
	
	public void windowClosing(WindowEvent e) {
		process(MSG_CLOSE,0,0,0);
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		BlackLabel l;
		l = new BlackLabel("Name:");
		layout.setConstraints(l,new GridBagConstraints(0,0,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(name,new GridBagConstraints(1,0,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(name);
		
		l = new BlackLabel("Width:");
		layout.setConstraints(l,new GridBagConstraints(0,1,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(width,new GridBagConstraints(1,1,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(width);
		
		l = new BlackLabel("Height:");
		layout.setConstraints(l,new GridBagConstraints(0,2,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(l);
		layout.setConstraints(height,new GridBagConstraints(1,2,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(height);
		
		topbottom.setPersistant(true,false);
		layout.setConstraints(topbottom,new GridBagConstraints(0,3,2,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(topbottom);
		
		leftright.setPersistant(true,false);
		layout.setConstraints(leftright,new GridBagConstraints(0,4,2,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(leftright);
				
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnl = new JPanel(gbl);
		
		l = new BlackLabel("No. of tiles that use each channel:");
		gbl.setConstraints(l,new GridBagConstraints(0,0,2,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(l);
		
		for (int i=1; i<32; i++) {
			channel.model.addElement(String.valueOf(i));
		}
		gbl.setConstraints(channel,new GridBagConstraints(0,1,1,2,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(channel);
		gbl.setConstraints(lch,new GridBagConstraints(1,1,1,2,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(lch);
		
		for (int i=0; i<16; i++) {
			xchannel.model.addElement(String.valueOf(i));
		}
		gbl.setConstraints(xchannel,new GridBagConstraints(0,3,1,2,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(xchannel);
		gbl.setConstraints(lxch,new GridBagConstraints(1,3,1,2,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(lxch);
		
		for (int i=0; i<16; i++) {
			ychannel.model.addElement(String.valueOf(i));
		}
		gbl.setConstraints(ychannel,new GridBagConstraints(0,5,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(ychannel);
		gbl.setConstraints(lych,new GridBagConstraints(1,5,1,1,0,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		pnl.add(lych);
		
		JPanel p = new JPanel();
		gbl.setConstraints(p,new GridBagConstraints(0,6,2,1,1,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(p);
		
		layout.setConstraints(pnl,new GridBagConstraints(2,0,1,5,1,0,c.NORTH,c.BOTH,new Insets(1,6,1,1),0,0));
		paneadd(pnl);
		
		gbl = new GridBagLayout();
		probPanel.setLayout(gbl);
		
		l = new BlackLabel("Problems (click for explanation):");
		gbl.setConstraints(l,new GridBagConstraints(0,0,2,1,1,0,c.WEST, c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		probPanel.add(l);
		
		probScroll = new MyScrollPane(problems, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER, 255,10);
		gbl.setConstraints(probScroll,new GridBagConstraints(0,1,1,3,0,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		probPanel.add(probScroll);
		
		gbl.setConstraints(explanation1,new GridBagConstraints(1,1,1,1,1,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		probPanel.add(explanation1);
		gbl.setConstraints(explanation2,new GridBagConstraints(1,2,1,1,1,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		probPanel.add(explanation2);
		gbl.setConstraints(explanation3,new GridBagConstraints(1,3,1,1,1,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		probPanel.add(explanation3);
		
		layout.setConstraints(probPanel,new GridBagConstraints(0,5,3,1,1,1,c.NORTH,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(probPanel);
		
		MyButton close = new MyButton(this,MSG_CLOSE,"Close", KeyEvent.VK_C);
		layout.setConstraints(close,new GridBagConstraints(0,6,3,1,1,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(close);
		
	}
	
	public void process(int message, int x, int y, int z) {
		int chnl;
		switch (message) {
			case MSG_CLOSE:
				hide();
				break;
			case MSG_PROBLEM:
				Problem p = (Problem)problems.getSelectedValue();
				if (p != null) {
					setProblem(p);
				}
				break;
			case MSG_CHANNEL:
				chnl = Integer.valueOf((String)channel.getSelectedItem()).intValue();
				lch.setText(ch[chnl]+" tiles share the "+nth(chnl)+" channel");
				break;
			case MSG_XCHANNEL:
				chnl = Integer.valueOf((String)xchannel.getSelectedItem()).intValue();
				lxch.setText(xch[chnl]+" flips share the "+nth(chnl)+" X channel");
				break;
			case MSG_YCHANNEL:
				chnl = Integer.valueOf((String)ychannel.getSelectedItem()).intValue();
				lych.setText(ych[chnl]+" flips share the "+nth(chnl)+" Y channel");
				break;
		}
	}
	
	public boolean isBlocking(int tile) {
		switch (tile) {
		case 0x04: case 0x07: case 0x0b: case 0x0f: case 0x13: case 0x14:
		case 0x15: case 0x16: case 0x17: case 0x1b: case 0x1c: case 0x23:
		case 0x26: case 0x27: case 0x2b: case 0x2c: case 0x2d: case 0x2e:
		case 0x2f: case 0x30: case 0x33: case 0x58: case 0x59: case 0x5f:
		case 0x62: case 0x64: case 0x6f: case 0x70: case 0x55: case 0x56:
		case 0x71: case 0x72: case 0x78: case 0x79: case 0x7a: case 0x7b:
		case 0x35: case 0x36: case 0x37: case 0x38: case 0x39: case 0x3a:
		case 0x3b: case 0x3c: case 0x3d: case 0x3e: case 0x3f: case 0x40:
		case 0x41: case 0x42: case 0x43: case 0x44: case 0x45: case 0x46:
		case 0x47: case 0x48: case 0x49: case 0x4a: case 0x4b: case 0x4c:
		case 0x4d: case 0x4e: case 0x4f: case 0x50: case 0x51: case 0x52:
		case 0x53: case 0x54: case Cell.C_ActiveStorm: case Cell.C_BfReverser:
		case Cell.C_BfUnReverser: case Cell.C_DescendingFloor3: case Cell.C_IceReverser:
		case Cell.C_IceUnReverser: case Cell.C_OpenHGate: case Cell.C_OpenVGate:
		case Cell.C_Reverser: case Cell.C_StartingPoint: case Cell.C_UnReverser:
			return false;
		}
		return true;
	}
	
	String nth(int x) {
		String s = String.valueOf(x);
		if (((x % 100)>=11) && ((x % 100) <=13)) return s+"th";
		switch (x % 10) {
		case 1: return s+"st";
		case 2: return s+"nd";
		case 3: return s+"rd";
		default:return s+"th";
		}
	}
	
	void setProblem(Problem p) {
		explanation1.setText(p.explanation1);
		explanation2.setText(p.explanation2);
		explanation3.setText(p.explanation3);
		probPanel.doLayout();
	}
	
	public void analyze(Game game, Level level) {
		problems.model.removeAllElements();
		if (level.name.length() < 1) {
			name.setText("n/a");
			problems.model.addElement(new Problem("Level was given an empty name",
										 												"Please specify a name for the level in the",
										 												"properties dialog (from game editor or level editor).",
										 												""));
		} else 
			name.setText(level.name);
		if (((level.width-1) % 9) != 0) {
			width.setText("bad value");
			problems.model.addElement(new Problem("The width of the level is not in full screens.",
										 												"The level's width does not compose full screens.",
										 												"It might have been created using a problematic editor.",
										 												"Please re-specify the width in the level properties dialog."));
		} else
			width.setText(String.valueOf((level.width-1)/9)+" Screens.");
		if (((level.height-1) % 8) != 0) {
			height.setText("bad value");
			problems.model.addElement(new Problem("The height of the level is not in full screens.",
										 												"The level's height does not compose full screens.",
										 												"It might have been created using a problematic editor.",
										 												"Please re-specify the height in the level properties dialog."));
		} else
			height.setText(String.valueOf((level.height-1)/8)+" Screens.");
		//wrap-around test top-bottom
		boolean ok = true;
		String which;
		for (int x = 0; x<level.width; x++)
			if (level.cells[x][0].tile != level.cells[x][level.height-1].tile)
				if (!isBlocking(level.cells[x][0].tile) || !isBlocking(level.cells[x][level.height-1].tile)) {
					ok = false;
					if (!isBlocking(level.cells[x][0].tile))
						which = "topmost";
					else 
						which = "bottom";
					problems.model.addElement(new Problem("Two opposite tiles do not allow wrap-around (V).",
																								"The "+nth(x+1)+" tile from the left on the "+which+" row is",
																								"non-blocking, while the tile on the opposite side is not the same.",
																								"Change either one to fit wrap-around limitations."));
			}
		topbottom.setPersistant(true, ok);
		//wrap-around test left-right
		ok = true;
		for (int y = 0; y<level.height; y++)
			if (level.cells[0][y].tile != level.cells[level.width-1][y].tile)
				if (!isBlocking(level.cells[0][y].tile) || !isBlocking(level.cells[level.width-1][y].tile)) {
					ok = false;
					if (!isBlocking(level.cells[0][y].tile))
						which = "leftmost";
					else
						which = "rightmost";
					problems.model.addElement(new Problem("Two opposite tiles do not allow wrap-around (H).",
																								"The "+nth(y+1)+" tile from the top on the "+which+" column is",
																								"non-blocking, while the tile on the opposite side is not the same.",
																								"Change either one to fit wrap-around limitations."));
			}
		leftright.setPersistant(true, ok);
		// init channel analysis
		for (int i=0; i<16; i++) {
			xch[i] = 0;
			ych[i] = 0;
		}
		for (int i=0; i<32; i++) ch[i] = 0;
		// init memo analysis
		int[][] memochannels = new int[4][2048];
		int[] maxmemo = {0,0,0,0};
		int startingPoints = 0;
		int targetCrosses = 0;
		// other global problems
		for (int y = 0; y<level.height; y++)
			for (int x = 0; x<level.width; x++) {
				int tile = level.cells[x][y].tile;
				int data = level.cells[x][y].data;
				// fill in the memo analysis
				if (tile == Cell.C_MemorizeBox0) {
					memochannels[0][maxmemo[0]] = data;
					maxmemo[0]++;
				} else if (tile == Cell.C_MemorizeBox1) {
					memochannels[1][maxmemo[1]] = data;
					maxmemo[1]++;
				} else if (tile == Cell.C_MemorizeBox2) {
					memochannels[2][maxmemo[2]] = data;
					maxmemo[2]++;
				} else if (tile == Cell.C_MemorizeBox3) {
					memochannels[3][maxmemo[3]] = data;
					maxmemo[3]++;
				}
				boolean found;
				// below integrity
				if (level.cells[x][y].isBelow()) {
					found = false;
					for (int i = 0; i<BelowHandler.set.length; i++) 
						for (int j = 0; j<BelowHandler.set[i].length; j++) 
							if (data == BelowHandler.set[i][j]) found = true;
					if (!found) 
						problems.model.addElement(new Problem("An invalid tile lies below another tile.",
																									"The "+nth(x+1)+" tile from the left in the "+nth(y+1)+" row from the top",
																									"is a "+Cell.getNameOfTile(tile)+" and has an invalid tile below it.",
																									"Click on it while working on connections in the level editor to choose the tile below it."));
				}
				// unsupported tiles check
				if (Cell.getNameOfTile(tile).equals("")) 
					problems.model.addElement(new Problem("There is an unsupported tile in the level",
																								"The "+nth(x+1)+" tile from the left in the "+nth(y+1),
																								"row from the top is not supported.",
																								"Draw a different tile from the tileset in the level editor"));
				switch (tile) {
				case 0x07: // note integrity
					if ((data <= 0) || (data > game.noteCount))
						problems.model.addElement(new Problem("A note is connected to a missing text",
																									"The note in the "+nth(x+1)+" tile from the left in the "+nth(y+1),
																									"row from the top is connected to a text that does not exist.",
																									"Click on it while working on connections to connect correctly"));
					break;
				case 0x5f: // umbrella integrity
					if ((data <= 0) || (data > 127))
						problems.model.addElement(new Problem("A parachute's time is incorrect",
																									"The parachute in the "+nth(x+1)+" tile from the left in the "+nth(y+1),
																									"row from the top has an incorrect time.",
																									"Click on it while working on connections to correct the time."));
					break;
				case 0x5a:
				case 0x5b:
					xch[data & 0x0f]++;
					ych[(data >> 4) & 0x0f]++;
					break;
				case Cell.C_StartingPoint:
					startingPoints++;
					break;
				case 0x05:
					targetCrosses++;
					break;
				}
				if (level.cells[x][y].isConnectable()) 
					if (data != 0)
						ch[data & 0x1f]++;
			}
		boolean memok = true;
		for (int i=0; i<4; i++) {
			int last = memochannels[i][0];
			for (int j=0; j<maxmemo[i]; j++) {
				if (memochannels[i][j] != last)
					memok = false;
			}
		}
		if (!memok) 
			problems.model.addElement(new Problem("Memorize boxes are not connected right",
																						"Some memorize boxes of the same type (same figure) are",
																						"connected to different channels. Make sure that all memorize",
																						"boxes of the same type are connected to the same channel."));
		if (targetCrosses < 1)
			problems.model.addElement(new Problem("There is no target cross",
																						"The level is unsolvable because there is no target cross.",
																						"Add a target cross in the right place.",
																						""));
		if (startingPoints < 1)
			problems.model.addElement(new Problem("There is no starting point",
																						"This level is illegal because it contains no starting point.",
																						"Add a starting point in the right place.",
																						""));
		else if (startingPoints > 1)
			problems.model.addElement(new Problem("There are too many starting points.",
																						"This level is illegal because it contains "+startingPoints+" starting points.",
																						"Remove the unnecessary starting points and make sure",
																						"there is only one."));
																							
		channel.setSelectedIndex(0);
		process(MSG_CHANNEL,0,0,0);
		xchannel.setSelectedIndex(0);
		process(MSG_XCHANNEL,0,0,0);
		ychannel.setSelectedIndex(0);
		process(MSG_YCHANNEL,0,0,0);		
	}
	
	
}
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
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;
import java.util.jar.*;
import java.io.*;

//TODO: Issues:
//	Progress-bar updating.

public class LevelEditor extends MyDialog {
	
	static final int MSG_NULL = 0;
	static final int MSG_PICCLICK = 1;
	static final int MSG_PICPRESS = 2;
	static final int MSG_PICRELEASE = 3;
	static final int MSG_PICMOVE = 4;
	static final int MSG_HBAR = 5;
	static final int MSG_VBAR = 6;
	static final int MSG_STANDARD = 7;
	static final int MSG_ADDITIONAL = 8;
	static final int MSG_GROOVE = 9;
	static final int MSG_TILESET = 10;
	static final int MSG_SIZE1 = 11;
	static final int MSG_SIZE2 = 12;
	static final int MSG_SIZE3 = 13;
	static final int MSG_MARKS = 14;
	static final int MSG_TILES = 15;
	static final int MSG_CONNECTIONS = 16;
	static final int MSG_UNDO = 17;
	static final int MSG_REDO = 18;
	static final int MSG_PROPERTIES = 19;
	static final int MSG_ANALYZE = 20;
	static final int MSG_HELP = 21;
	static final int MSG_SAVE = 22;
	static final int MSG_CANCEL = 23;
	static final int MSG_TILEHELP = 24;
	static final int MSG_TIMERSCROLL = 25;
	
	static final int MAX_TILES = 147;
	static final int MAX_STILES = 15;
	static final int boardWidth = 480;
	static final int boardHeight = 432;
	static final int[][] standard = {
		{0x04,0x06,0x03,Cell.C_StartingPoint,0x05,0x77,0x2b,0x70},
		{Cell.C_Reverser, Cell.C_UnReverser,
		 Cell.C_IceReverser, Cell.C_IceUnReverser, Cell.C_BfReverser, Cell.C_BfUnReverser,
		 0x5f,0x6f},
		{Cell.C_DescendingFloor3,0x55,0x56,0x57,0x2c,0x2d,0x2e,0x2f},
		{0x0b,Cell.C_OpenVGate,0x0f,Cell.C_OpenHGate,0x13,0x14,0x15,0x16},
		{0x26,0x71,0x72,0x27,0x17,
		 Cell.C_ActiveStorm, 0x1b,0x1c}};
	static final int[][] additional = { 
		{0x04,0x06,0x03,0x85,0x86,0x87,0x91,0x92},
		{0x07,0x23,0x30,0x33,0x5f,0x62,0x64,0x78},
		{Cell.C_MemorizeBox0, Cell.C_MemorizeBox1, Cell.C_MemorizeBox2, Cell.C_MemorizeBox3,
		 0x1d,0x1e,0x1f,Cell.C_Reverser},
		{0x5c,0x5e,0x28,0x2a,0x20,0x21,0x22,Cell.C_UnReverser},
		{0x09,0x0a,0x25,0x66,0x58,0x59,0x5a,0x5b}};
	static final int[][] groove = {
		{0x04,0x06,0x03,0x90,0x8e,0x8c,0x8f,0x8d},
		{0x38,0x43,0x39,0x3c,0x48,0x53,0x49,0x4c},
		{0x41,0x44,0x40,0x37,0x51,0x54,0x50,0x47},
		{0x3a,0x42,0x3b,0x3d,0x4a,0x52,0x4b,0x4d},
		{0x3e,0x36,0x3f,0x35,0x4e,0x46,0x4f,0x45}};
	static final int mTiles = 1;
	static final int mConnections = 2;
	static final Color lifeColor = new Color(200,100,0);
	static final Color[] colors = {
		new Color(0,0,0), new Color(255,0,0), new Color(0,255,0), new Color(0,0,255), 
		new Color(255,128,128), new Color(128,255,128), new Color(128,128,255), new Color(255,128,0),
		new Color(255,0,128), new Color(0,255,128), new Color(128,255,0), new Color(0,128,255),
		new Color(128,0,255), new Color(128,0,0), new Color(0,128,0), new Color(0,0,128),
		new Color(128,64,64), new Color(64,128,64), new Color(64,64,128), new Color(128,64,0),
		new Color(128,0,64), new Color(0,128,64), new Color(64,128,0), new Color(0,64,128),
		new Color(64,0,128), new Color(64,0,0), new Color(0,64,0), new Color(0,0,64),
		new Color(64,64,0), new Color(64,0,64), new Color(0,0,64), new Color(128,128,128)
		};
		
		
/*	static Cursor CUR_No = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
	static Cursor CUR_Connect = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	static Cursor CUR_Default = Cursor.getDefaultCursor();*/
	
	static Dimension HotSpot16x16 = Toolkit.getDefaultToolkit().getBestCursorSize(16,16);
	static Dimension HotSpot12x1 = Toolkit.getDefaultToolkit().getBestCursorSize(12,1);

	static Cursor CUR_No = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Cursors" + ImageLoader.separator + "CursorNo.gif").getImage(), new Point(HotSpot16x16.width < 16 ? HotSpot16x16.width : 16, HotSpot16x16.height < 16 ? HotSpot16x16.height : 16), "No Cursor");
	static Cursor CUR_Move = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Cursors" + ImageLoader.separator + "CursorMove.gif").getImage(), new Point(HotSpot16x16.width < 16 ? HotSpot16x16.width : 16, HotSpot16x16.height < 16 ? HotSpot16x16.height : 16), "Move Cursor");
	static Cursor CUR_Connect = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Cursors" + ImageLoader.separator + "CursorHand.gif").getImage(), new Point(HotSpot12x1.width < 12 ? HotSpot12x1.width : 12, HotSpot12x1.height < 1 ? HotSpot16x16.height : 1), "Hand Cursor");
	static Cursor CUR_Default = Cursor.getDefaultCursor();
	
	Game game;
	Level level;
	static Image[] tiles = new Image[MAX_TILES];
	static Image[] stiles = new Image[MAX_STILES];
	int tileSize;
	int[][] currentSet;
	boolean showMarks;
	int curTile,curData,mode;
	int HmaxVisible, VmaxVisible;
	int HPos, VPos;
	NoteHandler noteHandler;
	LevelPropertiesEditor propertiesEditor;
	UmbrellaHandler umbrellaHandler = new UmbrellaHandler(this);
	FlipHandler flipHandler = new FlipHandler(this);
	BelowHandler belowHandler = new BelowHandler(this);
	LevelAnalyzer analyzer = new LevelAnalyzer(this);
	TileHelp tileHelp = new TileHelp(this);
	
	GameEditor ged;
	Vector events = new Vector(50);
	int lastEvent = -1;
	Graphics board;
	Color marksColor = Color.red;
	Timer scrollTimer;
	boolean changed;
		
	MyCanvas pic = new MyCanvas(this,MSG_PICCLICK, MSG_PICPRESS, MSG_PICRELEASE, MSG_PICMOVE, boardWidth,boardHeight);
	MyScrollBar vbar = new MyScrollBar(this,MSG_VBAR,MyScrollBar.VERTICAL, 0,1,0,10);
	MyScrollBar hbar = new MyScrollBar(this,MSG_HBAR,MyScrollBar.HORIZONTAL, 0,1,0,10);
	BlackLabel sttXy = new BlackLabel("",50,10);
	BlackLabel sttName = new BlackLabel("",150,10);
	BlackLabel sttData = new BlackLabel("",50,10);
	BlackLabel sttDesc = new BlackLabel("",200,10);
	MyRadioButton standardRadio = new MyRadioButton(this,MSG_STANDARD,"Standard",true,10,16);
	MyRadioButton additionalRadio = new MyRadioButton(this,MSG_ADDITIONAL,"Additional",false,10,16);
//	MyRadioButton moreRadio = new MyRadioButton(this,MSG_STANDARD,"More",true,10,16);
	MyRadioButton grooveRadio = new MyRadioButton(this,MSG_GROOVE,"Groove / Rampart",false,10,16);
	MyCanvas tileset = new MyCanvas(this, MSG_NULL, MSG_TILESET, MSG_NULL, MSG_NULL, 128,80);
	MyCanvas currentTile = new MyCanvas(this, 32,32);
	MyButton tileHelpButton = new MyButton(this,MSG_TILEHELP,"Tile help");
	BlackLabel currentName = new BlackLabel("");
	MyRadioButton sizeRadio1 = new MyRadioButton(this,MSG_SIZE1,"10 x 9",false,10,16);
	MyRadioButton sizeRadio2 = new MyRadioButton(this,MSG_SIZE2,"15 x 13",true,10,16);
	MyRadioButton sizeRadio3 = new MyRadioButton(this,MSG_SIZE3,"30 x 27",false,10,16);
	MyCheckBox marksCheckbox = new MyCheckBox(this,MSG_MARKS,"Show marks",false);
	MyRadioButton tilesRadio = new MyRadioButton(this,MSG_TILES,"Work on tiles",true,10,16);
	MyRadioButton connectionsRadio = new MyRadioButton(this,MSG_CONNECTIONS,"Work on connections",false,10,16);
	/*
	MyButton undoButton = new MyButton(this,MSG_UNDO,"<", KeyEvent.VK_COMMA);
	MyButton redoButton = new MyButton(this,MSG_REDO,">", KeyEvent.VK_PERIOD);
	*/
	MyButton undoButton = new MyButton(this,MSG_UNDO,"Undo", KeyEvent.VK_U);
	MyButton redoButton = new MyButton(this,MSG_REDO,"Redo", KeyEvent.VK_R);
	volatile boolean up = false;
	volatile boolean down = false;
	volatile boolean left = false;
	volatile boolean right = false;	
	volatile int button;
	volatile int startx, starty;
	volatile int curx,cury;
	
	public LevelEditor(GameEditor parent, LevelPropertiesEditor lpe) {
		super(parent,"Editing level",true);
		ged = parent;
		noteHandler = new NoteHandler(this,ged);
		propertiesEditor = lpe;
//		setSize(670,500);
		setSize(645,500);
		setupComponents();
		addComponents();
		board = pic.getGraphics();
		loadTileImages(parent,"2BitTiles", ged.gauge, false);
		scrollTimer = new Timer(100,false);
		scrollTimer.setDialog(this, MSG_TIMERSCROLL);
	}
	
	void setupComponents() {
		ButtonGroup bg;
		bg = new ButtonGroup();
		bg.add(standardRadio);
		bg.add(additionalRadio);
//		bg.add(moreRadio);
		bg.add(grooveRadio);
		bg = new ButtonGroup();
		bg.add(sizeRadio1);
		bg.add(sizeRadio2);
		bg.add(sizeRadio3);
		bg = new ButtonGroup();
		bg.add(tilesRadio);
		bg.add(connectionsRadio);
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout gbl;
		JPanel pnl;
		//external componenets
		layout.setConstraints(pic,new GridBagConstraints(0,0,1,1,0,0,c.NORTHWEST,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(pic);		
		layout.setConstraints(vbar,new GridBagConstraints(1,0,1,1,0,0,c.NORTHWEST,c.VERTICAL,new Insets(1,1,1,1),0,0));
		paneadd(vbar);		
		layout.setConstraints(hbar,new GridBagConstraints(0,1,1,1,0,0,c.NORTHWEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(hbar);
		// right bar components
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
		
		addBarComponents(pnl,gbl);
		
		layout.setConstraints(pnl,new GridBagConstraints(2,0,1,2,1,1,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(pnl);		
		// status line components
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
		
		gbl.setConstraints(sttXy,new GridBagConstraints(0,0,1,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sttXy);		
		gbl.setConstraints(sttName,new GridBagConstraints(1,0,1,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sttName);		
		gbl.setConstraints(sttData,new GridBagConstraints(2,0,1,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sttData);		
		gbl.setConstraints(sttDesc,new GridBagConstraints(3,0,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sttDesc);		
		
		layout.setConstraints(pnl,new GridBagConstraints(0,2,3,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(pnl);		
		
	}
	
	void addBarComponents(JPanel p, GridBagLayout g) {
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout gbl;
		JPanel pnl;
		BlackLabel bl;
		MyButton mb;
		
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
/*		
		bl = new BlackLabel("Tileset:");
		gbl.setConstraints(bl,new GridBagConstraints(0,0,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(bl);
		standardRadio.setMnemonic(KeyEvent.VK_D);
		gbl.setConstraints(standardRadio,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(standardRadio);
		additionalRadio.setMnemonic(KeyEvent.VK_A);
		gbl.setConstraints(additionalRadio,new GridBagConstraints(0,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(additionalRadio);
		grooveRadio.setMnemonic(KeyEvent.VK_G);
		gbl.setConstraints(grooveRadio,new GridBagConstraints(0,3,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(grooveRadio);
*/
		standardRadio.setMnemonic(KeyEvent.VK_D);
		gbl.setConstraints(standardRadio,new GridBagConstraints(0,0,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(standardRadio);
		additionalRadio.setMnemonic(KeyEvent.VK_A);
		gbl.setConstraints(additionalRadio,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(additionalRadio);
//		moreRadio.setMnemonic(KeyEvent.VK_O);
//		gbl.setConstraints(moreRadio,new GridBagConstraints(0,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
//		pnl.add(moreRadio);
		grooveRadio.setMnemonic(KeyEvent.VK_G);
		gbl.setConstraints(grooveRadio,new GridBagConstraints(0,3,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(grooveRadio);
		
		g.setConstraints(pnl,new GridBagConstraints(0,0,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(pnl);		
		
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
		
		gbl.setConstraints(tileset,new GridBagConstraints(0,0,2,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		pnl.add(tileset);
		gbl.setConstraints(currentTile,new GridBagConstraints(0,1,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		pnl.add(currentTile);
		tileHelpButton.setMnemonic(KeyEvent.VK_L);
		gbl.setConstraints(tileHelpButton,new GridBagConstraints(1,1,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		pnl.add(tileHelpButton);
		gbl.setConstraints(currentName,new GridBagConstraints(0,2,2,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(currentName);
		
		g.setConstraints(pnl,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(pnl);		
		
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
		
		bl = new BlackLabel("Size:");
		gbl.setConstraints(bl,new GridBagConstraints(0,0,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(bl);
		sizeRadio1.setMnemonic(KeyEvent.VK_0);
		gbl.setConstraints(sizeRadio1,new GridBagConstraints(0,1,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sizeRadio1);
		sizeRadio2.setMnemonic(KeyEvent.VK_1);
		gbl.setConstraints(sizeRadio2,new GridBagConstraints(0,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sizeRadio2);
		sizeRadio3.setMnemonic(KeyEvent.VK_2);
		gbl.setConstraints(sizeRadio3,new GridBagConstraints(0,3,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(sizeRadio3);
		
		g.setConstraints(pnl,new GridBagConstraints(0,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(pnl);		
		
		marksCheckbox.setMnemonic(KeyEvent.VK_M);
		g.setConstraints(marksCheckbox,new GridBagConstraints(0,3,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(marksCheckbox);
		tilesRadio.setMnemonic(KeyEvent.VK_T);
		g.setConstraints(tilesRadio, new GridBagConstraints(0,4,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(tilesRadio);		
		connectionsRadio.setMnemonic(KeyEvent.VK_N);
		g.setConstraints(connectionsRadio,new GridBagConstraints(0,5,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(connectionsRadio);
		
		gbl = new GridBagLayout();
		pnl = new JPanel(gbl);
		
		mb = new MyButton(this,MSG_PROPERTIES,"Properties ...",KeyEvent.VK_P);
		gbl.setConstraints(mb,new GridBagConstraints(0,0,2,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(mb);
		mb = new MyButton(this,MSG_ANALYZE,"Analyze ...",KeyEvent.VK_Y);
		gbl.setConstraints(mb,new GridBagConstraints(0,1,2,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(mb);
		gbl.setConstraints(undoButton,new GridBagConstraints(0,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(undoButton);
		gbl.setConstraints(redoButton,new GridBagConstraints(1,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(redoButton);
		/*
		mb = new MyButton(this,MSG_HELP,"Help",KeyEvent.VK_H);
		gbl.setConstraints(mb,new GridBagConstraints(2,2,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(mb);
		*/
		mb = new MyButton(this,MSG_SAVE,"Save",KeyEvent.VK_S);
		gbl.setConstraints(mb,new GridBagConstraints(0,3,1,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(mb);
		mb = new MyButton(this,MSG_CANCEL,"Cancel",KeyEvent.VK_C);
		gbl.setConstraints(mb,new GridBagConstraints(1,3,1,1,0,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		pnl.add(mb);
		
		g.setConstraints(pnl,new GridBagConstraints(0,6,1,1,1,0,c.CENTER,c.BOTH,new Insets(1,1,1,1),0,0));
		p.add(pnl);				
	}
	
/*	void loadTileImagesA(String directory, JProgressBar p) {
		int maxper = MAX_TILES+MAX_STILES;
		int per = 0;
		for (int i=0; i<MAX_TILES; i++) {
			per = (100*i) / maxper;
			if (p != null) {
				p.setValue(per);
				p.repaint();
			}
			String name = directory+"\\tile"+int2str(i)+".gif";
			tiles[i] = new ImageIcon(name).getImage();
		}		
		for (int i=0; i<MAX_STILES; i++) {
			per = (100*(i+MAX_TILES)) / maxper;
			if (p != null) {
				p.setValue(per);
				p.repaint();
			}
			String name = directory+"\\tile1"+int2str(i)+".gif";
			stiles[i] = new ImageIcon(name).getImage();
		}
		belowHandler.prepare();
		if (p!= null) {
			p.setValue(0);
		}
	}*/
	
	void loadTileImages(GameEditor ged, String directory, JProgressBar p, boolean b) {
		ImageLoader il = new ImageLoader(ged, this,directory,p,b);
	}
	
	String int2str(int i) {
		String s = String.valueOf(i);
		if (i<100) s="0"+s;
		if (i<10) s="0"+s;
		return s;
	}
		
	void undo(LevelEvent e) {
		switch (e.ID) {
		case LevelEvent.LE_draw:
			if (level.contains(e.x,e.y))
				level.cells[e.x][e.y] = new Cell(e.oldTile,e.oldData);
			drawLevel(e.x, e.y);
			break;
		case LevelEvent.LE_connect:
			if (level.contains(e.x,e.y) && level.contains(e.secondx,e.secondy)) {
				level.cells[e.x][e.y] = new Cell(e.tile,e.oldData);
				level.cells[e.secondx][e.secondy] = new Cell(e.secondtile,e.secondolddata);
			}
			drawLevel(e.x,e.y);
			drawLevel(e.secondx,e.secondy);
			break;
		case LevelEvent.LE_data_changed:
			if (level.contains(e.x,e.y))
				level.cells[e.x][e.y] = new Cell(e.tile,e.oldData);
			drawLevel(e.x, e.y);
			break;
		}
	}
	
	void redo(LevelEvent e) {
		switch (e.ID) {
		case LevelEvent.LE_draw:
			if (level.contains(e.x,e.y))
				level.cells[e.x][e.y] = new Cell(e.tile,e.data);
			drawLevel(e.x,e.y);
			break;
		case LevelEvent.LE_connect:
			if (level.contains(e.x,e.y) && level.contains(e.secondx,e.secondy)) {
				level.cells[e.x][e.y] = new Cell(e.tile,e.data);
				level.cells[e.secondx][e.secondy] = new Cell(e.secondtile,e.seconddata);
			}
			drawLevel(e.x,e.y);
			drawLevel(e.secondx,e.secondy);
			break;
		case LevelEvent.LE_data_changed:
			if (level.contains(e.x,e.y))
				level.cells[e.x][e.y] = new Cell(e.tile,e.data);
			drawLevel(e.x,e.y);
			break;
		}
	}
	
	void undo() {
		LevelEvent e = (LevelEvent)events.elementAt(lastEvent);
		undo(e);
		lastEvent--;
		setUndoRedo();
	}
	
	void redo() {
		lastEvent++;
		LevelEvent e = (LevelEvent)events.elementAt(lastEvent);
		redo(e);
		setUndoRedo();
	}
	
	void addEvent(LevelEvent e) {
		if (lastEvent < (events.size()-1)) {
			//remove all forward events, if any
			for (int i=events.size()-1; i>lastEvent; i--) {
				events.removeElementAt(i);
			}
		}
		events.addElement(e);
		lastEvent = events.indexOf(e);
		setUndoRedo();
	}
	
	void setUndoRedo() {
		redoButton.setEnabled(lastEvent < (events.size()-1));
		undoButton.setEnabled(!events.isEmpty() && (lastEvent >= events.indexOf(events.firstElement())));
	}
	
	public void drawTile(Graphics g, int x, int y, int tile, int size) {
		Image i;
		if (tile >= 1000) {
			i = stiles[tile-1000];
		} else 
			i = tiles[tile];
		if (size == 16) {
			g.drawImage(i,x,y,Color.white,this);
		} else {
//			Rectangle r = new Rectangle(x,y,size,size);
			g.drawImage(i,x,y,size,size,Color.white,this);
		}
	}
	
	public void prepareLevel() {
		setTitle("Level Editor - "+level.name);
		standardRadio.setSelected(true);
		sizeRadio2.setSelected(true);
		marksCheckbox.setSelected(false);
		tilesRadio.setSelected(true);
		setTileSize(32);
		showMarks = false;
		clearBoard();
		mode = mTiles;
		drawLevel();
		currentSet = standard;
		drawTileset();
		setCurrentTile(4,0);
		events.removeAllElements();
		lastEvent = -1;
		setUndoRedo();
		changed = false;
	}

	public void clearBoard() {
		board.setColor(Color.gray);
		board.fillRect(0,0,boardWidth, boardHeight);
	}
	
	public void drawLevel() {
		int xx = HmaxVisible;
		int yy = VmaxVisible;
		if ((xx+hbar.getValue()) > level.width) xx = level.width-hbar.getValue();
		if ((yy+vbar.getValue()) > level.height) yy = level.height-vbar.getValue();
		for (int y = 0; y<yy; y++) {
			for (int x = 0; x<xx; x++) {
				int cellx = x+hbar.getValue();
				int celly = y+vbar.getValue();
				int pic = level.cells[cellx][celly].tile;
				drawTile(board,x*tileSize,y*tileSize,pic,tileSize);
			}
		}
		if (showMarks) {
			board.setColor(marksColor);
			int s = (tileSize/3)+2;
			int e = ((tileSize*2)/3)-s-2;
			if (e == 1) e++;
			for (int y = 0; y<yy; y++) {
				for (int x = 0; x<xx; x++) {
					if ((((x+hbar.getValue()) % 9) == 0) || (((y+vbar.getValue()) % 8) == 0))
						board.drawOval(x*tileSize+s,y*tileSize+s,e,e);
				}
			}
		} 
		if (mode == mConnections) {
			for (int y = 0; y<yy; y++) {
				for (int x = 0; x<xx; x++) {
					Cell c = level.cells[x+hbar.getValue()][y+vbar.getValue()];
					if (c.isConnectable()) {
						if ((c.data > 0) && (c.data < 32)) {
							board.setColor(colors[c.data]);
							board.drawOval(x*tileSize+1,y*tileSize+1,tileSize-2,tileSize-2);
						} else if (c.data == 255) {
							board.setColor(lifeColor);
							board.drawOval(x*tileSize+3,y*tileSize+3,tileSize-5,tileSize-5);
						}
					}
				}
			}
		}
		//TODO: after finished, draw only necessary parts, other move.
		pic.refresh();
	}
			
	public void drawLevel(int lx, int ly) {
		int xx = HmaxVisible;
		int yy = VmaxVisible;
		if ((xx+hbar.getValue()) > level.width) xx = level.width-hbar.getValue();
		if ((yy+vbar.getValue()) > level.height) yy = level.height-vbar.getValue();
		int sx = lx-hbar.getValue();
		int sy = ly-vbar.getValue();
		drawTile(board,sx*tileSize,sy*tileSize,level.cells[sx+hbar.getValue()][sy+vbar.getValue()].tile,tileSize);
		if (showMarks) {
			board.setColor(marksColor);
			int s = (tileSize/3)+2;
			int e = ((tileSize*2)/3)-s-2;
			if (e == 1) e++;
			if ((((sx+hbar.getValue()) % 9) == 0) || (((sy+vbar.getValue()) % 8) == 0))
				board.drawOval(sx*tileSize+s,sy*tileSize+s,e,e);
		} 
		if (mode == mConnections) {
			Cell c = level.cells[sx+hbar.getValue()][sy+vbar.getValue()];
			if (c.isConnectable()) {
				if ((c.data > 0) && (c.data < 32)) {
					board.setColor(colors[c.data]);
					board.drawOval(sx*tileSize+1,sy*tileSize+1,tileSize-2,tileSize-2);
				} else if (c.data == 255) {
					board.setColor(lifeColor);
					board.drawOval(sx*tileSize+3,sy*tileSize+3,tileSize-5,tileSize-5);
				}
			}
		}
		//TODO: after finished, draw only necessary parts, other move.
		pic.refresh();
	}

	public void drawTileset() {
		for (int y=0; y<5; y++) {
			for (int x=0; x<8; x++) {				
				drawTile(tileset.getGraphics(),x*16,y*16,currentSet[y][x],16);
			}
		}
		tileset.refresh();
	}
	
	public void setCurrentTile(int tile,int data) {
		curTile = tile;
		curData = data;
		drawTile(currentTile.getGraphics(),0,0,tile,32);
		currentTile.refresh();
		currentName.setText(Cell.getNameOfTile(tile));
	}
	
	public void setTileSize(int size) {
		tileSize = size;
		HmaxVisible	= boardWidth / tileSize;
		VmaxVisible = boardHeight / tileSize;
		adjustScrollBars();
		clearBoard();
	}
	
	public void setMode(int m) {
		mode = m;
		drawLevel();
	}
	
	public boolean setCell(int x, int y, int tile, int data) {
		changed = true;
		if ((tile == 0x5f) && (data == 0)) data = 10;
		if ((level.cells[x][y].tile != tile) || (level.cells[x][y].data != data)) {
			level.cells[x][y].tile = tile;
			level.cells[x][y].data = data;
			if ((level.cells[x][y].isBelow()) && (level.cells[x][y].data == 0)) level.cells[x][y].data = 4;
			drawLevel(x,y);
			return true;
		}
		if ((level.cells[x][y].isBelow()) && (level.cells[x][y].data == 0)) level.cells[x][y].data = 4;
		return false;
	}

	public void adjustScrollBars(){
		BoundedRangeModel hb = hbar.getModel();
		BoundedRangeModel vb = vbar.getModel();
		if (HmaxVisible < level.width) {
			hbar.setEnabled(true);
			hb.setMinimum(0);
			hb.setMaximum(level.width-1);
			hb.setValue(0);
			hb.setExtent(HmaxVisible-1);
		} else {
			hbar.setEnabled(false);
			hb.setMinimum(0);
			hb.setMaximum(0);
			hb.setValue(0);
		}
		if (VmaxVisible < level.height) {
			vbar.setEnabled(true);
			vb.setMinimum(0);
			vb.setMaximum(level.height-1);
			vb.setValue(0);
			vb.setExtent(VmaxVisible-1);
		} else {
			vbar.setEnabled(false);
			vb.setMinimum(0);
			vb.setMaximum(0);
			vb.setValue(0);
		}
	}
	
	public int findUnusedChannel() {
		for (int c = 1; c<32; c++) {
			boolean found = false;
			for (int x =0; x<level.width; x++) {
				for (int y =0; y<level.height; y++) {
					if (level.cells[x][y].isConnectable() && (level.cells[x][y].data == c)) {
						found = true;
					}
				}
			}
			if (!found) return c;
		}
		return 1;
	}
	
	public void connect(int sx, int sy, int ex, int ey) {
		changed = true;
		// both cells have been checked and are connectable.
		// now find a channel.
		int channel;
		// if either one has a channel, this is the one we seek,
		// the first one has a higher priority
		if (level.cells[sx][sy].data != 0)
			channel = level.cells[sx][sy].data;
		else if (level.cells[ex][ey].data != 0)
			channel = level.cells[ex][ey].data;
		// otherwise, find an unused channel
		else channel = findUnusedChannel();
		// bind Game-Of-Life to channel 2.
		if (channel == 255) {
			// don't ruin the already-bound-to-game-of-life tile
			if (level.cells[sx][sy].data != 255) {
				Cell c = new Cell(level.cells[sx][sy]);
				if (level.cells[sx][sy].data != 2) {
					level.cells[sx][sy].data = 2;
					addEvent(new LevelEvent(LevelEvent.LE_connect,sx, sy,
											level.cells[sx][sy].tile,
											level.cells[sx][sy].data,
											c.data,
											ex,ey,
											level.cells[ex][ey].tile,
											level.cells[ex][ey].data,
											level.cells[ex][ey].data));
				}
			}
			if (level.cells[ex][ey].data != 255) {
				Cell c = new Cell(level.cells[ex][ey]);
				if (level.cells[ex][ey].data != 2) {
					level.cells[ex][ey].data = 2;
					addEvent(new LevelEvent(LevelEvent.LE_connect,ex, ey,
											level.cells[ex][ey].tile,
											level.cells[ex][ey].data,
											c.data,
											sx,sy,
											level.cells[sx][sy].tile,
											level.cells[sx][sy].data,
											level.cells[sx][sy].data));
				}
			}
		} else {
			if ((level.cells[sx][sy].data != channel) || (level.cells[ex][ey].data != channel)) {
				Cell s = new Cell(level.cells[sx][sy]);
				Cell e = new Cell(level.cells[ex][ey]);
				level.cells[sx][sy].data = channel;
				level.cells[ex][ey].data = channel;
				addEvent(new LevelEvent(LevelEvent.LE_connect,
										sx,sy,s.tile,channel,s.data,
										ex,ey,e.tile,channel,e.data));
			}
		}
	}
	
	public void process(int msg, int x, int y, int z) {
		switch (msg) {
			case MSG_HBAR:
			case MSG_VBAR:
				drawLevel();
				break;
			case MSG_SIZE1:
				if (sizeRadio1.isSelected()) {
					setTileSize(48);
					drawLevel();
				}
				break;
			case MSG_SIZE2:
				if (sizeRadio2.isSelected()) {
					setTileSize(32);
					drawLevel();
				}
				break;
			case MSG_SIZE3:
				if (sizeRadio3.isSelected()) {
					setTileSize(16);
					drawLevel();
				}
				break;
			case MSG_TIMERSCROLL:
				if (up) {
					if (vbar.getValue() > vbar.getMinimum()) vbar.setValue(vbar.getValue()-1);
				} else if (down) {
					if (vbar.getValue()+vbar.getModel().getExtent()-1 < vbar.getMaximum()) vbar.setValue(vbar.getValue()+1);
				}
				if (left) {
					if (hbar.getValue() > hbar.getMinimum()) hbar.setValue(hbar.getValue()-1);
				} else if (right) {
					if (hbar.getValue()+hbar.getModel().getExtent()-1 < hbar.getMaximum()) hbar.setValue(hbar.getValue()+1);
				}
				break;
			case MSG_PICCLICK:
				break;
			case MSG_PICPRESS:
				startx = (x/tileSize) + hbar.getValue();
				starty = (y/tileSize) + vbar.getValue();
				if (level.contains(startx,starty) && ((y/tileSize) < VmaxVisible)) {
					Cell c = new Cell(level.cells[startx][starty]);
					if ((z & InputEvent.BUTTON1_MASK) != 0) {
						if (mode == mTiles) {
							if (setCell(startx,starty,curTile,curData))
								addEvent(new LevelEvent(LevelEvent.LE_draw,startx,starty,curTile,curData,c.tile,c.data));
						} else {
							if (level.cells[startx][starty].isConnectable()) {
								setCursor(CUR_Move);
							} else if (level.cells[startx][starty].isEditor()) {
								switch (level.cells[startx][starty].tile) {
								case 0x07://note
									noteHandler.noteChoice.removeAllItems();
									for (int i = 0; i < game.noteCount; i++)
										noteHandler.noteChoice.addItem(game.notes[i]);
									if ((c.data > 0) && (c.data <= game.noteCount))
										noteHandler.noteChoice.setSelectedIndex(level.cells[startx][starty].data-1);
									else 
										noteHandler.noteChoice.setSelectedIndex(-1);
									noteHandler.newButton.setEnabled(game.noteCount < Game.MAX_NOTES);
									if (game.noteCount > 0) {
										if (noteHandler.showDlg() == OK) {
											level.cells[startx][starty].data = noteHandler.noteChoice.getSelectedIndex()+1;
											addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
										}
									} else {
										if (noteHandler.showDlgNewNote() == OK) {
											level.cells[startx][starty].data = noteHandler.noteChoice.getSelectedIndex()+1;
											addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
										}
									}
									break;
								case 0x5f://parachute
									if (level.cells[startx][starty].data != 0)
										umbrellaHandler.seconds.setValue(level.cells[startx][starty].data);
									else 
										umbrellaHandler.seconds.setValue(1);
									if (umbrellaHandler.showDlg() == OK) {
										level.cells[startx][starty].data = umbrellaHandler.seconds.getValue();
										addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
									}
									break;
								case 0x5a:
								case 0x5b:
									int xx = level.cells[startx][starty].data & 0x0f;
									int yy = (level.cells[startx][starty].data >> 4) & 0x0f;
									flipHandler.Achannel.setValue(xx);
									flipHandler.Bchannel.setValue(yy);
									if (flipHandler.showDlg() == OK) {
										xx = flipHandler.Achannel.getValue();
										yy = flipHandler.Bchannel.getValue();
										level.cells[startx][starty].data = (yy<<4) | xx;
										addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
									}
									break;
								}
								startx = -1;
								starty = -1;
								button = -1;
								setCursor(CUR_Default);
							} else if (level.cells[startx][starty].isBelow()) {
								belowHandler.setTile(level.cells[startx][starty].data);
								if (belowHandler.showDlg() == OK) {
									level.cells[startx][starty].data = belowHandler.currentTile;
									addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
								}
								startx = -1;
								starty = -1;
								button = -1;
								setCursor(CUR_Default);						
							} else {
								startx = -1;
								starty = -1;
								button = -1;				
							}
						}
					} else if ((z & InputEvent.BUTTON3_MASK) != 0) {
						if (mode == mTiles) {
							if (level.contains(startx,starty) && ((y/tileSize) < VmaxVisible)) {
								setCurrentTile(c.tile,c.data);
							}
						} else {
							if (level.cells[startx][starty].isConnectable()) {
								if (level.cells[startx][starty].data != 0) {
									level.cells[startx][starty].data = 0;
									addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
									drawLevel(startx, starty);
								} else if (level.cells[startx][starty].isLife()) {
									level.cells[startx][starty].data = 255;							
									addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
									drawLevel(startx, starty);
								}
							} else if (level.cells[startx][starty].isEditor()) {
							} else if (level.cells[startx][starty].isBelow()) {
								level.cells[startx][starty].data = 0x04;
								addEvent(new LevelEvent(LevelEvent.LE_data_changed,startx,starty,c.tile,level.cells[startx][starty].data,c.data));
							} else if (level.cells[startx][starty].isLife()) {
							}
						}
						// no drag-drop with right click
						startx = -1;
						starty = -1;
						button = -1;				
					}
				} else {
					startx = -1;
					starty = -1;
					button = -1;
				}
				break;
			case MSG_PICRELEASE:
				curx = (x/tileSize) + hbar.getValue();
				cury = (y/tileSize) + vbar.getValue();
				if (level.contains(curx,cury) && level.contains(startx,starty)) {
					Cell c = level.cells[curx][cury];
					if ((z & InputEvent.BUTTON1_MASK) != 0) {
						if (mode == mTiles) {
						} else {
							if (((curx != startx) || (cury != starty)) && (c.isConnectable()) && (startx != -1) && (starty!= -1)) {
								if (!scrollTimer.getEnabled()) {
									connect(startx,starty,curx,cury);
									drawLevel(startx, starty);
									drawLevel(curx, cury);
								}
							}
							setCursor(CUR_Default);
						}
					} else if ((z & InputEvent.BUTTON3_MASK) != 0) {
						if (mode == mTiles) {
						} else {
						}
					}
				}
				scrollTimer.stop();
				startx = -1;
				starty = -1;
				button = -1;
				curx = -1;
				cury = -1;
				break;
			case MSG_PICMOVE:
				curx = (x/tileSize) + hbar.getValue();
				cury = (y/tileSize) + vbar.getValue();
				if ((level.contains(startx,starty)) && ((z & InputEvent.BUTTON1_MASK)!=0) && (mode == mConnections) ) {
					if ((x < 0)||(x > pic.getWidth()) ||(y < 0) ||(y > pic.getHeight()))  {
						if (x < 0) left = true;
						else left = false;
						if (x > pic.getWidth()) right = true;
						else right = false;
						if (y < 0) 
							up = true;
						else up = false;
						if (y > pic.getHeight()) 
							down = true;
						else down = false;
						scrollTimer.start();
					} else {
						scrollTimer.stop();
					}
				}
				if (level.contains(curx,cury) && ((y/tileSize) < VmaxVisible)) {
					Cell c = level.cells[curx][cury];
					if (level.contains(startx,starty)) {
						if ((z & InputEvent.BUTTON1_MASK) != 0) {
							if (mode == mTiles) {
								Cell cl = new Cell(level.cells[curx][cury]);
								if (setCell(curx,cury,curTile,curData))
									addEvent(new LevelEvent(LevelEvent.LE_draw,curx,cury,curTile,curData,cl.tile,cl.data));
								c = level.cells[curx][cury];
							} else {
								if ((curx == startx) && (cury == starty)) {
									// if we are where we started, then put a MOVE cursor
									setCursor(CUR_Move);
								} else if (c.isConnectable()) {
									// if we are on a connectable tile, put a HAND cursor
									setCursor(CUR_Connect);
								} else {
									// otherwise, a NO cursor
									setCursor(CUR_No);
								}
							}
						} else if ((z & InputEvent.BUTTON3_MASK) != 0) {
							if (mode == mTiles) {
								setCurrentTile(c.tile,c.data);
							} else {
							}
						}
					}
					sttXy.setText(String.valueOf(curx)+" , "+String.valueOf(cury));
					sttName.setText(Cell.getNameOfTile(c.tile));
					String d = Integer.toHexString(c.data);
					if (c.data < 16) d = "0"+d;
					sttData.setText(d);
					String s = "";
					switch (c.tile) {
					case 0x07:
						if ((c.data > 0) && (c.data <= game.noteCount))
							s = game.notes[c.data-1];
						if (s == null) s = "";
						break;
					case 0x26:
					case 0x71:
					case 0x78:
					case 0x79:
					case 0x7a:
					case 0x7b:
					case 0x8c:
					case 0x8d:
					case 0x8e:
					case 0x8f:
						s = "Below: "+Cell.getNameOfTile(c.data);
						break;
					case 0x5f:
						s = "Time: "+String.valueOf(c.data)+" Seconds.";
						break;
					case 0x5a:
					case 0x5b:
						s = "X channel: "+Integer.toString(c.data & 0x0f)+"     Y channel: "+Integer.toString((c.data>>4) & 0x0f);
						break;
					}
					sttDesc.setText(s);
				}
				break;
			case MSG_STANDARD:
				if (standardRadio.isSelected()) {
					currentSet = standard;
					drawTileset();
				}
				break;
			case MSG_ADDITIONAL:
				if (additionalRadio.isSelected()) {
					currentSet = additional;
					drawTileset();
				}
				break;
			case MSG_GROOVE:
				if (grooveRadio.isSelected()) {
					currentSet = groove;
					drawTileset();
				}
				break;
			case MSG_TILESET:
				setCurrentTile(currentSet[y/16][x/16],0);		
				break;
			case MSG_MARKS:
				showMarks = marksCheckbox.isSelected();
				drawLevel();
				break;
			case MSG_TILES:
				if (tilesRadio.isSelected()) setMode(mTiles);
				break;
			case MSG_CONNECTIONS:
				if (connectionsRadio.isSelected()) setMode(mConnections);		
				break;
			case MSG_UNDO:
				undo();
				break;
			case MSG_REDO:
				redo();
				break;
			case MSG_PROPERTIES:
				propertiesEditor.width.setValue(Level.Tiles2ScreensW(level.width));
				propertiesEditor.height.setValue(Level.Tiles2ScreensH(level.height));
				propertiesEditor.name.setText(level.name);
				propertiesEditor.setTitle("Edit Level Properties");
				if (propertiesEditor.showDlg() == OK) {
					changed = true;
					level.name = propertiesEditor.name.getText();		
					int w = Level.Screens2TilesW(propertiesEditor.width.getValue());
					int h = Level.Screens2TilesH(propertiesEditor.height.getValue());
					if (w != level.width) {
						level.adjustWidth(w);
						events.removeAllElements();
					}
					if (h != level.height) {
						level.adjustHeight(h);
						events.removeAllElements();
					}
					setUndoRedo();
					adjustScrollBars();
					clearBoard();
					drawLevel();
				}
				break;
			case MSG_ANALYZE:
				analyzer.analyze(game,level);
				analyzer.showDlg();
				break;
			case MSG_HELP:
				JOptionPane.showMessageDialog(this,"Not implemented yet","Sorry",JOptionPane.INFORMATION_MESSAGE);
				break;
			case MSG_SAVE:
				result = OK;
				hide();
				break;
			case MSG_CANCEL:
				if (changed) {
					if (JOptionPane.showConfirmDialog(this,"Are you sure you wish to discard\nall changes to the level?","Are you sure?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
						result = CANCEL;
						hide();
					}
				} else {
					result = CANCEL;
					hide();
				}
				break;
			case MSG_TILEHELP:
				tileHelp.setTile(curTile);
				tileHelp.showDlg();
				//JOptionPane.showMessageDialog(this,"Not implemented yet","Sorry",JOptionPane.INFORMATION_MESSAGE);
				break;
		}
	}
	
	public void windowClosing(WindowEvent e) {
		process(MSG_CANCEL,0,0,0);
	}
	
}
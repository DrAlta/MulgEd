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

/**
 *	class BelowHandler
 *	This class implements the dialog in which you choose which tile will be
 *	below other tiles.
 **/

public class BelowHandler extends MyDialog {
	
	// Messages
	static final int MSG_OK = 1;
	static final int MSG_CANCEL = 2;
	static final int MSG_TILE = 3;
	
	// Tile constant matrix 
	public static final int[][] set = {
		{0x04,0x05,0x1b,0x1c,0x23},
		{0x2b,0x30,0x33,0x62,0x64},
		{0x6f,0x70,0x55,0x56,0x57}};
	
	// Controls	
	BlackLabel currentName = new BlackLabel("");
	MyCanvas current;
	MyCanvas tiles;
	
	// Members
	int currentTile;
		
	// Ctor
	public BelowHandler(MyDialog parent) {
		super(parent,"Choose tile to be below",true);
		setSize(200,120);
		// Add controls to the dialog
		addComponents();
	}
	
	void prepare() {
		// Draw the tiles according to the tile matrix
		Graphics g = tiles.getGraphics();
		for (int y=0; y<3; y++) {
			for (int x=0; x<5; x++) {
				g.drawImage(LevelEditor.tiles[set[y][x]],x*16,y*16,this);
			}
		}
		// Choose the floor tile
		setTile(4);
	}
	
	void setTile(int tilenum) {
		// Choose a tile
		current.getGraphics().drawImage(LevelEditor.tiles[tilenum],0,0,32,32,this);
		current.refresh();
		currentTile = tilenum;
		currentName.setText(Cell.getNameOfTile(currentTile));
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		// The UI tile matrix
		tiles = new MyCanvas(this,MSG_TILE,0,0,0,80,64);
		layout.setConstraints(tiles,new GridBagConstraints(0,0,1,2,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(tiles);
		
		// The chosen tile's picture
		current = new MyCanvas(this,32,32);
		layout.setConstraints(current,new GridBagConstraints(1,0,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(current);
		
		// The chosen tile's name
		layout.setConstraints(currentName,new GridBagConstraints(1,1,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(currentName);		
		
		GridBagLayout g = new GridBagLayout();
		
		// The buttons have their own FlowLayout panel.
		JPanel p = new JPanel(g);
		
		layout.setConstraints(p,new GridBagConstraints(0,2,2,1,1,1,c.WEST,c.BOTH,new Insets(1,1,1,1),0,0));
		paneadd(p);
		
		MyButton mb;
		mb = new MyButton(this,MSG_OK,"Ok");
		g.setConstraints(mb,new GridBagConstraints(0,0,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		p.add(mb);
		
		mb = new MyButton(this,MSG_CANCEL,"Cancel");
		g.setConstraints(mb,new GridBagConstraints(1,0,1,1,0,0,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		p.add(mb);
		
	}
	
	// Message handler
	public void process(int msg, int x, int y, int z) {
		switch (msg) {
			case MSG_TILE:
				setTile(set[y/16][x/16]);
				break;
			case MSG_OK:
				result = OK;
				hide();
				break;
			case MSG_CANCEL:
				result = CANCEL;
				hide();
				break;
		}
	}
	
}
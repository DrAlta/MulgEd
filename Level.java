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

public class Level
{
	static final int MAX_WIDTH = 37;
	static final int MAX_HEIGHT = 33;					 
	
	String name;
	int width;
	int height;
	Cell[][] cells;
	
	public Level() {
		//+1 = just in case
		cells = new Cell[MAX_WIDTH+1][MAX_HEIGHT+1];
		for (int x=0; x<MAX_WIDTH+1; x++) {
			for (int y=0; y<MAX_HEIGHT+1; y++) {
				cells[x][y] = new Cell();
			}
		}
	}
		
	public boolean contains(int x, int y) {
		return ((x < width) && (y < height) && (x >= 0) && (y >= 0));
	}
	
	public void adjustWidth(int width) {
		if (width > this.width) {
			for (int x = this.width; x < width; x++)
				for (int y = 0; y < this.height; y++) 
					cells[x][y].clear();
		}
		this.width = width;
	}
	
	public void adjustHeight(int height) {
		if (height > this.height) {
			for (int y = this.height; y < height; y++)
				for (int x = 0; x < this.width; x++)
					cells[x][y].clear();
		}
		this.height = height;
	}
	
	/**
	 * Copy Constructor.
	 */
	public Level(Level from) {
		name = new String(from.name);
		width = from.width;
		height = from.height;
		cells = new Cell[MAX_WIDTH+1][MAX_HEIGHT+1];
		for (int x=0; x<MAX_WIDTH+1; x++) {
			for (int y=0; y<MAX_HEIGHT+1; y++) {
				cells[x][y] = new Cell(from.cells[x][y]);
			}
		}
	}
	
	public String toString() {
		return name;
	}
	
	static int Tiles2ScreensW(int tiles) {
		return (tiles-1)/9;
	}
	static int Tiles2ScreensH(int tiles) {
		return (tiles-1)/8;
	}
	static int Screens2TilesW(int screens) {
		return (screens*9)+1;
	}
	static int Screens2TilesH(int screens) {
		return (screens*8)+1;
	}
}
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

public class LevelEvent extends Object
{
	static final int LE_draw = 1;
	static final int LE_connect = 2;
	static final int LE_data_changed = 3;
	
	int ID;
	int tile;
	int data;
	int x;
	int y;
	int oldTile;
	int oldData;
	int oldX;
	int oldY;
	int secondx;
	int secondy;
	int secondtile;
	int seconddata;
	int secondolddata;
	
	public LevelEvent(int id, int x, int y, int tile, int data) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.data = data;		
	}
	
	public LevelEvent(int id, int x, int y, int tile, int data, int oldData) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.data = data;
		this.oldData = oldData;
	}
	
	public LevelEvent(int id, int x, int y, int tile, int data, int oldtile, int olddata) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.data = data;
		this.oldData = olddata;
		this.oldTile = oldtile;
	}
	
	public LevelEvent(int id, int x, int y, int tile, int data, int oldx, int oldy, int oldtile, int olddata) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.data = data;
		this.oldData = olddata;
		this.oldTile = oldtile;
		this.oldX = oldx;
		this.oldY = oldy;
	}
	
	public LevelEvent(int id, int x, int y, int tile, int data, int olddata, int secondx, int secondy, int secondtile, int seconddata, int secondolddata) {
		this.ID = id;
		this.x = x;
		this.y = y;
		this.tile = tile;
		this.data = data;
		this.oldData = olddata;
		this.secondx = secondx;
		this.secondy = secondy;
		this.secondtile = secondtile;
		this.seconddata = seconddata;
		this.secondolddata = secondolddata;
	}
	
}
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
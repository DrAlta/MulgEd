/**
 *	class Cell
 *	This class is a smart class that represent one cell in a level.
 *	Such a cell includes the tile number, and the connection number.
 *	Special tiles are also supported, along with .LEV import/export and other stuff
 **/

public class Cell
{
	// Constants of special tile numbers.
	public static final int C_StartingPoint			= 1000;
	public static final int C_DescendingFloor3	= 1001;
	public static final int C_ActiveStorm				= 1002;
	public static final int C_OpenVGate					= 1003;
	public static final int C_OpenHGate					= 1004;
	public static final int C_UnReverser				= 1005;
	public static final int C_Reverser					= 1006;
	public static final int C_IceUnReverser			= 1007;
	public static final int C_IceReverser				= 1008;
	public static final int C_BfUnReverser			= 1009;
	public static final int C_BfReverser				= 1010;
	public static final int C_MemorizeBox0			= 1011;
	public static final int C_MemorizeBox1			= 1012;
	public static final int C_MemorizeBox2			= 1013;
	public static final int C_MemorizeBox3			= 1014;
	
	// Constants of tile modifiers
	public static final int CT_CONNECTABLE 			= 0x01; // user can connect to channel (0..31)
	public static final int CT_BELOW 						= 0x02; // a different tile lies below.
	public static final int CT_EDITOR 					= 0x04; // has a unique editor.
	public static final int CT_LIFE 						= 0x08; // can activate Game Of Life
	
	// Special tiles modifiers
	public static final int sCellType[] =
	{
		0x00,//C_StartingPoint
		0x00,//C_DescendingFloor3
		0x01,//C_ActiveStorm
		0x01,//C_OpenVGate
		0x01,//C_OpenHGate
		0x00,//C_UnReverser
		0x00,//C_Reverser
		0x00,//C_IceUnReverser
		0x00,//C_IceReverser
		0x00,//C_BfUnReverser
		0x00,//C_BfReverser
		0x01,//C_MemorizeBox1
		0x01,//C_MemorizeBox2
		0x01,//C_MemorizeBox3
		0x01,//C_MemorizeBox4
	};
		
	// Regular tiles modifiers
	public static final int CellType[] = 
	{
		0x00,0x00,0x00,0x01,0x01,0x00,0x00,0x04,//0-7
		0x00,0x09,0x09,0x01,0x00,0x00,0x00,0x01,//8-f	 
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,//10-17
		0x00,0x00,0x00,0x00,0x00,0x09,0x09,0x09,//18-1f
		0x09,0x09,0x09,0x00,0x00,0x09,0x02,0x00,//20-27
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//28-2f
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//30-37
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//38-3f
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//40-47
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//48-4f
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//50-57
		0x09,0x09,0x04,0x04,0x00,0x00,0x00,0x04,//58-5f
		0x00,0x00,0x00,0x00,0x00,0x00,0x09,0x00,//60-67
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//68-6f
		0x00,0x02,0x00,0x00,0x00,0x00,0x00,0x09,//70-77
		0x02,0x02,0x02,0x02,0x00,0x00,0x00,0x00,//78-7f
		0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x00,//80-87
		0x01,0x01,0x01,0x00,0x02,0x02,0x02,0x02,//88-8f
		0x00,0x00,0x00//90-92
	};
	
	// Members
	int tile;
	int data;
	
	
	// CTor
	public Cell(int tile,int data) {
		this.tile = tile;
		this.data = data;
		if ((this.isBelow()) && (data == 0)) data = 4;
	}
	
	// CTor
	public Cell() {
		this.tile = 0;
		this.data = 0;
	}
	
	// Copy-CTor
	public Cell(Cell cell) {
		if (cell != null) {
			this.tile = cell.tile;
			this.data = cell.data;
		} else {
			this.tile = 0;
			this.data = 0;
		}
	}
	
	// Modifier checking methods
	public boolean isConnectable() {
		if (tile < 1000)
			return ((CellType[tile] & CT_CONNECTABLE) != 0);
		else 
			return ((sCellType[tile-1000] & CT_CONNECTABLE) != 0);
	}
	public boolean isBelow() {
		if (tile < 1000)
			return ((CellType[tile] & CT_BELOW) != 0);
		else 
			return ((sCellType[tile-1000] & CT_BELOW) != 0);
	}
	public boolean isEditor() {
		if (tile < 1000)
			return ((CellType[tile] & CT_EDITOR) != 0);
		else 
			return ((sCellType[tile-1000] & CT_EDITOR) != 0);
	}
	public boolean isLife() {
		if (tile < 1000)
			return ((CellType[tile] & CT_LIFE) != 0);
		else 
			return ((sCellType[tile-1000] & CT_LIFE) != 0);
	}
	
	// Translates special a special tile from Mulg to MulgEd
	public Cell toMulgEd() {
		switch (tile) {
		case 0x04:
			if (data == 0xff)
				return new Cell(C_StartingPoint,0);
			if (data == 0x20)
				return new Cell(C_DescendingFloor3,0);
			if (data == 0x40)
				return new Cell(C_UnReverser,0);
			if (data == 0x80)
				return new Cell(C_Reverser,0);
			break;
		case 0x17:
			if ((data & 0x80) != 0)
				return new Cell(C_ActiveStorm,data & 0x1f);
			break;
		case 0x0e:
			if ((data & 0x80) != 0)
				return new Cell(C_OpenVGate, data & 0x1f);
			break;
		case 0x12:
			if ((data & 0x80) != 0)
				return new Cell(C_OpenHGate, data & 0x1f);
			break;
		case 0x2b:
			if (data == 0x40)
				return new Cell(C_IceUnReverser,0);
			if (data == 0x80)
				return new Cell(C_IceReverser,0);
			break;
		case 0x27:
			if (data == 0x40)
				return new Cell(C_BfUnReverser,0);
			if (data == 0x80)
				return new Cell(C_BfReverser,0);
			break;
		case 0x7c:
			if ((data & 0xc0) == 0x00)
				return new Cell(C_MemorizeBox0, data & 0x1f);
			if ((data & 0xc0) == 0x40)
				return new Cell(C_MemorizeBox1, data & 0x1f);
			if ((data & 0xc0) == 0x80)
				return new Cell(C_MemorizeBox2, data & 0x1f);
			if ((data & 0xc0) == 0xc0)
				return new Cell(C_MemorizeBox3, data & 0x1f);
			break;
		}
		return new Cell(this);
	}
	
	// Translates a special tile from MulgEd to Mulg
	public Cell toFile() {
		switch (tile) {
		case C_StartingPoint:
			return new Cell(4,0xff);
		case C_DescendingFloor3:
			return new Cell(4,0x20);
		case C_ActiveStorm:
			return new Cell(0x17,0x80+data);
		case C_OpenVGate:
			return new Cell(0x0e,0x80+data);
		case C_OpenHGate:
			return new Cell(0x12,0x80+data);
		case C_UnReverser:
			return new Cell(4,0x40);
		case C_Reverser:
			return new Cell(4,0x80);
		case C_IceUnReverser:
			return new Cell(0x2b,0x40);
		case C_IceReverser:
			return new Cell(0x2b,0x80);
		case C_BfUnReverser:
			return new Cell(0x27,0x40);
		case C_BfReverser:
			return new Cell(0x27,0x80);
		case C_MemorizeBox0:
			return new Cell(0x7c,data | 0x00);
		case C_MemorizeBox1:
			return new Cell(0x7c,data | 0x40);
		case C_MemorizeBox2:
			return new Cell(0x7c,data | 0x80);
		case C_MemorizeBox3:
			return new Cell(0x7c,data | 0xc0);
		}
		return new Cell(this);
	}
	
	// Clears the tile - regular, unconnected floor tile.
	public void clear() {
		tile = 4;
		data = 0;
	}
	
	// Returns the name of a tile
	// Also - a good reference to those who don't remember.
	public static String getNameOfTile(int tile) {
		switch (tile) {
		case 0x03:return "Empty pit";
		case 0x04:return "Floor";
		case 0x05:return "Target cross";
		case 0x06:return "Block";
		case 0x07:return "Letter";
		case 0x09:return "Switch - low";
		case 0x0a:return "Switch - high";
		case 0x0b:return "Vert. gate - closed";
		case 0x0e:
		case C_OpenVGate:return "Vert. gate - opened";
		case 0x0f:return "Horz. gate - closed";
		case 0x12:
		case C_OpenHGate:return "Horz. gate - opened";
		case 0x13:return "Swing from left";
		case 0x14:return "Swing from right";
		case 0x15:return "Swing from up";
		case 0x16:return "Swing from down";
		case 0x17:return "Inactive Ventilator";
		case 0x1b:return "Hole";
		case 0x1c:return "Bump";
		case 0x1d:return "Die - 1";
		case 0x1e:return "Die - 2";
		case 0x1f:return "Die - 3";
		case 0x20:return "Die - 4";
		case 0x21:return "Die - 5";
		case 0x22:return "Die - 6";
		case 0x23:return "Key";
		case 0x25:return "Lock";
		case 0x26:return "Heavy box";
		case 0x27:return "Heavy box in place";
		case 0x28:return "Bouncer";
		case 0x2A:return "Death cube";
		case 0x2b:return "Icy floor";
		case 0x2c:return "One-way - left";
		case 0x2d:return "One-way - right";
		case 0x2e:return "One-way - up";
		case 0x2f:return "One-way - down";
		case 0x30:return "Bomb";
		case 0x33:return "Match";
		case 0x35:
		case 0x36:
		case 0x37:
		case 0x38:
		case 0x39:
		case 0x3A:
		case 0x3B:
		case 0x3C:
		case 0x3D:
		case 0x3E:
		case 0x3F:
		case 0x40:
		case 0x41:
		case 0x42:
		case 0x43:
		case 0x44:return "Groove";
		case 0x45:
		case 0x46:
		case 0x47:
		case 0x48:
		case 0x49:
		case 0x4A:
		case 0x4B:
		case 0x4C:
		case 0x4D:
		case 0x4E:
		case 0x4F:
		case 0x50:
		case 0x51:
		case 0x52:
		case 0x53:
		case 0x54:return "Rampart";
		case 0x55:return "Descending floor x2";
		case 0x56:return "Descending floor x1";
		case 0x57:return "Descending floor x0";
		case 0x58:return "Floor switch up";
		case 0x59:return "Floor switch down";
		case 0x5a:return "Flip - on";
		case 0x5b:return "Flip - off";
		case 0x5c:return "Bomb dispenser";
		case 0x5e:return "Smiley";
		case 0x5f:return "Parachute";
		case 0x62:return "Coin - 1";
		case 0x64:return "Coin - 5";
		case 0x66:return "Coin slot";
		case 0x6F:return "Oil";
		case 0x70:return "Mud";
		case 0x71:return "Light box";
		case 0x72:return "Light box in place";
		case 0x77:return "Switch pit";
		case 0x78:
		case 0x79:
		case 0x7a:
		case 0x7b:return "Scarab beatle";
		case 0x7c:
		case 0x7d:
		case 0x7e:
		case 0x7f:
		case 0x80:
		case 0x81:
		case 0x82:
		case 0x83:
		case 0x84:return "Memorize cube";
		case 0x85:return "Hanoi tower - B";
		case 0x86:return "Hanoi tower - M";
		case 0x87:return "Hanoi tower - T";
		case 0x88:return "Hanoi tower - BM";
		case 0x89:return "Hanoi tower - BT";
		case 0x8a:return "Hanoi tower - TM";
		case 0x8b:return "Hanoi tower - BTM";
		case 0x8c:return "Walker - right";
		case 0x8d:return "Walker - down";
		case 0x8e:return "Walker - left";
		case 0x8f:return "Walker - up";
		case 0x90:return "Exchange";
		case 0x91:return "Magnet - positive";
		case 0x92:return "Magnet - negative";
		case C_MemorizeBox0:
		case C_MemorizeBox1:
		case C_MemorizeBox2:
		case C_MemorizeBox3:return "Memorize cube";
		case C_StartingPoint:return "Starting point";
		case C_DescendingFloor3:return "Descending floor x3";
		case C_ActiveStorm:return "Active ventilator";
		case C_Reverser:return "Reverser";
		case C_UnReverser:return "Un-reverser";
		case C_IceReverser:return "Icy Reverser";
		case C_IceUnReverser:return "Icy Un-reverser";
		case C_BfReverser:return "Box in place Reverser";
		case C_BfUnReverser:return "Box in place Un-reverser";
		}
		return "";
	}
	
	// Constants of .LEV file names (as defined in tiles.h and level.h")
	static final String[] LevNames = {
		"ZERO","BALL","","SPACE","PATH","GOAL","STONE","DOC","DOCX","SW","SWITCH_OFF","VDOR","VDOOR_2","VDOOR_3","VDOOR_4","DOR",
		"HDOOR_2","HDOOR_3","HDOOR_4","WIPPL","WIPPR","WIPPO","WIPPU","HVENT_1","HVENT_2","HVENT_3","HVENT_4","HOLE","HUMP","DIC1","DIC2","DIC3",
		"DIC4","DIC5","DIC6","KEY","KEYX","KH","BOX","BOXFIX","BUMP","BUMPL","SKULL","ICE","OWL","OWR","OWU","OWD",
		"BOMB","BOMBI","EXPLODE","MATCH","MATCHX","GR","GRH","GRV","GRRB","GRLB","GRRT","GRLT","GRB","GRT","GRR","GRL",
		"GRTLB","GRTRB","GRLTR","GRLBR","GRX","WA","WAH","WAV","WARB","WALB","WART","WALT","WAB","WAT","WAR","WAL",
		"WATLB","WATRB","WALTR","WALBR","WAX","VAN0","VAN1","VAN2","BUT0","BUT1","F0","F1","BOMBD","BOMBX","FRATZE","PARA",
		"BALLSH","","DM1","DM1X","DM5","DM5X","SLOT","SLOT1","SLOT2","SLOT3","SLOT4","SLOT5","SLOT6","SLOT7","SLOT8","OIL",
		"SWAMP","LBOX","LBOXFIX","","","","","SWSPACE","Sc0","Sc90","Sc180","Sc270",
		"MEM_Q","MEM_0C","MEM_0O","MEM_1C","MEM_1O","MEM_2C","MEM_2O","MEM_3C","MEM_3O",
		"HANOI_B","HANOI_M","HANOI_T","HANOI_BM","HANOI_BT","HANOI_MT","HANOI_BMT",
		"PMOV_R","PMOV_D","PMOV_L","PMOV_U","EXCHANGE","MAGNET_P","MAGNET_N"};
	
	// Returns a .LEV file string representing of the cell
	String toLevString() {
		String s="";
		if (tile>=1000) {
			switch (tile) {
			case C_ActiveStorm:
				s = "VENT."+String.valueOf(data);
				break;
			case C_BfReverser:
				s = "BOXFIX.RV";
				break;
			case C_BfUnReverser:
				s = "BOXFIX.UR";
				break;
			case C_DescendingFloor3:				
				s = "VANSH";
				break;
			case C_IceReverser:
				s = "ICE.RV";
				break;
			case C_IceUnReverser:
				s = "ICE.UR";
				break;
			case C_OpenHGate:
				s="ODOR."+String.valueOf(data);
				break;
			case C_OpenVGate:
				s="VDOOR_4."+String.valueOf(data);
				break;
			case C_StartingPoint:
				s="START";
				break;
			case C_Reverser:
				s = "PATH.RV";
				break;
			case C_UnReverser:
				s = "PATH.UR";
				break;
			case C_MemorizeBox0:
				s = "MEM0."+String.valueOf(data & 0x1f);
				break;
			case C_MemorizeBox1:
				s = "MEM1."+String.valueOf(data & 0x1f);
				break;
			case C_MemorizeBox2:
				s = "MEM2."+String.valueOf(data & 0x1f);
				break;
			case C_MemorizeBox3:
				s = "MEM3."+String.valueOf(data & 0x1f);
				break;
			default:
				s = "???";
			}
		} else {
			s=LevNames[tile];
			if ((tile == 0x09) && (data == 0xff))
				s="SLIFE";
			else if ((tile == 0x17) && ((data & 0x80) != 0))
				s="VENT";
			else if ((tile == 0x26) && ((data & 0x04) != 0))
				s="BPATH";
			else if ((tile == 0x5a) || (tile == 0x5b)) {
				s+=".0x";
				if (data < 16) s+="0";
				s+=Integer.toHexString(data);
			} else if (this.isConnectable()) {
				if (data > 0)
					s+="."+String.valueOf(data);
			} else if (this.isEditor() || this.isBelow()) {
				if ((tile == 0x07) || (tile == 0x5f)) {
					if (data > 0)
						s+="."+String.valueOf(data);
				} else {
					if (data > 0)
						s+=".0x"+Integer.toHexString(data);
				}
			} 
			
		}
		return s;
	}
	
}
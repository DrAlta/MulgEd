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

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *	class Game
 *	This class is a smart class that represent the whole game database file
 *	A game includes the levels, notes, and other information.
 *	This class implements loading / saving, manpulation, export / import and 
 *	other stuff.
 **/

public class Game extends Object
{
	// Messages
	static final int MAX_LEVELS=256;
	static final int MAX_NOTES=256;
	
	// Members
	String name;
	String author;
	int levelCount;
	int noteCount;
	Level[] levels;
	String[] notes;
	boolean debug;
	int creationDate;
	int modificationDate;
	int modificationCount;
	
	// CTor
	public Game() {
		levels = new Level[MAX_LEVELS];
		notes = new String[MAX_NOTES];
		levelCount = 0;
		noteCount = 0;
		name = "untitled";
		author = "";
		creationDate = now();
		modificationDate = creationDate;
		modificationCount = 0;
		debug = false;
	}
	
	// Statics used when loading/saving
	static byte[] content;
	static byte[] chunk;
	static int index;
	
	// Methods used for loading a PDB :
	static int getShort() {
		// Read a short from content
		int i=0;
		i |= content[index] & 0xff;
		i = i << 8;
		i |= content[index+1] & 0xff;
		index+=2;
		return i;
	}
	
	static int getChunkShort() {
		// Read a short from Chunk
		int i=0;
		i |= chunk[index] & 0xff;
		i = i << 8;
		i |= chunk[index+1] & 0xff;
		index+=2;
		return i;	
	}
	
	static int getInt() {
		// Read an int from content
		int i=0;
		i |= content[index] & 0xff;
		i = i << 8;
		i |= content[index+1] & 0xff;
		i = i << 8;
		i |= content[index+2] & 0xff;
		i = i << 8;
		i |= content[index+3] & 0xff;
		index+=4;
		return i;		
	}
	
	static int getChunkInt() {
		// Read an int from chunk
		int i=0;
		i |= chunk[index] & 0xff;
		i = i << 8;
		i |= chunk[index+1] & 0xff;
		i = i << 8;
		i |= chunk[index+2] & 0xff;
		i = i << 8;
		i |= chunk[index+3] & 0xff;
		index+=4;
		return i;		
	}
	
	static String getString(int max) {
		// Read a string from content
		String s = "";
		for (int i=0; (i<max) && (content[index+i] != 0); i++) {
			s+=(char)content[index+i];
		}		
		index+=max;
		return s;
	}
	
	static String getChunkString(int max) {
		// Read a string from chunk
		String s = "";
		for (int i=0; (i<max) && (chunk[index+i] != 0); i++) {
			s+=(char)chunk[index+i];
		}		
		index+=max;
		return s;
	}
	
	static String getChunkAsciiZ() {
		// Read a null terminated string from chunk
		String s = "";
		for (int i=0; chunk[index+i] != 0; i++) {
			s+=(char)chunk[index+i];
		}
		return s;
	}
	
	// Load a game file from a .PDB file.
	public static Game loadFromFile(String filename) throws IOException {
		// open the file for reading
		File f = new File(filename);
		// set up the input stream
		FileInputStream in = new FileInputStream(f);
		// find out file size
		long fileSize = f.length();
		// and fail if larger than 64k
		if (fileSize>65536)
			throw new IOException("Cannot open .pdb files larger than 64k");
		// prepare a byte-array to read to.
		content = new byte[(int)fileSize];
		index = 0;
		// and read
		int total = in.read(content,0,(int)fileSize);
		// and close input stream
		in.close();
		// now create a new game that will be returned.
		Game game = new Game();
		// read the game header : name, properties, etc :
		game.name = getString(32);
		// read attributes (0x0000)
		getShort(); 
		// read version (0x0001 / 0x0002)
		getShort(); 
		game.creationDate = getInt();
		game.modificationDate = getInt();
		// read last backup date
		getInt(); 
		game.modificationCount = getInt();
		// read app info (0x0000)
		getInt(); 
		// read sort info (0x0000)
		getInt(); 
		// Check that it's a Levl / LevF database
		int pdbType = getInt();
		if ((pdbType != 0x4c65766c/*Levl*/) && (pdbType != 0x4c657646/*LevF*/) && (pdbType != 0x4c65764E/*LevF*/))
			throw new IOException("Not a Mulg II / IIf or IIn game file");
		// Check that it's a Mulg database
		int appName = getInt();
		if (appName != 0x4d756c67/*Mulg*/) 
			throw new IOException("Not a Mulg game file");
		// read two Ints ???
		getInt();
		getInt();
		int chunksCount = getShort();
		int[] offsets = new int[chunksCount];
		game.levelCount = chunksCount-2;
		game.levels = new Level[MAX_LEVELS];
		for (int i=0; i<chunksCount; i++) {
			offsets[i] = getInt();
			getInt();
		}
		// now parse one chunk at a time.
		for (int chk=0; chk<chunksCount; chk++) {
			int length;			
			if (chk == chunksCount-1) 
				length = total-offsets[chk];
			else 
				length = offsets[chk+1]-offsets[chk];
			chunk = new byte[length];
			System.arraycopy(content,offsets[chk],chunk,0,length);
			index = 0;
			switch (chk) {
			case 0:
				// the first chunk is the game info chunk
				// read a short ???
				getChunkShort();
				// read the debug flag
				if (getChunkShort() == 0xffff) {
					game.debug = true;
					// remove the "(D)" from the name
					if (game.name.endsWith(" (D)")) {
						game.name = game.name.substring(0,game.name.indexOf(" (D)"));
					}
				}
				break;
			case 1:
				// the second chunk is the author & notes chunk
				int[] noffsets = new int[MAX_NOTES];
				// This chunk is a bit more complicated, with offsets all around
				int ofs = getChunkShort();
				int noteCount = 0;
				// the terminator string is "20", for some strange reason.
				while (ofs != 0x3230/*20*/) {
					noffsets[noteCount] = ofs;
					ofs = getChunkShort();
					noteCount++;
				}
				// take care of the notes
				game.noteCount = noteCount-1;
				game.notes = new String[MAX_NOTES];
				for (int i=0; i<noteCount; i++) {
					index = noffsets[i];
					String s = getChunkAsciiZ();
					switch (i) {
					case 0:
						// first note is the author name
						s = s.substring(2);
						game.author = s;
						break;
					default:
						// all other notes are real notes
						game.notes[i-1] = s;
					}
				}
				break;
			default:
				// all other chunks are level chunks
				// create a new level
				Level level = new Level();
				// and set it's properties
				level.name = getChunkString(32);
				level.adjustWidth(chunk[index]);
				level.adjustHeight(chunk[index+1]);
				index+=2;
				// and read the cells
				for (int y=0; y<level.height; y++) {
					for (int x=0; x<level.width; x++) {
						int place = getChunkShort();
						level.cells[x][y] = new Cell(place & 0xff,place >> 8).toMulgEd();
					}
				}				
				game.levels[chk-2] = level;
			}
		}
		// A lot of garbage was created. make sure it's cleaned now,
		// when the user is willing to wait.
		System.gc();
		return game;
	}
	
	// Methods used when saving a PDB file.
	// mostly the same as their ReadXXXX brother methods
	protected void writeInt(int i) {
		content[index] = (byte)((i >> 24) & 0xff);
		content[index+1] = (byte)((i >> 16) & 0xff);
		content[index+2] = (byte)((i >> 8) & 0xff);
		content[index+3] = (byte)(i & 0xff);
		index+=4;
	}
	
	protected void writeChunkInt(int i) {
		chunk[index] = (byte)((i >> 24) & 0xff);
		chunk[index+1] = (byte)((i >> 16) & 0xff);
		chunk[index+2] = (byte)((i >> 8) & 0xff);
		chunk[index+3] = (byte)(i & 0xff);
		index+=4;
	}
	
	protected void writeShort(int i) {
		content[index] = (byte)((i >> 8) & 0xff);
		content[index+1] = (byte)(i & 0xff);
		index+=2;
	}
	
	protected void writeChunkShort(int i) {
		chunk[index] = (byte)((i >> 8) & 0xff);
		chunk[index+1] = (byte)(i & 0xff);
		index+=2;
	}
	
	protected void writeString(String s, int max) {
		if (s.length() > max) s = s.substring(0,max);
		System.arraycopy(s.getBytes(),0,content,index,s.length());
		for (int i = s.length(); i<max; i++) 
			content[index+i] = 0;
		index+=max;
	}
	
	protected void writeChunkString(String s, int max) {
		System.arraycopy(s.getBytes(),0,chunk,index,s.length());
		for (int i = s.length(); i<max; i++) 
			chunk[index+i] = 0;
		index+=max;
	}
	
	protected void writeChunk(int size) {
		System.arraycopy(chunk,0,content,index,size);
		index+=size;
	}
	
	// save this game to a PDB file
	public void saveToFile(String filename) throws IOException {
		// create the buffers
		content = new byte[65536];
		chunk = new byte[4096];
		index = 0;
		// add the "(D)" if debug
		if (debug)
			writeString(name+" (D)",32);
		else 
			writeString(name,32);
		// write PDB header
		// attributes
		writeShort(0);
		// version: 00 02
		writeShort(2);
		writeInt(creationDate);
		writeInt(modificationDate);
		// lastbackup
		writeInt(0);
		writeInt(modificationCount);
		// app info
		writeInt(0);
		// sort info
		writeInt(0);
		
//		writeInt(0x4c657646);// "levF"
		writeInt(0x4c65764E); // "levN"
		// "Mulg"
		writeInt(0x4d756c67);
		// two Ints ???
		writeInt(0);
		writeInt(0);
		// write chunks one by one
		writeShort(levelCount+2);
		int offsetsIndex = index;
		int chunksIndex = index+(8*(levelCount+2));
		for (int chk = 0; chk<(levelCount+2); chk++) {
			index = offsetsIndex+(8*chk);
			writeInt(chunksIndex);
			// ???
			writeInt(0x60000000);
			index = 0;
			switch (chk) {
			case 0:
				// first chunk if the game info.
				writeChunkShort(0);
				// write debug flag
				if (debug) 
					writeChunkShort(0xffff);
				else 
					writeChunkShort(0);
				// other stuff is only FFs ...
				for(int i=0;i<16;i++) {
					writeChunkInt(0xffffffff);
				}
				break;
			case 1:
				// second chunk is author name and notes
				String s;
				int notesIndex = (noteCount+1)*2;
				// write notes one by one
				for(int nt = 0; nt<noteCount+1; nt++) {
					index = nt*2;
					writeChunkShort(notesIndex);
					switch (nt) {
					case 0:
						// first note if author name
						// add the string terminator of the offsets (0x3230)
						s = "20"+author;
						index = notesIndex;
						writeChunkString(s,s.length()+1);
						break;
					default:
						// other notes are real notes.
						s = notes[nt-1];
						index = notesIndex;
						writeChunkString(s,s.length()+1);			
					}
					notesIndex=index;
				}				
				break;
			default:
				// Other chunks are levels.
				Level level = levels[chk-2];
				// write the level properties
				writeChunkString(level.name,32);
				int dim = (((int)level.width) & 0xff) << 8;
				dim |= ((int)level.height) & 0xff;
				writeChunkShort(dim);
				// write the level tiles
				for(int y = 0; y<level.height; y++) {
					for (int x=0; x<level.width; x++) {
						int place = (((int)level.cells[x][y].toFile().tile) & 0xff);
						place |= (((int)level.cells[x][y].toFile().data) & 0xff)<<8;
						writeChunkShort(place);
					}
				}
			}
			int chunkSize = index;
			index = chunksIndex;
			writeChunk(chunkSize);
			chunksIndex += chunkSize;
		}
		
		int total = index;
		// create the file
		File f = new File(filename);
		// prepare the output string
		FileOutputStream out = new FileOutputStream(f);
		// write to file
		out.write(content,0,total);
		out.flush();
		out.close();
		// A lot of garbase was created. Make sure it's collected now,
		// while the user is willing to wait.
		System.gc();
	}
	
	// returns the current time, in the PDB's format (palm date)
	public static int now() {
		return java2palmDate(new Date());
	}
	
	// The java date is milliseconds since 1970
	// The palm date is seconds since 1903
	// So basically we need two conversion methods.
	
	// translates a palm date to a java date value
	public static long palm2javaDate(int palmDate) {
		int p = palmDate;
		long i =0;
		p -= 2082844800;
		i |= p;
		i *= 1000;
		return new Date(i).getTime();
	}
	
	// translates a java date to a palm date value
	public static int java2palmDate(long javaDate) {
		long i = javaDate;
		i /= 1000;
		i += 2082844800;
		return (int)i;
	}
	
	public static int java2palmDate(Date javaDate) {
		long i = javaDate.getTime();
		i /= 1000;
		i += 2082844800;
		return (int)i;
	}
	
	// Export this game to a LEV file.
	void exportToFile(String filename) throws IOException{
		// create the file.
		File f = new File(filename);
		if (f.exists()) f.delete();
		// prepare the output stream, as a writer (for strings)
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename));
		// write the headers.
		out.write("// "+filename+" - Mulg Level Data\r\n");
		out.write("// Exported by Mulg Editor V2.01\r\n");
		out.write("// NOTE: you must have tiles.h and level.h that come with MulgIIh or later\r\n");
		out.write("//       when compiling this level file.\r\n");
		out.write("\r\n");
		out.write("#include \"tiles.h\"   // tile definitions from conv.c\r\n");
		out.write("#include \"level.h\"   // shortcuts and the like\r\n");
		out.write("\r\n");
		// make sure we uncomment the debug as necessary.
		if (!debug)
			out.write("//");
		out.write("dbdebug  // create debug version\r\n");
		out.write("\r\n");
		out.write("dbname \""+name+"\"\r\n");
		out.write("dbauthor \""+author+"\"\r\n");
		out.write("\r\n");
		
		// now we export the notes, so all levels can use them.
		for (int i=0; i<noteCount; i++) {
			out.write("doc "+String.valueOf(i+1)+" \""+notes[i]+"\"\r\n");
		}
		
		// now we export the levels, tile by tile.
		for (int i=0; i<levelCount; i++) {
			out.write("\r\n");
			out.write("level \""+levels[i].name+"\" {\r\n");
			String[][] names = new String[levels[i].width][levels[i].height];
			// calculate the maximum width for each column, and prepare the strings.
			for (int x=0; x<levels[i].width; x++) {
				int max = 0;
				for (int y=0; y<levels[i].height; y++) {
					names[x][y] = levels[i].cells[x][y].toLevString();
					if (names[x][y].length() > max) max = names[x][y].length();
				}
				max++;
				for (int y=0; y<levels[i].height; y++) {
					while (names[x][y].length() < max)
						names[x][y] += " ";
				}
				
			}
			// now write the strings
			for (int y=0; y<levels[i].height; y++) {
				out.write("  ");
				for (int x=0; x<levels[i].width; x++) {
					out.write(names[x][y]);
				}
				out.write("\r\n");
			}
			out.write("}\r\n");
		}
		out.close();
	}
	
	// Ahhhhh
	// Now we get to the real masterpiece.
	// This is the game import mechanism
	// Stay focused, it's confusing.
	
	// A hashtable for all defined IDs, and their values
	static Hashtable ids;
	
	// returns the ID in a string that's immediately before a string.
	static String before(String s,String c) {
		s = s.substring(0,s.indexOf(c)).trim();
		if (s.indexOf(" ") != -1) s = s.substring(s.indexOf(" ")+1);
		else if (s.indexOf("*") != -1) s = s.substring(s.indexOf("*")+1);
		else if (s.indexOf("/") != -1) s = s.substring(s.indexOf("/")+1);
		else if (s.indexOf("+") != -1) s = s.substring(s.indexOf("+")+1);
		else if (s.indexOf("-") != -1) s = s.substring(s.indexOf("-")+1);
		else if (s.indexOf("|") != -1) s = s.substring(s.indexOf("|")+1);
		else if (s.indexOf("&") != -1) s = s.substring(s.indexOf("&")+1);
		else if (s.indexOf("^") != -1) s = s.substring(s.indexOf("^")+1);
		else if (s.indexOf("~") != -1) s = s.substring(s.indexOf("~")+1);
		return s;
	}
	
	// returns the ID in a string that's immediately after a string
	static String after(String s,String c) {
		s = s.substring(s.indexOf(c)+c.length()).trim();
		if (s.indexOf(" ") != -1) s = s.substring(0,s.indexOf(" "));
		else if (s.indexOf("*") != -1) s = s.substring(0,s.indexOf("*"));
		else if (s.indexOf("/") != -1) s = s.substring(0,s.indexOf("/"));
		else if (s.indexOf("+") != -1) s = s.substring(0,s.indexOf("+"));
		else if (s.indexOf("-") != -1) s = s.substring(0,s.indexOf("-"));
		else if (s.indexOf("|") != -1) s = s.substring(0,s.indexOf("|"));
		else if (s.indexOf("&") != -1) s = s.substring(0,s.indexOf("&"));
		else if (s.indexOf("^") != -1) s = s.substring(0,s.indexOf("^"));
		else if (s.indexOf("~") != -1) s = s.substring(0,s.indexOf("~"));
		return s;
	}
	
	// replaces a part of a string with another string
	static String replace(String s, String c, String i) {
		int bef = s.indexOf(c)-1;
		while ((bef > 0) && (s.charAt(bef) == ' '))
			bef--;
		while ((bef > 0) && (s.charAt(bef) != ' ') && 
			   (s.charAt(bef) != '*') && 
			   (s.charAt(bef) != '/') && 
			   (s.charAt(bef) != '+') && 
			   (s.charAt(bef) != '-') && 
			   (s.charAt(bef) != '|') && 
			   (s.charAt(bef) != '&') && 
			   (s.charAt(bef) != '^') && 
			   (s.charAt(bef) != '~'))
			bef--;
		int aft = s.indexOf(c)+c.length();
		while ((aft < s.length()) && (s.charAt(aft) == ' '))
			aft++;
		while ((aft < s.length()) && (s.charAt(aft) != ' ') && 
			   (s.charAt(aft) != '*') && 
			   (s.charAt(aft) != '/') && 
			   (s.charAt(aft) != '+') && 
			   (s.charAt(aft) != '-') && 
			   (s.charAt(aft) != '|') && 
			   (s.charAt(aft) != '&') && 
			   (s.charAt(aft) != '^') && 
			   (s.charAt(aft) != '~'))
			aft++;
		aft++;
		String saft = "";
		if (aft < s.length()) saft = s.substring(aft);
		return s.substring(0,bef)+i+saft;
	}
	
	// calculates an expression, and returns the result.
	// This recoursive method, computes whatever is needed, and uses all defined IDs.
	static String calculate(String s) throws NumberFormatException {
		s = s.trim();
		try {
			// try to translate a regular number
			int i;
			if (s.startsWith("0x")) {
				String hx = s.substring(2);
				i = Integer.valueOf(hx,16).intValue();
			} else {
				i = Integer.valueOf(s).intValue();
			}
			return String.valueOf(i);
		} catch (NumberFormatException nfe) {
			// not a simple number - translate otherwise.
			// try parenthesis first.
			if (s.indexOf(")") != -1) {
				String par = s.substring(0,s.indexOf(")"));
				par = par.substring(par.lastIndexOf("(")+1);
				String sbef = s.substring(0,s.lastIndexOf("("));
				String saft = "";
				if (s.indexOf(")")+1 < s.length())
					saft = s.substring(s.indexOf(")")+1);
				s = sbef+calculate(par)+saft;
				return calculate(s);
			} else if (s.indexOf("*")!= -1) {
				// compute multiplication
				int bef = Integer.valueOf(calculate(before(s,"*").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,"*").trim())).intValue();
				return calculate(replace(s,"*",String.valueOf(bef*aft)));
			} else if (s.indexOf("/")!= -1) {
				// compute division
				int bef = Integer.valueOf(calculate(before(s,"/").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,"/").trim())).intValue();
				return calculate(replace(s,"/",String.valueOf(bef/aft)));
			} else if (s.indexOf("+")!= -1) {
				// compute addition
				int bef = Integer.valueOf(calculate(before(s,"+").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,"+").trim())).intValue();
				return calculate(replace(s,"+",String.valueOf(bef+aft)));
			} else if (s.indexOf("-")!= -1) {
				// compute substraction
				int bef = Integer.valueOf(calculate(before(s,"-").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,"-").trim())).intValue();
				return calculate(replace(s,"-",String.valueOf(bef-aft)));
			} else if (s.indexOf("|")!= -1) {
				// compute bitwise OR
				int bef = Integer.valueOf(calculate(before(s,"|")).trim()).intValue();
				int aft = Integer.valueOf(calculate(after(s,"|")).trim()).intValue();
				return calculate(replace(s,"|",String.valueOf(bef|aft)));
			} else if (s.indexOf("&")!= -1) {
				// compute bitwise AND
				int bef = Integer.valueOf(calculate(before(s,"&")).trim()).intValue();
				int aft = Integer.valueOf(calculate(after(s,"&")).trim()).intValue();
				return calculate(replace(s,"&",String.valueOf(bef&aft)));
			} else if (s.indexOf("^")!= -1) {
				// compute bitwise NOT
				int bef = Integer.valueOf(calculate(before(s,"^").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,"^").trim())).intValue();
				return calculate(replace(s,"^",String.valueOf(bef^aft)));
			} else if (s.indexOf("<<")!= -1) {
				// compute bitwise shift left
				String sbef = calculate(before(s,"<<").trim());
				int bef = Integer.valueOf(sbef).intValue();
				String saft = calculate(after(s,"<<").trim());
				int aft = Integer.valueOf(saft).intValue();
				return calculate(replace(s,"<<",String.valueOf(bef<<aft)));
			} else if (s.indexOf(">>")!= -1) {
				// compute bitwise shift right
				int bef = Integer.valueOf(calculate(before(s,">>").trim())).intValue();
				int aft = Integer.valueOf(calculate(after(s,">>").trim())).intValue();
				return calculate(replace(s,">>",String.valueOf(bef>>aft)));
			} else if (s.indexOf("~")!= -1) {
				// compute bitwise XOR
				int aft = Integer.valueOf(calculate(after(s,"~").trim())).intValue();
				return calculate(replace(s,"~",String.valueOf(~aft)));
			} else if (ids.containsKey(s)) {
				// try to translate a key
				String r = (String)ids.get(s);
				return r;
			} else if (ids.containsKey("ATTRIB_"+s)) {
				// try the ATTRIB_xxx values
				String r = (String)ids.get("ATTRIB_"+s);
				return r;
			} else throw new NumberFormatException("Invalid symbol \""+s+"\"");
		}
	}
	
	// Includes an .h file in our computation.
	static void includeFile(String filename) throws IOException {
		// .h files contain only #define lines
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(filename));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot load include file "+filename,"Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			throw new IOException();
		}
		Game game = new Game();
		String line = "";
		line = in.readLine();
		int i=1;
		// parse the line, and add the ID.
		try {
			while (line != null) {
				line = line.trim();
				if (!line.startsWith("//") && !line.equals("")) {
					if (line.startsWith("#define")) {
						line = line.substring(line.indexOf(" ")+1);
						String id = line.substring(0,line.indexOf(" "));
						line = line.substring(line.indexOf(" ")+1);
						if (line.indexOf("//") != -1)
							line = line.substring(0,line.indexOf("//"));
						String value = calculate(line.trim());
						ids.put(id,value);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid line No. "+i+" in include file "+filename,"Invalid line", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
						in.close();
						return;
					}
				}
				line = in.readLine();
				i++;
			}
		} catch (NumberFormatException nfe) {
			throw new NumberFormatException("Invalid line No. "+i+" in include file "+filename+": "+nfe.getMessage());
		}
		in.close();
	}

	// splits a string into two strings	like a tokenizer
	static int split(String s, String[] r) {
		s = s.trim();
		int i = 0;
		while (s.length() > 0) {
			String t;
			if (s.indexOf(" ") != -1)
				t = s.substring(0,s.indexOf(" "));
			else t = s;
			r[i++] = t;
			if (s.indexOf(" ") != -1)
				s = s.substring(s.indexOf(" ")).trim();
			else s = "";
		}
		return i;
	}
	
	// reads a level from the LEV file
	static Level readLevel(BufferedReader in, String levName) throws IOException{
		String line = in.readLine();
		Level level = new Level();
		String[] names = new String[Level.MAX_WIDTH+1];
		int y=0;
		try {
			while (line.indexOf("}") == -1) {
				y++;
				int n = split(line,names);
				if (level.width < n) level.width = n;
				if (level.height < y) level.height = y;
				for (int x=0; x<n; x++) {
					String sTile = names[x];
					String sData = "0";
					if (sTile.indexOf(".") != -1) {
						sData = sTile.substring(sTile.indexOf(".")+1);
						sTile = sTile.substring(0,sTile.indexOf("."));
					}
					int tile = Integer.valueOf(calculate(sTile)).intValue();
					int data = Integer.valueOf(calculate(sData)).intValue();
					if (tile >= 256) {
						data |= (tile >> 8);
						tile &= 0xff;
					}
					level.cells[x][y-1] = new Cell(tile,data).toMulgEd();
				}
				line = in.readLine();
			}
		} catch (NumberFormatException nfe) {
			throw new NumberFormatException("Error in level \""+levName+"\": "+nfe.getMessage());
		}
		return level;
	}
	
	// Reads a LEV file into a Game object
	static Game importFromFile(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		Game game = new Game();
		String line = in.readLine().trim();
		ids = new Hashtable();
		try {
			// parse all types of recognizable lines
			while (line != null) {
				if (!line.startsWith("//") && !line.equals("")) {
					String firstWord = line.substring(0,line.indexOf(" "));
					if (firstWord.equalsIgnoreCase("#include")) {
						String ifilename = line.substring(line.indexOf("\"")+1);
						ifilename = ifilename.substring(0,ifilename.indexOf("\""));
						includeFile(ifilename);
					} else if (firstWord.equalsIgnoreCase("#define")) {
						line = line.substring(line.indexOf(" ")+1);
						String id = line.substring(0,line.indexOf(" "));
						line = line.substring(line.indexOf(" ")+1);
						if (line.indexOf("//") != -1)
							line = line.substring(0,line.indexOf("//"));
						String value = calculate(line.trim());
						ids.put(id,value);
					} else  if (firstWord.equalsIgnoreCase("dbname")) {
						String name = line.substring(line.indexOf("\"")+1);
						name = name.substring(0,name.indexOf("\""));
						game.name = name;					
					} else  if (firstWord.equalsIgnoreCase("dbauthor")) {
						String name = line.substring(line.indexOf("\"")+1);
						name = name.substring(0,name.indexOf("\""));
						game.author = name;					
					} else  if (firstWord.equalsIgnoreCase("level")) {
						String name = line.substring(line.indexOf("\"")+1);
						name = name.substring(0,name.indexOf("\""));
						game.levelCount++;
						game.levels[game.levelCount-1] = readLevel(in, name);
						game.levels[game.levelCount-1].name = name;
					} else  if (firstWord.equalsIgnoreCase("doc")) {
						String number = line.substring(line.indexOf(" ")+1);
						number = number.substring(0,number.indexOf(" "));
						String name = line.substring(line.indexOf("\"")+1);
						name = name.substring(0,name.indexOf("\""));
						int num = Integer.valueOf(number).intValue();
						game.notes[num-1] = name;
						if (game.noteCount < num) game.noteCount = num;
					}
				}
				line = in.readLine();
				if (line != null) line = line.trim();
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, nfe.getMessage(),"Invalid line", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			return null;
		}
		in.close();
		ids = null;
		return game;
	}
}
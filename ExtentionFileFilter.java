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
import javax.swing.filechooser.*;
import java.io.File;

/**
 *	class ExtentionFileFilter
 *	This class is a FileFilter (for JFileDialog), that filters files with
 *	one specific extention, and all directories.
 **/

public class ExtentionFileFilter extends FileFilter {
	
	String description;
	String extention;
	
	public ExtentionFileFilter(String extention, String description) {
		this.extention = extention;
		this.description = description;
	}
	
	public boolean accept(File f) {
		if (extention != null) 
			return (f.isDirectory()) || (f.getName().endsWith("."+extention));
		else 
			return true;
	}
	
	public String getDescription() {
		return description;
	}
}
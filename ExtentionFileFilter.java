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
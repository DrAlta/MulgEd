import javax.swing.*;
import java.io.*;

public class ImageLoader implements Runnable {
	
	String directory;
	JProgressBar p;
	LevelEditor le;
	GameEditor ged;
	public static final String separator = System.getProperty("file.separator","\\");
	
	public ImageLoader(GameEditor ged, LevelEditor le, String directory, JProgressBar p, boolean background) {
		this.le = le;
		this.p = p;
		this.directory = directory;
		this.ged = ged;
		ged.setEnabled(false);
		if (background)
			new Thread(this).start();
		else
			run();
	}
	
	public void run() {
		int maxper = le.MAX_TILES+le.MAX_STILES;
		int per = 0;
		for (int i=0; i<le.MAX_TILES; i++) {
			per = (100*i) / maxper;
			if (p != null) {
				p.setValue(per);
			}
			String name = directory+separator+"tile"+le.int2str(i)+".gif";
			File f = new File(name);
			String sPath = f.getAbsolutePath();
			if (!new File(name).canRead()) {
				JOptionPane.showMessageDialog(ged,"File "+name+" cannot be read.\nMulgEd cannot run without this file.","Error reading file",JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			le.tiles[i] = new ImageIcon(name).getImage();
		}		
		for (int i=0; i<le.MAX_STILES; i++) {
			per = (100*(i+le.MAX_TILES)) / maxper;
			if (p != null) {
				p.setValue(per);
			}
			String name = directory+separator+"tile1"+le.int2str(i)+".gif";
			if (!new File(name).canRead()) {
				JOptionPane.showMessageDialog(ged,"File "+name+" cannot be read.\nMulgEd cannot run without this file.","Error reading file",JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			le.stiles[i] = new ImageIcon(name).getImage();
		}
		le.belowHandler.prepare();
		p.setValue(0);
		ged.setEnabled(true);
	}
}
import javax.swing.*;
import java.awt.*;
import java.lang.Thread;

public class Main {
	static GameEditor g;
	static java.awt.Button button = new java.awt.Button();
		
	public static void main(String[] args) {
	    LoadingDlg dlg = new LoadingDlg();
	    dlg.show();
	    try {
	        java.lang.Thread.sleep(100);
	    } catch (InterruptedException e)
	    {}
		g = new GameEditor();
		g.setFonts(g);
		g.setMenuFonts();
		g.setLocation();
		dlg.hide();
		g.show();
	}
}
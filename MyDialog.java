import javax.swing.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Dimension;

public class MyDialog extends JDialog implements WindowListener, ImageObserver{
	
	GridBagLayout layout;
	Container contentPane;
	Font font;
	int result;
	boolean fontsSet = false;
	boolean centered = false;
	
	static final int YES = 0;
	static final int NO = 1;
	static final int CANCEL = 2;
	static final int OK = 3;
	
	public MyDialog() {
	}
	
	public MyDialog(JDialog father, String text, boolean modal) {
		super(father, text, modal);
		contentPane = getContentPane();
		layout = new GridBagLayout();
		contentPane.setLayout(layout);
		setResizable(false);
		addWindowListener(this);
		font = new Font("Serif",Font.PLAIN, 11);
//		font = new Font("SansSerif",Font.ITALIC, 11);
		setFont(font);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setForeground(Color.black);
	}
	
	public MyDialog(String text, boolean modal) {
		super();
		contentPane = getContentPane();
		layout = new GridBagLayout();
		contentPane.setLayout(layout);
		setModal(modal);
		setTitle(text);
		setResizable(false);
		addWindowListener(this);
		font = new Font("Serif",Font.PLAIN, 11);
//		font = new Font("SansSerif",Font.ITALIC, 11);
		setFont(font);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setForeground(Color.black);
	}
	
	public void windowActivated(WindowEvent e) {
		if (!centered) {
			setLocation();
			centered = true;
		}
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowClosing(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {
	}
	public void windowOpened(WindowEvent e) {
	}
	
	public void process(int message,int x,int y,int z) {
	}
	
	public int showDlg() {
		if (!fontsSet) {
			setMenuFonts();
			setFonts(this);
			fontsSet = true;
		}
		show();
		return result;
	}
	
	public void setLocation() {
		Dimension d = getToolkit().getScreenSize();
		Dimension e = getSize();
		setLocation((d.width-e.width)/2,(d.height-e.height)/2);
	}
	
	void paneadd(JComponent c) {
		contentPane.add(c);
	}
	
	void setFonts(Container c) {
		Component[] cps = c.getComponents();
		for (int i=0; i<cps.length; i++) {
			Component cp = cps[i];
			cp.setFont(font);
			if (cp instanceof Container) 
				setFonts((Container)cp);
		}
	}
	
	void setMenuFonts() {
		JMenuBar mb;
		mb = getJMenuBar();
		if (mb != null) {
			for (int i=0; i<mb.getMenuCount(); i++) {
				setMenuFonts(mb.getMenu(i));
			}
		}
	}
	
	void setMenuFonts(JMenuItem mi) {
		mi.setFont(font);
		if (mi instanceof JMenu) {
			JMenu m = (JMenu)mi;
			for (int i=0; i<m.getItemCount(); i++) {
				Object o = m.getItem(i);
				if (o instanceof JMenuItem)
					setMenuFonts((JMenuItem)o);
			}
		}
	}
	
	public boolean imageUpdate(Image i, int info, int x, int y, int w, int h) {
		return false;
	}
	
}
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Dimension;
import java.awt.event.*;

public class ListBox extends JList implements ListSelectionListener, KeyListener, MouseListener, MouseMotionListener {
	
	DefaultListModel model;
	Dimension d = null;
	MyDialog o;
	int selectmsg, dbclickmsg, entermsg, insertmsg, deletemsg;
	int mouseupmsg, mousedownmsg, mousemovemsg, mouseleavemsg;
	
	public ListBox(MyDialog o, int selectmsg, int dbclickmsg, int entermsg, int insertmsg, int deletemsg, int mouseupmsg, int mousedownmsg, int mousemovemsg, int mouseleavemsg, int sx, int sy) {
		super(new DefaultListModel());
		this.o = o;
		this.selectmsg = selectmsg;
		this.dbclickmsg = dbclickmsg;
		this.entermsg = entermsg;
		this.insertmsg = insertmsg;
		this.deletemsg = deletemsg;
		this.mouseupmsg = mouseupmsg;
		this.mousedownmsg = mousedownmsg;
		this.mousemovemsg = mousemovemsg;
		this.mouseleavemsg = mouseleavemsg;
		addListSelectionListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		model = (DefaultListModel)getModel();
		d = new Dimension(sx, sy);
	}
	
	public ListBox(MyDialog o, int selectmsg, int dbclickmsg, int entermsg, int insertmsg, int deletemsg, int mouseupmsg, int mousedownmsg, int mousemovemsg, int mouseleavemsg) {
		super(new DefaultListModel());
		this.o = o;
		this.selectmsg = selectmsg;
		this.dbclickmsg = dbclickmsg;
		this.entermsg = entermsg;
		this.insertmsg = insertmsg;
		this.deletemsg = deletemsg;
		this.mouseupmsg = mouseupmsg;
		this.mousedownmsg = mousedownmsg;
		this.mousemovemsg = mousemovemsg;
		this.mouseleavemsg = mouseleavemsg;
		addListSelectionListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		model = (DefaultListModel)getModel();
	}
	
	public Dimension getPreferredSize() {
		if (d != null) 
			return d;
		else 
			return super.getPreferredSize();
	}
	
	public Dimension getMinimumSize() {
		if (d != null) 
			return d;
		else 
			return super.getMinimumSize();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		o.process(selectmsg,0,0,0);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1) {
			o.process(dbclickmsg,0,0,0);
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
		o.process(mouseleavemsg,0,0,0);
	}
	public void mousePressed(MouseEvent e) {
		if ( (e.getClickCount() == 1) && ( (e.getModifiers() & e.BUTTON1_MASK)!=0) )
			o.process(mousedownmsg,0,0,0);
	}
	public void mouseReleased(MouseEvent e) {
		o.process(mouseupmsg,0,0,0);
	}
	
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				o.process(entermsg,0,0,0);
				break;
			case KeyEvent.VK_INSERT:
				o.process(insertmsg,0,0,0);
				break;
			case KeyEvent.VK_DELETE:
				o.process(deletemsg,0,0,0);
				break;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		o.process(mousemovemsg,e.getX(),e.getY(),0);
	}
	public void mouseMoved(MouseEvent e) {
		o.process(mousemovemsg,e.getX(),e.getY(),0);
	}
	
}
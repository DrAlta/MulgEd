import javax.swing.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Component;

public class LoadingDlg extends MyDialog {

    public LoadingDlg()
    {
    	super("MulgEd", false);
		setSize(200,100);
		JPanel panel = new JPanel(new BorderLayout());
		BlackLabel bl = new BlackLabel("Loading. Please wait...");
		panel.add(bl);
		paneadd(panel);
    }
}

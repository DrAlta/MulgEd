import java.awt.*;
import javax.swing.*;

public class MessageBox {
	
	public static void showErrorMessage(MyDialog parent, String text, String title) {
		JOptionPane.showMessageDialog(parent,text,title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showYesNoMessage(MyDialog parent, String text, String title) {
		return JOptionPane.showConfirmDialog(parent,text,title, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
	public static int showYesNoCancelMessage(MyDialog parent, String text, String title) {
		return JOptionPane.showConfirmDialog(parent,text,title, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
}
package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class MyPopUp extends JFrame {

	public MyPopUp() {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height/4;
		int screenWidth = screenSize.width/4;
		Dimension d= new Dimension(screenWidth,screenHeight);
		setSize(d);
		setLocationRelativeTo(null);
		
	}
	
}

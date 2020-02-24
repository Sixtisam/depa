package jcolor.awt;

import java.awt.event.*;

class ColorExitListener extends WindowAdapter implements ActionListener {
	@Override
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
}
// Modul Design Patterns, Uebung 1, ColorPicker Applikation
// Autor: D. Gruntz
package jcolor.awt;

import java.awt.*;

import jcolor.ColorModel;

public class ColorApplication extends Frame {
		
	public static void main (String[] args){
		new ColorApplication().setVisible(true);
	}
		
	private ColorModel model = new ColorModel();

	ColorApplication(){
		setTitle("Color Picker AWT");
		setBackground(Color.lightGray);
		
		Color color = Color.black;

		this.addWindowListener(new ColorExitListener());
		
		setLayout(new BorderLayout());
		Panel top = new Panel(new GridLayout(1, 2, 5, 5));
		this.add("North", top);
		Panel bottom = new Panel(new FlowLayout());		
		this.add(bottom);
		Panel p;
		
		// Scrollbar panel
		p = new Panel(new GridLayout(3,1,3,3));
		top.add(p);
		ColorScrollbar sr = new ColorScrollbar(model, ColorScrollbar.RED,   Scrollbar.HORIZONTAL, color.getRed());
		ColorScrollbar sg = new ColorScrollbar(model, ColorScrollbar.GREEN, Scrollbar.HORIZONTAL, color.getGreen());
		ColorScrollbar sb = new ColorScrollbar(model, ColorScrollbar.BLUE,  Scrollbar.HORIZONTAL, color.getBlue());
		p.add(sr);
		p.add(sg);
		p.add(sb);
		
		// Textfield panel
		p = new Panel(new GridLayout(3, 1));
		top.add(p);
		ColorTextField tr = new ColorTextField(model, ColorTextField.RED,   5);
		ColorTextField tg = new ColorTextField(model, ColorTextField.GREEN, 5);
		ColorTextField tb = new ColorTextField(model, ColorTextField.BLUE,  5);
		p.add(tr);
		p.add(tg);
		p.add(tb);
		
		ColorCanvas canvas = new ColorCanvas(model, color);
		bottom.add(canvas);
		
		final int nRadioButtons = 5;
		p = new Panel(new GridLayout(nRadioButtons,1));
		bottom.add(p);
		ColorCheckBox[] colors = new ColorCheckBox[nRadioButtons];
		colors[0] = new ColorCheckBox(model, "red",    Color.red); 
		colors[1] = new ColorCheckBox(model, "blue",   Color.blue);
		colors[2] = new ColorCheckBox(model, "green",  Color.green);
		colors[3] = new ColorCheckBox(model, "yellow", Color.yellow);
		colors[4] = new ColorCheckBox(model, "cyan",   Color.cyan);
	
		for(int i = 0; i<nRadioButtons; i++) {
			p.add(colors[i]);
		}
		
		p = new Panel(new GridLayout(2, 1, 5, 5));
		ColorButton b;
		b = new ColorButton(model, ColorButton.DARKER, "Darker");
		p.add(b);
		b = new ColorButton(model, ColorButton.BRIGHTER, "Brighter");
		p.add(b);
		bottom.add(p);
	
		MenuBar bar = new MenuBar();
		setMenuBar(bar);
		
		Menu file = new Menu("File");
		bar.add(file);
		
		MenuItem exit = new MenuItem("Exit");
		file.add(exit);
		exit.addActionListener(new ColorExitListener());
		
		Menu attr = new Menu("Attributes");
		bar.add(attr);
		ColorMenuItem m;
		m = new ColorMenuItem(model, "red",    Color.red);    attr.add(m);
		m = new ColorMenuItem(model, "blue",   Color.blue);   attr.add(m);
		m = new ColorMenuItem(model, "green",  Color.green);  attr.add(m);
		m = new ColorMenuItem(model, "cyan",   Color.cyan);   attr.add(m);
		m = new ColorMenuItem(model, "pink",   Color.pink);   attr.add(m);
		m = new ColorMenuItem(model, "orange", Color.orange); attr.add(m);
		
		model.setColor(color);
		pack();
	}

}
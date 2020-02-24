package jcolor.awt;

import java.awt.*;
import java.awt.event.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorTextField extends TextField implements TextListener, ColorListener {
	static final int RED   = 0;
	static final int GREEN = 1;
	static final int BLUE  = 2;
	
	ColorModel model;
	int type;
	
	ColorTextField(ColorModel model, int type, int n){
		super("", n);
		this.model = model;
		this.type  = type;
		addTextListener(this);
		model.addColorListener(this);
	}
	
	@Override
	public void setText(String s){
		if(!s.equals(this.getText()))
			super.setText(s);
	}

	@Override
	public void textValueChanged(TextEvent e){
		try{
			//int n = Integer.parseInt(((TextField)e.getSource()).getText());
			int n = Integer.parseInt(getText());
			if(n >= 0 && n < 256){
				Color c = model.getColor();
				switch(type){
					case RED:   c = new Color(n, c.getGreen(), c.getBlue()); break;
					case GREEN: c = new Color(c.getRed(), n, c.getBlue()); break;
					case BLUE:  c = new Color(c.getRed(), c.getGreen(), n); break;
				}
				model.setColor(c);
			}
		}
		catch(Exception  x){}
	}

	@Override
	public void colorValueChanged(Color color){
		switch(type){
			case RED:   setText("" + color.getRed());   break;
			case GREEN: setText("" + color.getGreen()); break;
			case BLUE:  setText("" + color.getBlue());  break;
		}
	}
}
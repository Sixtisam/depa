package jcolor.awt;

import java.awt.*;
import java.awt.event.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorScrollbar extends Scrollbar implements AdjustmentListener, ColorListener {
	static final int RED   = 0;
	static final int GREEN = 1;
	static final int BLUE  = 2;
	
	ColorModel model;
	int type;
	
	ColorScrollbar(ColorModel model, int type, int orientation, int val){
		super(orientation, val, 1, 0, 256);
		this.model = model;
		this.type  = type;
		switch(type){
			case RED:   setBackground(Color.red);   break;
			case GREEN: setBackground(Color.green); break;
			case BLUE:  setBackground(Color.blue);  break;
		}
		addAdjustmentListener(this);
		model.addColorListener(this);
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e){
		Color c = model.getColor();
		switch(type){
			case RED:   c = new Color(getValue(), c.getGreen(), c.getBlue()); break;
			case GREEN: c = new Color(c.getRed(), getValue(), c.getBlue()); break;
			case BLUE:  c = new Color(c.getRed(), c.getGreen(), getValue()); break;
		}
		model.setColor(c);
	}
		
	@Override
	public void colorValueChanged(Color color){
		switch(type){
			case RED:   setValue(color.getRed());   break;
			case GREEN: setValue(color.getGreen()); break;
			case BLUE:  setValue(color.getBlue());  break;
		}
	}
}

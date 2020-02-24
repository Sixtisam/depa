package jcolor.awt;

import java.awt.*;
import java.awt.event.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorMenuItem extends CheckboxMenuItem implements ItemListener, ColorListener {
	ColorModel model;
	Color color;
	
	ColorMenuItem (ColorModel model, String label, Color color){
		super(label);
		this.model = model;
		this.color = color;
		addItemListener(this);
		model.addColorListener(this);
	}
	
	@Override
	public void colorValueChanged(Color c){
		setState(c.equals(this.color));
	}
	
	@Override
	public void itemStateChanged(ItemEvent e){
		model.setColor(color);
	}

}
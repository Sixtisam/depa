package jcolor.awt;

import java.awt.*;
import java.awt.event.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorButton extends Button implements ColorListener, ActionListener {
	static final int BRIGHTER = 0;
	static final int DARKER   = 1;
	
	ColorModel model;
	int type;
	
	ColorButton(ColorModel model, int type, String label){
		super(label);
		
		this.model = model;		
		this.type  = type;
		addActionListener(this);
		model.addColorListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Color c = model.getColor();
		
		switch(type) {
			case BRIGHTER: model.setColor(c.brighter()); break;
			case DARKER:   model.setColor(c.darker()); break;
		}
	}
	
	@Override
	public void colorValueChanged(Color c){
		switch(type) {
			case BRIGHTER: setEnabled(!c.equals(c.brighter())); break;
			case DARKER:   setEnabled(!c.equals(c.darker())); break;
		}
	}
}
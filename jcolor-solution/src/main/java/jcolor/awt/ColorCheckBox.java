package jcolor.awt;

import java.awt.*;
import java.awt.event.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorCheckBox extends Checkbox implements ItemListener, ColorListener {
	ColorModel model;
	Color c;
	Checkbox peer; 	// used in order to display round buttons
					// Without CheckboxGroup the radio button is displayed as checkbox
	
	ColorCheckBox (ColorModel model, String label, Color color){
		super(label, false);
		CheckboxGroup g = new CheckboxGroup();
		this.setCheckboxGroup(g);
		peer = new Checkbox();		// used to deselect the radio button
		peer.setCheckboxGroup(g);
		this.model = model;
		c = color;
		addItemListener(this);
		model.addColorListener(this);
	}
	
	@Override
	public void colorValueChanged(Color c){
		peer.setState(true);
		setState(c.equals(this.c));
	}
	
	@Override
	public void itemStateChanged(ItemEvent e){
		model.setColor(c);
	}

}
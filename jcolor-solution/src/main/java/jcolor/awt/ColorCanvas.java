package jcolor.awt;

import java.awt.*;

import jcolor.ColorListener;
import jcolor.ColorModel;

class ColorCanvas extends Canvas implements ColorListener {
	ColorModel model;
	Color color;
	
	ColorCanvas(ColorModel model, Color color){
		this.model = model;
		this.color = color;
		model.addColorListener(this);
	}
	
	@Override
	public void paint(Graphics g){
		Dimension d = getSize();
		g.setColor(color);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.black);
		g.drawRect(0, 0, d.width-1, d.height-1);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(100,100);
	}
	
	@Override
	public void colorValueChanged(Color color){
		this.color = color;
		repaint();
	}

}

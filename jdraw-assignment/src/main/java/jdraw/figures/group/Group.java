package jdraw.figures.group;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jdraw.figures.AbstractFigure;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;

public class Group extends AbstractFigure implements Figure, FigureGroup {
	private static final long serialVersionUID = 1L;
	
	private List<Figure> children = new LinkedList<>();
	
	public Group(List<Figure> figures) {
		children.addAll(figures);
	}
	
	public Group(Figure... figures) {
		for(Figure f : figures) {
			children.add(f);
		}
	}
	
	@Override
	public Iterable<Figure> getFigureParts() {
		return children;
	}

	@Override
	public void draw(Graphics g) {
		children.forEach(c -> c.draw(g));
	}

	@Override
	public void move(int dx, int dy) {
		children.forEach(c -> c.move(dx,dy));
	}

	@Override
	public boolean contains(int x, int y) {
		return getBounds().contains(x, y);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		Iterator<Figure> iter = children.iterator();
		Figure figure = iter.next();
		Rectangle boundingBox = new Rectangle(figure.getBounds());
		while(iter.hasNext()) {
			figure = iter.next();
			boundingBox.add(figure.getBounds());
		}
		return boundingBox;
	}

}

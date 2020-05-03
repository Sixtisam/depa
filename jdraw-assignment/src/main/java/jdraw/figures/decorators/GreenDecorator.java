package jdraw.figures.decorators;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import jdraw.framework.Figure;

public class GreenDecorator extends AbstractFigureDecorator {
	private static final long serialVersionUID = 1L;

	public GreenDecorator(Figure f) {
		super(f);
	}

	@Override
	public Figure clone() {
		return new GreenDecorator(getInner().clone());
	}

	@Override
	public void draw(Graphics g) {
		super.draw(new GraphicsDecorator(g));
	}

	static class GraphicsDecorator extends Graphics {
		private final Graphics realG;

		public GraphicsDecorator(Graphics realG) {
			super();
			this.realG = realG;
			this.realG.setColor(Color.GREEN);
		}

		public void setColor(Color c) {
//			realG.setColor(c);
		}

		public int hashCode() {
			return realG.hashCode();
		}

		public boolean equals(Object obj) {
			return realG.equals(obj);
		}

		public Graphics create() {
			return realG.create();
		}

		public Graphics create(int x, int y, int width, int height) {
			return realG.create(x, y, width, height);
		}

		public void translate(int x, int y) {
			realG.translate(x, y);
		}

		public Color getColor() {
			return realG.getColor();
		}

		public void setPaintMode() {
			realG.setPaintMode();
		}

		public void setXORMode(Color c1) {
			realG.setXORMode(c1);
		}

		public Font getFont() {
			return realG.getFont();
		}

		public void setFont(Font font) {
			realG.setFont(font);
		}

		public FontMetrics getFontMetrics() {
			return realG.getFontMetrics();
		}

		public FontMetrics getFontMetrics(Font f) {
			return realG.getFontMetrics(f);
		}

		public Rectangle getClipBounds() {
			return realG.getClipBounds();
		}

		public void clipRect(int x, int y, int width, int height) {
			realG.clipRect(x, y, width, height);
		}

		public void setClip(int x, int y, int width, int height) {
			realG.setClip(x, y, width, height);
		}

		public Shape getClip() {
			return realG.getClip();
		}

		public void setClip(Shape clip) {
			realG.setClip(clip);
		}

		public void copyArea(int x, int y, int width, int height, int dx, int dy) {
			realG.copyArea(x, y, width, height, dx, dy);
		}

		public void drawLine(int x1, int y1, int x2, int y2) {
			realG.drawLine(x1, y1, x2, y2);
		}

		public void fillRect(int x, int y, int width, int height) {
			realG.fillRect(x, y, width, height);
		}

		public void drawRect(int x, int y, int width, int height) {
			realG.drawRect(x, y, width, height);
		}

		public void clearRect(int x, int y, int width, int height) {
			realG.clearRect(x, y, width, height);
		}

		public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
			realG.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
		}

		public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
			realG.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
		}

		public void draw3DRect(int x, int y, int width, int height, boolean raised) {
			realG.draw3DRect(x, y, width, height, raised);
		}

		public void fill3DRect(int x, int y, int width, int height, boolean raised) {
			realG.fill3DRect(x, y, width, height, raised);
		}

		public void drawOval(int x, int y, int width, int height) {
			realG.drawOval(x, y, width, height);
		}

		public void fillOval(int x, int y, int width, int height) {
			realG.fillOval(x, y, width, height);
		}

		public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
			realG.drawArc(x, y, width, height, startAngle, arcAngle);
		}

		public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
			realG.fillArc(x, y, width, height, startAngle, arcAngle);
		}

		public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
			realG.drawPolyline(xPoints, yPoints, nPoints);
		}

		public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
			realG.drawPolygon(xPoints, yPoints, nPoints);
		}

		public void drawPolygon(Polygon p) {
			realG.drawPolygon(p);
		}

		public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
			realG.fillPolygon(xPoints, yPoints, nPoints);
		}

		public void fillPolygon(Polygon p) {
			realG.fillPolygon(p);
		}

		public void drawString(String str, int x, int y) {
			realG.drawString(str, x, y);
		}

		public void drawString(AttributedCharacterIterator iterator, int x, int y) {
			realG.drawString(iterator, x, y);
		}

		public void drawChars(char[] data, int offset, int length, int x, int y) {
			realG.drawChars(data, offset, length, x, y);
		}

		public void drawBytes(byte[] data, int offset, int length, int x, int y) {
			realG.drawBytes(data, offset, length, x, y);
		}

		public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
			return realG.drawImage(img, x, y, observer);
		}

		public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
			return realG.drawImage(img, x, y, width, height, observer);
		}

		public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
			return realG.drawImage(img, x, y, bgcolor, observer);
		}

		public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
			return realG.drawImage(img, x, y, width, height, bgcolor, observer);
		}

		public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
				ImageObserver observer) {
			return realG.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
		}

		public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor,
				ImageObserver observer) {
			return realG.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
		}

		public void dispose() {
			realG.dispose();
		}

		public void finalize() {
			realG.finalize();
		}

		public String toString() {
			return realG.toString();
		}

		public Rectangle getClipRect() {
			return realG.getClipRect();
		}

		public boolean hitClip(int x, int y, int width, int height) {
			return realG.hitClip(x, y, width, height);
		}

		public Rectangle getClipBounds(Rectangle r) {
			return realG.getClipBounds(r);
		}

	}
}

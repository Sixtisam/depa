package jdraw.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jdraw.figures.AbstractFigure;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

public abstract class AbstractFigureTest {

	protected abstract AbstractFigure createFigure();

	@Test
	@DisplayName("Assert figureChanged is called on registered listeners and not an disconnected listeners")
	final void testNotification1() {
		AbstractFigure f = createFigure();
		FigureListener l = mock(FigureListener.class);
		f.addFigureListener(l);
		f.move(1, 1);
		verify(l, description("figureChanged must be called on a registered listener"))
				.figureChanged(argThat(e -> e.getSource() == f));
		f.removeFigureListener(l);
		f.move(2, 2);
		verify(l, description("figureChanged must not be called on disconnected listener"))
				.figureChanged(argThat(e -> e.getSource() == f));
	}

	@Test
	@DisplayName("Notify only if figure was acutally moved")
	final void testNotification2() {
		AbstractFigure f = createFigure();
		FigureListener l = mock(FigureListener.class);
		f.addFigureListener(l);
		f.move(0, 0);
		verify(l, never().description("Listener was called even if state does not change"))
				.figureChanged(argThat(e -> e.getSource() == f));
	}

	@Test
	@DisplayName("Assert support for multiple listeners")
	final void testMultiListeners() {
		AbstractFigure f = createFigure();
		FigureListener l1 = mock(FigureListener.class);
		FigureListener l2 = mock(FigureListener.class);

		f.addFigureListener(l1);
		f.addFigureListener(l2);
		f.move(3, 3);

		verify(l1, description("first listener should be notified")).figureChanged(any());
		verify(l2, description("multiple listeners are not supported")).figureChanged(any());
	}

	@Test
	@DisplayName("Assert listener list is immutable during notification")
	final void testRemoveListener() {
		AbstractFigure f = createFigure();
		f.addFigureListener(mock(FigureListener.class));
		f.addFigureListener(new RemoveListener(f));
		f.addFigureListener(mock(FigureListener.class));
		f.addFigureListener(mock(FigureListener.class));
		f.move(4, 4);
	}

	@Test
	@DisplayName("Assert no cyclic notification occurs.")
	final void testCycle() {
		AbstractFigure f1 = createFigure();
		AbstractFigure f2 = createFigure();
		f2.setBounds(new Point(10, 10), new Point(20, 20));

		f1.addFigureListener(new UpdateListener(f2));
		f2.addFigureListener(new UpdateListener(f1));

		f2.move(5, 5);
		assertAll("both figures at same position (15, 15)",
				() -> assertEquals(f1.getBounds().getLocation(),
						f2.getBounds().getLocation(),
						"Position of the two figures must be equal"),
				() -> assertEquals(15, f1.getBounds().x, "Figures must both be at position x=15"),
				() -> assertEquals(15, f1.getBounds().y, "Figures must both be at position y=15"));

		f1.move(5, 5);
		assertAll("both figures at same position (20, 20)",
				() -> assertEquals(f1.getBounds().getLocation(),
						f2.getBounds().getLocation(),
						"Position of the two figures must be equal"),
				() -> assertEquals(20, f1.getBounds().x, "Figures must both be at position x=20"),
				() -> assertEquals(20, f1.getBounds().y, "Figures must both be at position y=20"));
	}

	@Test
	@DisplayName("Assert setBounds sets bounding box correctly")
	final void testSetBounds() {

		assertAll("set bounding box from all angles", () -> {
			var f = createFigure();
			f.setBounds(new Point(-5, -1), new Point(13, 19));
			var expected = new Rectangle(-5, -1, 18, 20);
			var actual = f.getBounds();
			assertEquals(expected, actual);
		},
				() -> {
					var f = createFigure();
					f.setBounds(new Point(-3, 4), new Point(5, -1));
					var expected = new Rectangle(-3, -1, 8, 5);
					var actual = normalize(f.getBounds());
					assertEquals(expected, actual);
				},
				() -> {
					var f = createFigure();
					f.setBounds(new Point(5, -1), new Point(-3, 4));
					var expected = new Rectangle(-3, -1, 8, 5);
					var actual = normalize(f.getBounds());
					assertEquals(expected, actual);
				},
				() -> {
					var f = createFigure();
					f.setBounds(new Point(5, 4), new Point(-3, -1));
					var expected = new Rectangle(-3, -1, 8, 5);
					var actual = normalize(f.getBounds());
					assertEquals(expected, actual);
				});
	}

	@Test
	@DisplayName("Assert move shifts figure correctly")
	final void testMove() {
		var fig = createFigure();
		var b = fig.getBounds();
		fig.move(0, 0);
		assertEquals(b, fig.getBounds());

		assertAll("move in all directions", () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(3, 7);
			assertMoved(before, f.getBounds(), 3, 7);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(-3, 7);
			assertMoved(before, f.getBounds(), -3, 7);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(3, -7);
			assertMoved(before, f.getBounds(), 3, -7);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(-3, -7);
			assertMoved(before, f.getBounds(), -3, -7);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(2, 0);
			assertMoved(before, f.getBounds(), 2, 0);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(-3, 0);
			assertMoved(before, f.getBounds(), -3, 0);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(0, 5);
			assertMoved(before, f.getBounds(), 0, 5);
		}, () -> {
			var f = createFigure();
			var before = f.getBounds();
			f.move(0, -7);
			assertMoved(before, f.getBounds(), 0, -7);
		});
	}

	private void assertMoved(Rectangle before, Rectangle after, int dx, int dy) {
		final var b = normalize(before);
		final var a = normalize(after);
		assertAll("compare x, y, width and height",
				() -> assertEquals(b.x + dx, a.x),
				() -> assertEquals(b.y + dy, a.y),
				() -> assertEquals(b.width, a.width),
				() -> assertEquals(b.height, a.height));
	}

	final protected Rectangle normalize(Rectangle r) {
		int x, y, w, h;
		if (r.width < 0) {
			x = r.x + r.width;
			w = -r.width;
		} else {
			x = r.x;
			w = r.width;
		}
		if (r.height < 0) {
			y = r.y + r.height;
			h = -r.height;
		} else {
			y = r.y;
			h = r.height;
		}
		return new Rectangle(x, y, w, h);
	}

	/**
	 * Listener unsubscribes itself from further notification when notified of a
	 * change of its subject.
	 */
	final class RemoveListener implements FigureListener {
		private final Figure f;

		RemoveListener(Figure f) {
			this.f = f;
		}

		@Override
		public void figureChanged(FigureEvent e) {
			f.removeFigureListener(this);
		}
	}

	/**
	 * Listener updates its own position to match position of subject by moving
	 * itself. Instead of repositioning itself by calling setBounds(), this listener
	 * calculates the distances it needs to move to the same position as the
	 * subjects. Then it moves itself by this distance.
	 */
	final class UpdateListener implements FigureListener {
		private final Figure f;

		public UpdateListener(Figure f) {
			this.f = f;
		}

		@Override
		public void figureChanged(FigureEvent e) {
			Point p1 = e.getFigure().getBounds().getLocation();
			Point p2 = f.getBounds().getLocation();
			f.move(p1.x - p2.x, p1.y - p2.y);
		}
	}
}
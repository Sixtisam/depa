package patterns.decorator.identity;

public abstract class Figure {
	public abstract void move(int dx, int dy);

	public abstract void draw();

	public final boolean isSame(Figure other) {
		return isSameImpl(other) || (other != null && other.isSameImpl(this));
	}

	protected boolean isSameImpl(Figure other) {
		return this == other;
	}

}

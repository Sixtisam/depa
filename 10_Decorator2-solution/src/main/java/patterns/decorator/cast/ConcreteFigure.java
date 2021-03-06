package patterns.decorator.cast;

public class ConcreteFigure implements Figure {

	@Override
	public final boolean isInstanceOf(Class<?> type) {
		return type.isAssignableFrom(this.getClass());
	}

	@Override
	public final <T> T getInstanceOf(Class<T> type) {
		//return (T)this;  // unchecked version, compiler complains
		return type.cast(this); // checked version of (T)this, accepted by compiler
	}

	@Override
	public void draw() {
		System.out.println("drawing concrete figure");
	}

	@Override
	public void move(int dx, int dy) {
		System.out.println("moving concrete figure (" + dx + ", " + dy + ")");
	}

}

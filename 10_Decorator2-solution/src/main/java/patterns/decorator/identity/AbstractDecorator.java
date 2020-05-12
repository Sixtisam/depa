package patterns.decorator.identity;

public class AbstractDecorator extends Figure {
	private final Figure inner;

	public Figure getInner() {
		return inner;
	}

	public AbstractDecorator(Figure inner) {
		this.inner = inner;
	}

	@Override
	public void draw() {
		inner.draw();
	}

	@Override
	public void move(int dx, int dy) {
		inner.move(dx, dy);
	}
	
	@Override
	protected final boolean isSameImpl(Figure other) {
		return super.isSameImpl(other) || inner.isSameImpl(other);
	}
}

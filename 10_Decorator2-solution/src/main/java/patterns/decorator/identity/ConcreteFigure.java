package patterns.decorator.identity;

public class ConcreteFigure extends Figure {

	@Override
	public void draw() {
		System.out.println("drawing concrete figure");
	}

	@Override
	public void move(int dx, int dy) {
		System.out.println("moving concrete figure (" + dx + ", " + dy + ")");
	}

}

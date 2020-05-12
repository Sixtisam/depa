package patterns.decorator.identity;

public class BorderDecorator extends AbstractDecorator {

	public BorderDecorator(Figure inner) {
		super(inner);
	}

	@Override
	public void draw() {
		System.out.println("draw border");
		super.draw();
	}

}

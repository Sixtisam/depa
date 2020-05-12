package patterns.decorator.identity;

public class Test {
	public static void main(String[] args) {
		Figure f0 = new ConcreteFigureExtended();
		Figure f1 = new BorderDecorator(f0);
		Figure f2 = new AnimationDecorator(f1);
		System.out.println(f2.isSame(f2));
		System.out.println(f2.isSame(f1));
		System.out.println(f2.isSame(f0));
		System.out.println(f1.isSame(f2));
		System.out.println(f1.isSame(f1));
		System.out.println(f1.isSame(f0));
		System.out.println(f0.isSame(f2));
		System.out.println(f0.isSame(f1));
		System.out.println(f0.isSame(f0));
		System.out.println();
		Figure f3 = new ConcreteFigure();
		System.out.println(f2.isSame(f3));
		System.out.println(f3.isSame(f2));
		System.out.println(f1.isSame(f3));
		System.out.println(f3.isSame(f1));
		System.out.println(f0.isSame(f3));
		System.out.println(f3.isSame(f0));
		Figure f4 = new BorderDecorator(f3);
		System.out.println(f2.isSame(f4));
		System.out.println(f4.isSame(f2));
		System.out.println(f1.isSame(f4));
		System.out.println(f4.isSame(f1));
		System.out.println(f0.isSame(f4));
		System.out.println(f4.isSame(f0));
		System.out.println(f2.isSame(null));
		System.out.println(f0.isSame(null));
	}
}

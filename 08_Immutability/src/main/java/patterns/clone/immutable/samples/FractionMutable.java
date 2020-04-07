package patterns.clone.immutable.samples;

public class FractionMutable {
	private int n, d;

	// Constructors
	public FractionMutable(int numer, int denom) {
		if (denom == 0) throw new IllegalArgumentException();
		int g = gcd(numer, denom);
		this.n = numer / g;
		this.d = denom / g;
		assert this.d > 0;
	}

	private static int gcd(int a, int b) {
		int r = a % b;
		while (r != 0) {
			a = b;
			b = r;
			r = a % b;
		}
		return b;
	}

	public FractionMutable(int numer) { this(numer, 1); }
	public FractionMutable(FractionMutable f) { this(f.n, f.d); }

	public double getNumerator() { return n; }
	public double getDenominator() { return d; }

	@Override
	public String toString() {
		return n + " / " + d;
	}

	public void divide(FractionMutable y) { // this = this / y
		if (y.n == 0) {
			throw new IllegalArgumentException(); 
		}
		this.n *= y.d; // remember Fraction is mutable!
		this.d *= y.n; // when dividing by itself, we use this.n from previous line!
		int g = gcd(this.n, this.d);
		this.n /= g;
		this.d /= g;
		assert this.d > 0;
	}
	
	public static void main(String[] args) {
		FractionMutable f = new FractionMutable(2, 3); 	// f = 2/3
		System.out.println(f);
		f.divide(f);						// f = f/f => 1/3 :wrong!
		System.out.println(f);
	}

}
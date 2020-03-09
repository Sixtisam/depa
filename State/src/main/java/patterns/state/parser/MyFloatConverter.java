package patterns.state.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyFloatConverter {

	public static void main(String[] args) throws Exception {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String s = r.readLine();
		while (s != null && s.length() > 0) {
			try {
				double d = parseDouble(s);
				System.out.println(d);
			} catch (IllegalArgumentException e) {
				System.out.println("Illegal Format");
			}
			s = r.readLine();
		}
	}

	private static double parseDouble(String str) {
		DoubleData data = new DoubleData();
		ParserState currentState = S1;
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case 'e':
			case 'E':
				currentState = currentState.handleE(data);
				break;
			case '-':
				currentState = currentState.handleMinus(data);
				break;
			case '+':
				currentState = currentState.handlePlus(data);
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				currentState = currentState.handleDigit(data, str.charAt(i) - '0');
				break;
			case '.':
				currentState = currentState.handleDot(data);
				break;
			default:
				currentState = currentState.handleUnknown(data);
				break;
			}
		}
		if (currentState == S3 || currentState == S6) {
			return data.getValue();
		} else {
			throw new IllegalStateException();
		}

	}

	public static class DoubleData {
		double m = 0;
		int exp = 0;
		int sign = 1;
		int quo = 10;

		double getValue() {
			return m * Math.pow(10, sign * exp);
		}
	}

	public static final ParserState S0 = new S0();
	public static final ParserState S1 = new S1();
	public static final ParserState S2 = new S2();
	public static final ParserState S3 = new S3();
	public static final ParserState S4 = new S4();
	public static final ParserState S5 = new S5();
	public static final ParserState S6 = new S6();

	public static interface ParserState {
		default ParserState handleDigit(DoubleData data, int val) {
			throw new IllegalArgumentException();
		}

		default ParserState handleE(DoubleData data) {
			throw new IllegalArgumentException();
		}

		default ParserState handleDot(DoubleData data) {
			throw new IllegalArgumentException();
		}

		default ParserState handlePlus(DoubleData data) {
			throw new IllegalArgumentException();
		}

		default ParserState handleMinus(DoubleData data) {
			throw new IllegalArgumentException();
		}

		default ParserState handleUnknown(DoubleData data) {
			throw new IllegalArgumentException();
		}
	}

	public static class S0 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.m = 10 * data.m + val;
			return S1;
		}

		@Override
		public ParserState handleDot(DoubleData data) {
			return S2;
		}
	}

	public static class S1 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.m = 10 * data.m + val;
			return S1;
		}

		@Override
		public ParserState handleDot(DoubleData data) {
			return S3;
		}

		@Override
		public ParserState handleE(DoubleData data) {
			return S4;
		}
	}

	public static class S2 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.m += val / data.quo;
			data.quo *= 10;
			return S3;
		}
	}

	public static class S3 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.m += val / (double) data.quo;
			System.out.println(data.quo);
			System.out.println(data.m);
			data.quo *= 10;
			return S3;
		}

		@Override
		public ParserState handleE(DoubleData data) {
			return S4;
		}
	}

	public static class S4 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.exp = val;
			return S6;
		}

		@Override
		public ParserState handlePlus(DoubleData data) {
			data.sign = 1;
			return S5;
		}

		@Override
		public ParserState handleMinus(DoubleData data) {
			data.sign = -1;
			return S5;
		}
	}

	public static class S5 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.exp = val;
			return S6;
		}
	}

	public static class S6 implements ParserState {
		@Override
		public ParserState handleDigit(DoubleData data, int val) {
			data.exp = (10 * data.exp) + val;
			return S6;
		}
	}
}

package model;

public class Operation {
	
	private final static int SUMA = 1;
	private final static int RESTA = 2;
	private final static int MULTIPLICACION = 3;
	private final static int DIVISION = 4;
	
	
	private int n1, n2, result, options[];
	private char sign;
	
	
	public Operation() {
		
		n1 = generateRandomNumber(1, 100);
		n2 = generateRandomNumber(1, 100);
		sign = generateRandomSign();
		result = getResult(n1,n2,sign);
	}
	
	
	public int generateRandomNumber(int min,int max) {
		
		return (int)(Math.random()*(max+min));
	}
	
	
	public char generateRandomSign() {
		
		int v = generateRandomNumber(1, 5);
		
		switch (v) {
		
			case SUMA: return '+';
			case RESTA: return '-';
			case MULTIPLICACION: return '*';
			case DIVISION: return '/';
			default: return '+';
		}
	}
	
	
	public int getResult(int n1, int n2, char sing) {
		
		switch (sign) {
		
			case '+': return (int)n1+n2;
			case '-': return (int)n1-n2;
			case '*': return (int)n1*n2;
			case '/': return (int)n1/n2;
			default: return (int)n1+n2;
		}
	}
	
	
	public int[] createOptions(int result) {
		
		int[] opts = new int[4];
		int pos = generateRandomNumber(0, 4);
		
		opts[0] = result - generateRandomNumber(1, 6);
		opts[1] = result - generateRandomNumber(6, 10);
		opts[2] = result + generateRandomNumber(1, 6);
		opts[3] = result + generateRandomNumber(6, 10);
		
		opts[pos]  = result;
		return opts;		
	}
	
	
	public int getN1() {
		
		return n1;
	}
	
	
	public void setN1(int n1) {
		
		this.n1 = n1;
	}
	
	
	public int getN2() {
		
		return n2;
	}
	
	
	public void setN2(int n2) {
		
		this.n2 = n2;
	}
	
	
	public int getResult() {
		
		return result;
	}
	
	
	public void setResult(int result) {
		
		this.result = result;
	}
	
	
	public int[] getOptions() {
		
		return options;
	}
	
	
	public void setOptions(int[] options) {
		
		this.options = options;
	}
	
	
	public char getSign() {
		
		return sign;
	}
	
	
	public void setSign(char sign) {
		
		this.sign = sign;
	}
}

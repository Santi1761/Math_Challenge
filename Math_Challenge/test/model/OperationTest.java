package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OperationTest {
	Operation a;
	
	private void setup1() {
		a = new Operation();
	}
	@Test
	void testCreateOptions() {
		setup1();
		int[] aux = a.createOptions(5);
		assertEquals(5, aux[0],10);
	}

}

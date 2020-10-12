package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.00);
		assertEquals(0.20, DKK.getRate(), 0.00);
		assertEquals(1.5, EUR.getRate(), 0.00);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.80);
		assertEquals(0.80, SEK.getRate(), 0.00);
		EUR.setRate(0.001);
		assertEquals(0.001, EUR.getRate(), 0.00);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK.universalValue(10000), 0.00);
		assertEquals(2000, DKK.universalValue(10000), 0.00);
		assertEquals(15000, EUR.universalValue(10000), 0.00);
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(10000, SEK.valueInThisCurrency(1000, EUR), 0.00);
		assertEquals(7500, DKK.valueInThisCurrency(1000, EUR), 0.00);
	}

}
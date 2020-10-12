package b_Money;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, SEK100.getAmount(), 0);
		assertEquals(1000, EUR10.getAmount(), 0);
		assertEquals(20000, SEK200.getAmount(), 0);
		assertEquals(2000, EUR20.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		assertSame(SEK, SEK100.getCurrency());
		assertSame(EUR, EUR10.getCurrency());
		assertSame(SEK, SEK200.getCurrency());
		assertSame(EUR, EUR20.getCurrency());
		assertSame(SEK, SEK0.getCurrency());
		assertSame(EUR, EUR0.getCurrency());
		assertSame(SEK, SEKn100.getCurrency());
		assertEquals("SEK", SEK100.getCurrency().getName());
		assertEquals("EUR", EUR10.getCurrency().getName());
		assertEquals("SEK", SEK200.getCurrency().getName());
		assertEquals("EUR", EUR20.getCurrency().getName());
		assertEquals("SEK", SEK0.getCurrency().getName());
		assertEquals("EUR", EUR0.getCurrency().getName());
		assertEquals("SEK", SEKn100.getCurrency().getName());
		assertThat(SEK, instanceOf(SEK100.getCurrency().getClass()));
		assertThat(EUR, instanceOf(EUR10.getCurrency().getClass()));
		assertThat(SEK, instanceOf(SEK200.getCurrency().getClass()));
		assertThat(EUR, instanceOf(EUR20.getCurrency().getClass()));
		assertThat(SEK, instanceOf(SEK0.getCurrency().getClass()));
		assertThat(EUR, instanceOf(EUR0.getCurrency().getClass()));
		assertThat(SEK, instanceOf(SEKn100.getCurrency().getClass()));
	}

	@Test
	public void testToString() {
		// (..) e.g. "10.5 SEK"
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
		assertEquals("200.00 SEK", SEK200.toString());
		assertEquals("20.00 EUR", EUR20.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("0.00 EUR", EUR0.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(SEK.universalValue(10000), SEK100.universalValue());
		assertTrue(EUR10.equals(SEK100));
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK0.equals(EUR0));
		assertFalse(EUR10.equals(EUR20));
		assertFalse(SEK0.equals(EUR10));
		assertFalse(SEKn100.equals(EUR0));
		assertTrue(EUR10.equals(EUR10));
		assertTrue(EUR10.equals(SEK100));
	}

	@Test
	public void testAdd() {
		Money addMoneySEK0toEUR0 = SEK0.add(EUR0);
		assertEquals(new Money(0, SEK).getAmount(), addMoneySEK0toEUR0.getAmount());
		Money addMoneySEK100toEUR10 = SEK100.add(EUR10);
		assertEquals(new Money(20000, SEK).getAmount(), addMoneySEK100toEUR10.getAmount());
		Money addMoneySEK200toSEK200 = SEK200.add(SEK200);
		assertEquals(new Money(40000, SEK).getAmount(), addMoneySEK200toSEK200.getAmount());
	}

	@Test
	public void testSub() {
		Money subMoneySEK0toEUR0 = SEK0.sub(EUR0);
		assertEquals(new Money(0, SEK).getAmount(), subMoneySEK0toEUR0.getAmount());
		Money subMoneySEK100toEUR10 = SEK100.sub(EUR10);
		assertEquals(new Money(0, SEK).getAmount(), subMoneySEK100toEUR10.getAmount());
		Money subMoneySEK200toSEK200 = SEK200.sub(SEK200);
		assertEquals(new Money(0, SEK).getAmount(), subMoneySEK200toSEK200.getAmount());
		Money subMoneySEKn100toSEKn100 = SEKn100.sub(SEKn100);
		assertEquals(new Money(-20000, SEK).getAmount(), subMoneySEKn100toSEKn100.getAmount());
	}

	@Test
	public void testIsZero() {
		assertFalse(SEK100.isZero());
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(0, EUR0.negate().getAmount(), 0);
		assertEquals(-10000, SEK100.negate().getAmount(), 0);
		assertEquals(10000, SEKn100.negate().getAmount(), 0);
		assertEquals(-20000, SEK200.negate().getAmount(), 0);
	}

	@Test
	public void testCompareTo() {
		assertTrue(SEKn100.compareTo(EUR20) < 0);
		assertTrue(EUR0.compareTo(SEK0) == 0);
	}
}
package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("Nordea", Nordea.getName());
		assertEquals("SweBank", SweBank.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Filip");
		Hashtable<String, Account> accountList = SweBank.getAccountlist();
		assertTrue(accountList.containsKey("Filip"));
		assertFalse(accountList.containsKey("FilipWozniak"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(700000, SEK));
		assertEquals(700000, SweBank.getBalance("Ulrika"), 0.0);
		Nordea.deposit("Bob", new Money(8000, SEK));
		assertEquals(8000, Nordea.getBalance("Bob"), 0.0);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Bob"), 0.0);
		SweBank.withdraw("Bob", new Money(20000, SEK));
		assertEquals(20000, SweBank.getBalance("Bob"), 0.0);
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, DanskeBank.getBalance("Gertrud"), 0);
		SweBank.deposit("Ulrika", new Money(700000, SEK));
		assertEquals(700000, SweBank.getBalance("Ulrika"), 0.0);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		assertEquals(0, Nordea.getBalance("Bob"), 0.0);
		SweBank.transfer("Bob", Nordea, "Bob", new Money(550000, SEK));
		assertEquals(550000, Nordea.getBalance("Bob"), 0.0);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Bob", "Bob2Bob", 10, 1, new Money(3000000, SEK), Nordea, "Bob");
		SweBank.tick();
		SweBank.tick();
		assertEquals(3000000, Nordea.getBalance("Bob"), 0.0);
	}
}
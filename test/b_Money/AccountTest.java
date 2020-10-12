package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("Alice", 30, 1, new Money(10000, SEK), SweBank, "Hans");
		assertTrue(testAccount.timedPaymentExists("Alice"));
		testAccount.removeTimedPayment("Alice");
		assertFalse(testAccount.timedPaymentExists("Alice"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException, AccountExistsException {
		testAccount.addTimedPayment("Hans", 1, 0, new Money(800000, SEK), SweBank, "Alice");
		testAccount.tick();
		testAccount.tick();
		assertEquals(8400000, testAccount.getBalance().getAmount(), 0.0);
	}

	@Test
	public void testAddWithdraw() {
		Money moneyWithdrawHalf = new Money(testAccount.getBalance().getAmount() / 2, SEK);
		testAccount.withdraw(moneyWithdrawHalf);
		assertEquals(5000000, testAccount.getBalance().getAmount(), 0);
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount(), 0);
	}
}
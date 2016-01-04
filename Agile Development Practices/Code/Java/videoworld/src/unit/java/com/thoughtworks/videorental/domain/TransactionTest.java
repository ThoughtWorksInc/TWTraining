package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;

public class TransactionTest {
	private static final Movie FINDING_NEMO = new Movie("Finding Nemo", Movie.CHILDRENS);
	private static final Movie SHAWSHANK_REDEMPTION = new Movie("Shawshank Redemption", Movie.REGULAR);

	private static final Customer CUSTOMER_ONE = new Customer("James Cameron");
	private static final Customer CUSTOMER_TWO = new Customer("Quentin Tarantino");
	
	private static final Rental RENTAL_ONE = new Rental(CUSTOMER_ONE, FINDING_NEMO, Period.of(LocalDate.today(),
			Duration.ofDays(1)));
	private static final Rental RENTAL_TWO = new Rental(CUSTOMER_ONE, SHAWSHANK_REDEMPTION, Period.of(
			LocalDate.today(), Duration.ofDays(3)));

	@Test
	public void shouldReturnDifferentRentalSetFromConstruction() {
		final Set<Rental> rentals = new HashSet<Rental>();
		rentals.add(RENTAL_ONE);
		final Transaction transaction = new Transaction(LocalDateTime.now(), CUSTOMER_ONE, rentals);

		rentals.add(RENTAL_TWO);
		
		assertFalse(rentals.equals(transaction.getRentals()));
		assertEquals(Collections.singleton(RENTAL_ONE), transaction.getRentals());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldReturnUnmodifiableRentalSet() {
		final Transaction transaction = new Transaction(LocalDateTime.now(), CUSTOMER_ONE, Collections.singleton(RENTAL_ONE));
		transaction.getRentals().clear();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfRentalForDifferentCustomer() {
		new Transaction(LocalDateTime.now(), CUSTOMER_TWO, Collections.singleton(RENTAL_ONE));
	}
}

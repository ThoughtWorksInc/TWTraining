package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.Period;

public class CustomerTest {

	private static final String RESOURCES_PATH = "src/unit/resources";
	private static final Set<Rental> EMPTY_RENTALS = Collections.emptySet();

	private Customer customer;
	private Set<Rental> mixedRentals;

	@Before
	public void setUp() {
		customer = new Customer("John Smith");

		final Movie montyPython = new Movie("Monty Python and the Holy Grail", Movie.REGULAR);
		final Movie ran = new Movie("Ran", Movie.REGULAR);
		final Movie laConfidential = new Movie("LA Confidential", Movie.NEW_RELEASE);
		final Movie starTrek = new Movie("Star Trek 13.2", Movie.NEW_RELEASE);
		final Movie WallaceAndGromit = new Movie("Wallace and Gromit", Movie.CHILDRENS);

		mixedRentals = new LinkedHashSet<Rental>();
		mixedRentals.add(new Rental(customer, montyPython, Period.of(LocalDate.today(), Duration.ofDays(3))));
		mixedRentals.add(new Rental(customer, ran, Period.of(LocalDate.today(), Duration.ofDays(1))));
		mixedRentals.add(new Rental(customer, laConfidential, Period.of(LocalDate.today(), Duration.ofDays(2))));
		mixedRentals.add(new Rental(customer, starTrek, Period.of(LocalDate.today(), Duration.ofDays(1))));
		mixedRentals.add(new Rental(customer, WallaceAndGromit, Period.of(LocalDate.today(), Duration.ofDays(6))));
	}

	@Test
	public void testEmpty() throws Exception {
		String noRentalsStatement = 
			"Rental Record for John Smith\n"
			+ "Amount charged is $0.0\n" 
			+ "You have a new total of 0 frequent renter points";
		assertEquals(noRentalsStatement, customer.statement(EMPTY_RENTALS));
	}

	@Test
	public void testCustomer() throws Exception {
		String expected = 
			"Rental Record for John Smith\n" 
			+ "  Monty Python and the Holy Grail  -  $3.5\n"
			+ "  Ran  -  $2.0\n"
			+ "  LA Confidential  -  $6.0\n"
			+ "  Star Trek 13.2  -  $3.0\n"
			+ "  Wallace and Gromit  -  $6.0\n"
			+ "Amount charged is $20.5\n"
			+ "You have a new total of 6 frequent renter points";
		assertEquals(expected, customer.statement(mixedRentals));
	}

}

package com.thoughtworks.videorental.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.datetime.LocalDateTime;

public class Transaction {
	private final LocalDateTime dateTime;
	private final Customer customer;
	private final Set<Rental> rentals;

	public Transaction(final LocalDateTime dateTime, final Customer customer, final Set<Rental> rentals) {
		this.dateTime = dateTime;
		for (final Rental rental : rentals) {
			if (!rental.getCustomer().equals(customer)) {
				throw new IllegalArgumentException("Rentals must be for the same customer");
			}
		}
		this.customer = customer;
		this.rentals = Collections.unmodifiableSet(new HashSet<Rental>(rentals));
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Set<Rental> getRentals() {
		return rentals;
	}
}
